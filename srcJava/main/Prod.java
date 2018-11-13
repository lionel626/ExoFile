package main;

import javax.swing.JLabel;

public class Prod extends Thread {
	  private File f;
	  private int t;
	  private JLabel prod;
	  private JLabel file;

	  public Prod(File f, int t, JLabel prod, JLabel file) {
			/*Prend en parametre une file f, un temps attente t
	        son label prod et le label de la file file*/
	    this.f = f;
	    this.t = t;
	    this.prod = prod;
	    this.file = file;
	    setDaemon(true); // arret du Thread si le programme pricipal s'arrete
	  }

	  public void run(){
	    try {
	      while(!interrupted()) { // gestion de interruption
		    	int a = (int)(Math.random()*100);
		    	this.prod.setText("Producteur : " + "(+) "+a); // Affichage de l'element à ajouter
		    	this.file.setText(this.f.displayFile()); // Affichage de la file
	        f.add(a); // ajout de l'element dans la file
	        sleep(t);
	      }

	    } catch(InterruptedException e) {}
	  }
	}
