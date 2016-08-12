package org.processmining.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
/*
 * Created by JFormDesigner on Fri Aug 12 12:36:44 CEST 2016
 */
import javax.swing.border.EmptyBorder;

import org.processmining.contexts.uitopia.UIPluginContext;



/**
 * @author Alifah Syamsiyah
 */
public class Gui2 extends JPanel {
	
	String classifiers;
	String chosenClassifier;
	String createDfr;
	
	public String[] getConfiguration() {
		chosenClassifier = textField2.getText();
		if(radioButton1.isEnabled()) createDfr = "yes";
		else createDfr = "no";
		
		String[] conf = {chosenClassifier, createDfr};
		
		return conf;
	}
	
	public void displayClassifier(String classifier) {
		textArea1.setText(classifier);
	}
	
	public Gui2(final UIPluginContext context) {
		initComponents();
	}
	

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Alifah Syamsiyah
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label1 = new JLabel();
		textField2 = new JTextField();
		label2 = new JLabel();
		label3 = new JLabel();
		label4 = new JLabel();
		radioButton1 = new JRadioButton();
		radioButton2 = new JRadioButton();
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();

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
				label1.setText("These are classifier(s) inside the log:");

				//---- label2 ----
				label2.setText("Choose one (write down the classifier id):");

				//---- label3 ----
				label3.setText("Do you want to (re)create the DFR table?");

				//---- radioButton1 ----
				radioButton1.setText("Yes");

				//---- radioButton2 ----
				radioButton2.setText("No");

				//======== scrollPane1 ========
				{
					scrollPane1.setViewportView(textArea1);
				}

				GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
				contentPanel.setLayout(contentPanelLayout);
				contentPanelLayout.setHorizontalGroup(
					contentPanelLayout.createParallelGroup()
						.addGroup(contentPanelLayout.createSequentialGroup()
							.addGroup(contentPanelLayout.createParallelGroup()
								.addGroup(contentPanelLayout.createSequentialGroup()
									.addGap(26, 26, 26)
									.addGroup(contentPanelLayout.createParallelGroup()
										.addComponent(label1)
										.addComponent(label2)
										.addComponent(label3)
										.addComponent(label4)))
								.addGroup(contentPanelLayout.createSequentialGroup()
									.addGap(37, 37, 37)
									.addGroup(contentPanelLayout.createParallelGroup()
										.addComponent(radioButton1)
										.addComponent(radioButton2))))
							.addContainerGap(215, Short.MAX_VALUE))
						.addGroup(contentPanelLayout.createSequentialGroup()
							.addGap(26, 26, 26)
							.addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
								.addComponent(textField2, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
								.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
							.addGap(0, 101, Short.MAX_VALUE))
				);
				contentPanelLayout.setVerticalGroup(
					contentPanelLayout.createParallelGroup()
						.addGroup(contentPanelLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(label1)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(label2)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(8, 8, 8)
							.addComponent(label4)
							.addGap(42, 42, 42)
							.addComponent(label3)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(radioButton1)
							.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
							.addComponent(radioButton2)
							.addContainerGap())
				);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);
		}
		add(dialogPane, BorderLayout.CENTER);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Alifah Syamsiyah
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel label1;
	private JTextField textField2;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JRadioButton radioButton1;
	private JRadioButton radioButton2;
	private JScrollPane scrollPane1;
	private JTextArea textArea1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
