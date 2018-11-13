package main;

import java.util.ArrayList;

public class File {
	int size;
	ArrayList<Integer> f;

	public File(int size) {              // Constructeur prend une taille max
																			 //		argument size
		this.f = new ArrayList<Integer>();
		this.size = size;
	}

	public synchronized void  add(int elem) { /* Gestion de l'ajout d'un element elem
																							et gestion du verrou synchronized*/
		while(f.size() >= this.size) {         // Gestion si la file est pleine
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		f.add((Integer)elem);    // Ajout de l'element à la file
		notifyAll();
	}

	public synchronized int remove() { // supression d'un element dans la file
		while(f.size()==0) {             // Gestion si file vide
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		int a = f.remove(0);    // Suppresion d'un element
		notifyAll();
		return a;
	}
	public String displayFile() {
		String str = "[ ";
		if(this.f.size()==1) {
			str+= this.f.get(0).toString()+ " ]";
		}else if(this.f.size()==0) {
			str+= "]";
		} else {
			for(int i =0; i< this.f.size()-1;i++) {
				str  += this.f.get(i).toString() + ", ";
			}
			str += this.f.get(this.f.size()-1).toString()+ " ]";
		}
		return str;
	}

}
