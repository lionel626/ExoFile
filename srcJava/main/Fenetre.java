package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Fenetre extends JFrame implements ActionListener{
	
	JLabel l_file;
	JLabel l_prod;
	JLabel l_cons;
	JButton pause;
	JButton quit;
	Boolean run;
	
	File file;
	Prod prod;
	Conso cons[];
	
	public Fenetre() {
		this.setSize(600, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.run = true;
		
		this.file = new File(20);
		
		l_file = new JLabel("< 22, 54, 85, 65, 0, 5 >");
		l_prod = new JLabel("Producteur : ......");
		l_cons = new JLabel("Consomateur : ......");
		
		pause = new JButton(" Run ");
		quit = new JButton("Quit");
		quit.addActionListener(this);
		pause.addActionListener(this);
		
		JPanel content = (JPanel) this.getContentPane();
		JPanel cent = new JPanel();
		JPanel aff = new JPanel();
		aff.setLayout(new GridLayout(2,1));
		content.setLayout(new BorderLayout());
		cent.setLayout(new FlowLayout());
		
		content.add(l_file, BorderLayout.NORTH);
		aff.add(l_prod);
		aff.add(l_cons);
		content.add(aff,BorderLayout.CENTER);
		cent.add(quit);
		cent.add(pause);
		content.add(cent, BorderLayout.SOUTH);
		
		
		this.setVisible(true);
		
	}
	

	
	public static void main(String[] argv) {
		Fenetre fn = new Fenetre();
		
		
	}
	
	public void createThread() {
		
		
		if(this.run) {
			this.prod = new Prod(this.file,50,this.l_prod,this.l_file);
			this.cons = new Conso[3];
			for(int i = 0; i<this.cons.length; i++) {
				this.cons[i] = new Conso(this.file,250,this.l_cons,this.l_file);
			}
			this.prod.start();
			for(int i = 0; i<this.cons.length; i++) {
				this.cons[i].start();
			}
			this.pause.setText("Pause");
			this.run = !this.run;
		} else {
			this.prod.interrupt();
			for(int i = 0; i<this.cons.length; i++) {
				this.cons[i].interrupt();
			}
			this.run = !this.run;
			this.pause.setText("Run");
		}
		
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == this.quit) {
			dispose();
			
			System.exit(0);
		} else if(source == this.pause) {
			if(((JButton) source).getText()==" Run ") {
				pause.setText("Pause");
			} else {
				pause.setText(" Run ");
			}
			createThread();
			
		}
	}
}
