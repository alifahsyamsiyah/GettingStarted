package org.processmining.dfr;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

import org.deckfour.xes.classification.XEventClass;
import org.processmining.plugins.InductiveMiner.dfgOnly.Dfg;
import org.processmining.plugins.InductiveMiner.dfgOnly.DfgImpl;

public class Dfr {
	private Connection conn;
	private HashMap<String, Integer> mapIndices;
	private int totalelements;
	public HashSet<String> elements1 = new HashSet<String>();
	public HashSet<String> elements2 = new HashSet<String>();
	public HashSet<String> singleelement = new HashSet<String>();
	
	public Dfr(Connection conn) {
		this.conn = conn;
		this.mapIndices = new HashMap<String, Integer>();
		this.totalelements = 0;
	}
	
	public void create() {
		Statement st = null;
		
		// drop table dfr
		String query = "DROP TABLE IF EXISTS dfr; ";
		
		// create table dfr
		String query2 = "CREATE TABLE dfr ( `id` varchar(50) not null, `elem1` varchar(250) not null, `elem2` varchar(250) not null, `freq` int, primary key (`id`, `elem1`, `elem2`) ); ";
		
		// for pair of activities
		String query3 = "INSERT INTO dfr SELECT x.identifier, x.val1, x.val2, count(*) as freq FROM ( select concat('dfr', '-', d.log_id, '-', d.classifier_id) as identifier, e1.id as id1, group_concat(a1.value_ separator ' ') as val1, e2.id as id2, group_concat(a2.value_ separator ' ') as val2 from (select tr1.trace_id as tr_id, tr1.event_id as ev_id1, tr2.event_id as ev_id2 from trace_has_event as tr1 use index (idx_seq) inner join trace_has_event as tr2 use index (idx_seq) on tr1.trace_id = tr2.trace_id where tr1.sequence = tr2.sequence - 1) as t, attribute as a1, attribute as a2, event as e1, event as e2, log_has_trace as l, log_has_classifier as d, classifier as c where t.tr_id = l.trace_id and l.log_id = d.log_id and d.classifier_id = c.id and t.ev_id1 = e1.id and e1.attr_id = a1.id and c.key_ = a1.key_ and t.ev_id2 = e2.id and e2.attr_id = a2.id and c.key_ = a2.key_ group by d.log_id, d.classifier_id, e1.id, e2.id ) as x GROUP BY identifier, val1, val2 ";
		
		// start activities
		String query4 = "INSERT INTO dfr SELECT x.identifier, 'the start', x.val, count(*) as freq FROM ( SELECT concat('dfr', '-', d.log_id, '-', d.classifier_id) as identifier, e.id, group_concat(a.value_ separator ' ') as val FROM trace_has_event as t, event as e, attribute as a, log_has_trace as l, log_has_classifier as d, classifier as c WHERE t.sequence = 0 and t.trace_id = l.trace_id and l.log_id = d.log_id and d.classifier_id = c.id and t.event_id = e.id and e.attr_id = a.id and c.key_ = a.key_ group by d.log_id, d.classifier_id, e.id ) as x GROUP BY identifier, val ";
		
		// end activities
		String query5 = "INSERT INTO dfr SELECT x.identifier, x.val, 'the end', count(*) as freq FROM ( SELECT concat('dfr', '-', d.log_id, '-', d.classifier_id) as identifier, e.id, group_concat(a.value_ separator ' ') as val FROM trace_has_event as t, event as e, attribute as a, log_has_trace as l, log_has_classifier as d, classifier as c, (select trace_id, count(*) as length from trace_has_event group by trace_id) as y WHERE t.trace_id = y.trace_id and t.sequence = y.length-1 and t.trace_id = l.trace_id and l.log_id = d.log_id and d.classifier_id = c.id and t.event_id = e.id and e.attr_id = a.id and c.key_ = a.key_ group by d.log_id, d.classifier_id, e.id ) as x GROUP BY identifier, val ";
		
		// drop trigger for log_has_classifier
		String query6 = "DROP TRIGGER IF EXISTS trigger_log_has_classifier; ";
		
		// create trigger for log_has_classifier
		String query7 = "CREATE TRIGGER trigger_log_has_classifier AFTER INSERT ON log_has_classifier FOR EACH ROW BEGIN set @logID = new.log_id; set @classifierID = new.classifier_id; set @dfrID = concat('dfr', '-', @logID, '-', @classifierID); replace into log_has_dfr values (@logID, @classifierID, @dfrID); END; ";

		// drop trigger for dfr
		String query8 = "DROP TRIGGER IF EXISTS trigger_trace_has_event; ";
		
		// create trigger for dfr
		String query9 = "CREATE TRIGGER trigger_trace_has_event AFTER INSERT ON trace_has_event FOR EACH ROW BLOCK1: BEGIN DECLARE done1 INT DEFAULT FALSE; declare logID varchar(50); declare curlogID cursor for select log_id from log_has_trace where trace_id = new.trace_id; DECLARE CONTINUE HANDLER FOR NOT FOUND SET done1 = TRUE; set @newEvent = new.event_id; set @newTrace = new.trace_id; open curlogID; read_loop_1: LOOP fetch curlogID into logID; if done1 then leave read_loop_1; end if; BLOCK2: BEGIN DECLARE done2 INT DEFAULT FALSE; declare classifierID varchar(50); declare curclassifierID cursor for select classifier_id from log_has_classifier where log_id = logID; DECLARE CONTINUE HANDLER FOR NOT FOUND SET done2 = TRUE; open curclassifierID; read_loop_2: LOOP fetch curclassifierID into classifierID; if done2 then leave read_loop_2; end if; set @newEC = ''; set @prevEC = ''; set @seq = 0; set @dfrID = ''; BLOCK3: BEGIN DECLARE done3 INT DEFAULT FALSE; declare keys_ varchar(50); declare curkey cursor for select key_ from classifier where id = classifierID; DECLARE CONTINUE HANDLER FOR NOT FOUND SET done3 = TRUE; open curkey; read_loop_3: LOOP fetch curkey into keys_; if done3 then leave read_loop_3; end if; select a.value_, t.sequence from trace_has_event as t, event as e, attribute as a where t.event_id = @newEvent and t.event_id = e.id and e.attr_id = a.id and a.key_ = keys_ into @newECtemp, @seq; set @newEC = concat(@newEC,' ',@newECtemp); end loop read_loop_3; close curkey; end BLOCK3; select dfr_id from log_has_dfr where log_id = logID and classifier_id = classifierID into @dfrID; if @seq = 0 then set @oldfreq = 0; select freq from dfr where id = @dfrID and elem1 = 'the start' and elem2 = @newEC into @oldfreq; set @newfreq = @oldfreq + 1; replace into dfr values (@dfrID,'the start',@newEC,@newfreq); set @oldfreq = 0; select freq from dfr where id = @dfrID and elem1 = @newEC and elem2 = 'the end' into @oldfreq; set @newfreq = @oldfreq + 1; replace into dfr values (@dfrID,@newEC,'the end',@newfreq); else BLOCK4: BEGIN DECLARE done4 INT DEFAULT FALSE; declare keys_ varchar(50); declare curkey cursor for select key_ from classifier where id = classifierID; DECLARE CONTINUE HANDLER FOR NOT FOUND SET done4 = TRUE; open curkey; read_loop_4: LOOP fetch curkey into keys_; if done4 then leave read_loop_4; end if; select a.value_ from trace_has_event as t, event as e, attribute as a where t.trace_id = @newTrace and t.sequence = @seq - 1 and t.event_id = e.id and e.attr_id = a.id and a.key_ = keys_ into @prevECtemp; set @prevEC = concat(@prevEC,' ',@prevECtemp); end loop read_loop_4; close curkey; end BLOCK4; set @oldfreq = 0; select freq from dfr where id = @dfrID and elem1 = @prevEC and elem2 = 'the end' into @oldfreq; set @newfreq = @oldfreq - 1; if @newfreq = 0 then delete from dfr where id = @dfrID and elem1 = @prevEC and elem2 = 'the end'; else replace into dfr values (@dfrID, @prevEC, 'the end', @newfreq); end if; set @oldfreq = 0; select freq from dfr where id = @dfrID and elem1 = @newEC and elem2 = 'the end' into @oldfreq; set @newfreq = @oldfreq + 1; replace into dfr values (@dfrID, @newEC, 'the end', @newfreq); set @oldfreq = 0; select freq from dfr where id = @dfrID and elem1 = @prevEC and elem2 = @newEC into @oldfreq; set @newfreq = @oldfreq + 1; replace into dfr values (@dfrID, @prevEC, @newEC, @newfreq); end if; end loop read_loop_2; close curclassifierID; end BLOCK2; end loop read_loop_1; close curlogID; END BLOCK1; ";
		
		try {
							
			st = conn.createStatement();
			st.execute(query);
			System.out.println("Finish execute query 1");
			st.execute(query2);
			System.out.println("Finish execute query 2");
			st.execute(query3);
			System.out.println("Finish execute query 3");
			st.execute(query4);
			System.out.println("Finish execute query 4");
			st.execute(query5);
			System.out.println("Finish execute query 5");
			st.execute(query6);
			System.out.println("Finish execute query 6");
			st.execute(query7);		
			System.out.println("Finish execute query 7");
			st.execute(query8);		
			System.out.println("Finish execute query 8");
			st.execute(query9);		
			System.out.println("Finish execute query 9");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Dfg getDfg(String logid, String classifierid) {
		Dfg dfg = new DfgImpl();
		HashMap<String,Integer> eventClassIndex = new HashMap<String, Integer>();
		
		try {
			Statement st = conn.createStatement();
			
			String dfrid = "dfr-" + logid + "-" + classifierid;
			
			String query = "select elem1, elem2, freq " 
							+ "from dfr "
							+ "where id = '" + dfrid +"'";
			ResultSet rs = st.executeQuery(query);
			
			int index = 0;
			while(rs.next()) {
				String elem1 = rs.getString(1);
				String elem2 = rs.getString(2);
				int freq = rs.getInt(3);
			
				if(!eventClassIndex.containsKey(elem1)) {
					eventClassIndex.put(elem1, index);
					index++;
				}				
				if(!eventClassIndex.containsKey(elem2)) {
					eventClassIndex.put(elem2, index);
					index++;
				}
				
				XEventClass ec1 = new XEventClass(elem1, eventClassIndex.get(elem1));
				XEventClass ec2 = new XEventClass(elem2, eventClassIndex.get(elem2));
				
				dfg.addActivity(ec1);
				dfg.addActivity(ec2);
				
				if(elem1.equalsIgnoreCase("the start")) {
					dfg.addStartActivity(ec1, freq); 
				} else if(elem2.equalsIgnoreCase("the end")) {
					dfg.addEndActivity(ec2, freq);
				} 
				
				dfg.addDirectlyFollowsEdge(ec1, ec2, freq);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dfg;		
	}
	
	public String getClassifier(String logid) {
		String classifier = "";
			
		try {
			Statement st = conn.createStatement();
			
			String query = "select c.id, group_concat(c.key_ separator ' ') " 
							+ "from log_has_classifier as l, classifier as c "
							+ "where l.log_id = '" + logid +"' and l.classifier_id = c.id " 
							+ "group by c.id";
			ResultSet rs = st.executeQuery(query);
			
			while(rs.next()) {
				String id = rs.getString(1);
				String key = rs.getString(2);
				
				classifier = classifier + id + " " + key + "\n";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return classifier;
	}
	
}
