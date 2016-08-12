package org.processmining.plugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.deckfour.uitopia.api.event.TaskListener.InteractionResult;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.dfr.Dfr;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;
import org.processmining.gui.Gui;
import org.processmining.gui.Gui2;
import org.processmining.plugins.InductiveMiner.dfgOnly.Dfg;
import org.processmining.plugins.InductiveMiner.dfgOnly.DfgMiningParameters;
import org.processmining.plugins.InductiveMiner.dfgOnly.DfgMiningParametersIMiD;
import org.processmining.plugins.InductiveMiner.dfgOnly.plugins.IMdProcessTree;
import org.processmining.processtree.ProcessTree;

@Plugin(name = "Database Inductive Miner", parameterLabels = { }, returnLabels = { "Process Tree" }, returnTypes = { ProcessTree.class }, userAccessible = true)

public class DatabaseInductiveMinerPlugin {
	
	@UITopiaVariant(affiliation = "Eindhoven University of Technology", author = "A. Syamsiyah", email = "a.syamsiyah@tue.nl")
	@PluginVariant(requiredParameterLabels = { })
	
	public ProcessTree apply(UIPluginContext context) {
		long startTime = System.nanoTime();
		
		Gui gui = new Gui(context);
		InteractionResult result = context.showWizard("Enter your database configuration", true, true, gui);   
		
		String username = null;
		String password = null;
		String jdbc = null;
		String databasename = null;
		String logID = null;
		
		if(result == InteractionResult.FINISHED) {

			String[] conf = gui.getConfiguration();
			username = conf[0];
			password = conf[1];
			jdbc = conf[2];
			databasename = conf[3];
			logID = conf[4];
		}
		 
		
		java.util.Properties props = new java.util.Properties();
        props.put("user", username);
        props.put("password", password);
        Connection conn = null;
		try {
			conn = DriverManager.getConnection(jdbc +databasename,props);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		Dfr dfr = new Dfr(conn);
		
		String classifier = dfr.getClassifier(logID);
		Boolean createDfr = false;
		String classifierID = null;
		
		Gui2 gui2 = new Gui2(context);
		gui2.displayClassifier(classifier);
		InteractionResult result2 = context.showWizard("Enter your database configuration", true, true, gui2);   
		
		
		if(result2 == InteractionResult.FINISHED) {

			String[] conf = gui2.getConfiguration();
			classifierID = conf[0];
			if(conf[1].equals("yes")) createDfr = true;
		}
		
		// create dfr
		if(createDfr) dfr.create();
		
		// get the dfr and convert it into dfg
		Dfg dfg = dfr.getDfg(logID, classifierID);
		
		long stopTime = System.nanoTime();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("IM with DB - get dfg: " + elapsedTime/1000000 + " ms");
		
		DfgMiningParameters param = new DfgMiningParametersIMiD();
		ProcessTree processtree = IMdProcessTree.mineProcessTree(dfg, param);
		
		stopTime = System.nanoTime();
	    elapsedTime = stopTime - startTime;
	    System.out.println("IM with DB - total time used: " + elapsedTime/1000000 + " ms");
		
		return processtree;
	}
}
