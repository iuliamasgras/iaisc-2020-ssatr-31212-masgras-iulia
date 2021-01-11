package net.agten.heatersimulator.gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.agten.heatersimulator.Main;

public class AboutDialog {
	private final JDialog dialog;
	
	public AboutDialog(Frame owner) {
		dialog = new JDialog(owner, "About", true);
		initializeComponents();
		dialog.setLocationRelativeTo(owner);
	}
	
	private void initializeComponents() {
		Box b = Box.createVerticalBox();
	    b.add(Box.createGlue());
	    
	    JLabel title = new JLabel(Main.NAME);
	    title.setFont(title.getFont().deriveFont(18.0f));
		b.add(title);
		
		JLabel version = new JLabel("Version: "+Main.VERSION+" (22/10/2011)");
		b.add(version);
		
		JLabel author = new JLabel("By "+Main.AUTHOR+" <"+Main.AUTHOR_EMAIL+">");
		b.add(author);
		
	    b.add(Box.createGlue());
	    dialog.add(b, "Center");

	    JPanel p2 = new JPanel();
	    JButton ok = new JButton("Ok");
	    
	    p2.add(ok);
	    dialog.add(p2, "South");
		
	
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				hide();
			}
		});
		
		dialog.setResizable(false);
		dialog.pack();
	}
	
	public void show() {
		dialog.setVisible(true);
	}
	
	public void hide() {
		dialog.setVisible(false);
	}

}
