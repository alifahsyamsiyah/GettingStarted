package org.processmining.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
/*
 * Created by JFormDesigner on Thu Aug 11 16:46:36 CEST 2016
 */

import org.processmining.contexts.uitopia.UIPluginContext;



/**
 * @author Alifah Syamsiyah
 */
public class Gui extends JPanel {
	
	String username;
	String password;
	String jdbc;
	String databasename;
	String logID;
	
	public String[] getConfiguration() {
		username = textField1.getText();
		password = textField2.getText();
		jdbc = textField3.getText();
		databasename = textField4.getText();
		logID = textField5.getText();
		
		String[] conf = {username, password, jdbc, databasename, logID};
		
		return conf;
	}
	
	
	public Gui(final UIPluginContext context) {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Alifah Syamsiyah
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label1 = new JLabel();
		textField1 = new JTextField();
		textField2 = new JTextField();
		label2 = new JLabel();
		label3 = new JLabel();
		textField3 = new JTextField();
		label4 = new JLabel();
		label5 = new JLabel();
		textField4 = new JTextField();
		textField5 = new JTextField();
		textField6 = new JTextField();
		label6 = new JLabel();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

		setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setMinimumSize(new Dimension(317, 299));
				contentPanel.setMaximumSize(new Dimension(32767, 42767));

				//---- label1 ----
				label1.setText("Username");

				//---- label2 ----
				label2.setText("Password");

				//---- label3 ----
				label3.setText("JDBC");

				//---- label4 ----
				label4.setText("Database Name");

				//---- label5 ----
				label5.setText("Log ID");

				GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
				contentPanel.setLayout(contentPanelLayout);
				contentPanelLayout.setHorizontalGroup(
					contentPanelLayout.createParallelGroup()
						.addGroup(contentPanelLayout.createSequentialGroup()
							.addGap(26, 26, 26)
							.addGroup(contentPanelLayout.createParallelGroup()
								.addComponent(label1)
								.addComponent(label2)
								.addComponent(label3)
								.addComponent(label5)
								.addComponent(label4))
							.addGap(58, 58, 58)
							.addGroup(contentPanelLayout.createParallelGroup()
								.addComponent(textField5, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField4, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
								.addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addComponent(textField3)
									.addComponent(textField2)
									.addComponent(textField1, GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
							.addContainerGap(115, Short.MAX_VALUE))
				);
				contentPanelLayout.setVerticalGroup(
					contentPanelLayout.createParallelGroup()
						.addGroup(contentPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label1))
							.addGap(18, 18, 18)
							.addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label2))
							.addGap(18, 18, 18)
							.addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(label3)
								.addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(20, 20, 20)
							.addGroup(contentPanelLayout.createParallelGroup()
								.addComponent(textField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label4))
							.addGap(18, 18, 18)
							.addGroup(contentPanelLayout.createParallelGroup()
								.addComponent(label5)
								.addComponent(textField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(111, 111, 111))
				);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);
		}
		add(dialogPane, BorderLayout.CENTER);

		//---- label6 ----
		label6.setText("Base URI");
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Alifah Syamsiyah
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel label1;
	private JTextField textField1;
	private JTextField textField2;
	private JLabel label2;
	private JLabel label3;
	private JTextField textField3;
	private JLabel label4;
	private JLabel label5;
	private JTextField textField4;
	private JTextField textField5;
	private JTextField textField6;
	private JLabel label6;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
