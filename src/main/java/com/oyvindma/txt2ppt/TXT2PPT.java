package com.oyvindma.txt2ppt;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.poi.hslf.usermodel.SlideShow;

public class TXT2PPT {

	private JFrame jFrame = null;
	private JPanel jContentPane = null;
	private JEditorPane notatfelt = null;
	private JButton generer = null;
	private PptBuilder presentationBulider = new PptBuilder(new SlideShow()); // @jve:decl-index=0:
	private JPanel knappePanel = null;
	private JScrollPane jScrollPane = null;

	/**
	 * This method initializes notatfelt
	 * 
	 * @return javax.swing.JEditorPane
	 */
	private JEditorPane getNotatfelt() {
		if (notatfelt == null) {
			notatfelt = new JEditorPane();
		}
		return notatfelt;
	}

	/**
	 * This method initializes generer
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getGenerer() {
		if (generer == null) {
			generer = new JButton();
			generer.setAction(new AbstractAction() {

				private static final long serialVersionUID = 1L;

				public void actionPerformed(ActionEvent e) {
					try {
						presentationBulider.buildPresentation(notatfelt
								.getText());
						presentationBulider.writePresentation(new File(
								"C:/presentasjon.ppt"));

					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e);
						e1.printStackTrace();
					}

				}
			});
			generer.setVerticalAlignment(SwingConstants.TOP);
			generer.setText("Generer");
		}
		return generer;
	}

	/**
	 * This method initializes knappePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getKnappePanel() {
		if (knappePanel == null) {
			knappePanel = new JPanel();
			knappePanel.setLayout(new FlowLayout());
			knappePanel.add(getGenerer(), new GridBagConstraints());
		}
		return knappePanel;
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getNotatfelt());
		}
		return jScrollPane;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				TXT2PPT application = new TXT2PPT();
				application.getJFrame().setVisible(true);
			}
		});
	}

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setSize(500, 400);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("Application");
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getKnappePanel(), BorderLayout.EAST);
			jContentPane.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

}
