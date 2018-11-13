package main;

import javax.swing.JLabel;

public class Conso extends Thread {
	  private File f;
	  private int t;
	  private JLabel cons;
	  private JLabel file;

	  public Conso(File f, int t, JLabel cons, JLabel file) {
			/*Prend en parametre une file f, un temps attente t
	        son label cons et le label de la file file*/
	    this.f = f;
	    this.t = t;
	    this.cons = cons;
	    this.file = file;
	    setDaemon(true); // arret du Thread si le programme pricipal s'arrete
	  }

	  public void run(){
	    try {

	      while(!interrupted()) { // gestion de interruption

	        int a = f.remove(); // retire un element de la file
	        this.cons.setText("Consomateur : " + "(-) "+a); // affichage de l'element retiré
	        this.file.setText(this.f.displayFile()); // Affichage de la file

	        sleep(t);
	      }
	    } catch(InterruptedException e) {}
	  }
	}
