
import sys
from threading import Thread, Condition
import random
import time
from tkinter import *


class File(Condition):
    """docstring for File. Prend en parametre une taille max : size"""
    def __init__(self, size):
        super(File, self).__init__()
        self.size = size
        self.f = list()
        self.verr = Condition() # Verrou

    """ Méthode enfile, ajoute un element en tete de file """
    def enfile(self, elem):
        with self.verr: #  Gestion du verrou
            while (len(self.f) >= self.size): # Gestion si file pleine
                self.verr.wait() # Attente si file pleine
            self.f.insert(0,elem)
            self.verr.notifyAll()

    """ Methode defile, retire un element en debut file
        ( l'ajout des element se fait à la suite )"""
    def defile(self):
        with self.verr: # Gestion du verrou
            while (len(self.f)  <1): # Gestion si file vide
                self.verr.wait() # Attente si file vide
            a = self.f.pop()
            self.verr.notifyAll()
            return a


class Conso(Thread):
    """docstring for Conso. Prend en parametre une file n, un temps attente att
        son label lab et le label de la file file"""
    def __init__(self, n,att, lab,file):
        Thread.__init__(self)
        self.interrupt = True
        self.n = n
        self.att = att
        self.daemon = True # Arret du Thread quand le programme pricipal et arreter
        self.lab = lab
        self.file = file

    def run(self):
        self.interrupt = True # Gestion l'interruption
        while self.interrupt:
            e = self.n.defile() # retire l'élément dans la file
            self.lab.config(text="Consomateur : (-) " + str(e)) # Affichage du Travaille
                                                                # du consomateur
            self.file.config(text = str(self.n.f)) # Affichage de la file
            time.sleep(self.att/1000)

    def stop(self): # Fonction qui gére l'arret du Thread
        self.interrupt = not self.interrupt


class Prod(Thread):
    """docstring for Prod. Prend en parametre une file n, un temps attente att
        son label lab et le label de la file file"""
    def __init__(self, n,att, lab,file):
        Thread.__init__(self)
        self.interrupt = True
        self.n = n
        self.att = att
        self.daemon = True # Arret du Thread quand le programme pricipal et arreter
        self.lab = lab
        self.file = file

    def run(self):
        self.interrupt = True # Gestion l'interruption
        while self.interrupt:
            a = random.randint(0,100)
            self.n.enfile(a)
            self.lab.config(text="Producteur : (+)" + str(a)) # Affichage du Travaille
                                                              # du producteur
            self.file.config(text = str(self.n.f)) # Affichage de la file
            time.sleep(self.att/1000)

    def stop(self): # Fonction qui gére l'arret du Thread
        self.interrupt = not self.interrupt

class Fenetre(Frame):
    """docstring for Fenetre."""
    def __init__(self,master = None ):
        Frame.__init__(self,master)
        self.tfile = File(20)
        self.pause = False
        self.master = master
        self.cons = Label(self.master,text=" Consomateur : ....",pady=150,padx= 240)
        self.cons.grid(row=4, column=2)
        self.file = Label(self.master,text='[???]')
        self.file.grid(row=1, column=2)
        self.prod = Label(self.master,text = 'Producteur : ....')
        self.prod.grid(row=7, column=2)
        self.quit = Button(self.master, text='quit', command = self.close)
        self.quit.grid(row= 9, column=2)
        self.pause = Button(self.master, text='Pause', command = self.startThread)
        self.pause.grid(row= 8, column=2)

    def close(self):
        self.master.destroy()
    def createThread(self):
        self.tProd = Prod(self.tfile,40,self.prod,self.file)
        self.tCons = []
        for i in range(4):
            self.tCons.append(Conso(self.tfile,2000,self.cons,self.file))
    def deleteThread(self):
        del self.tProd
        del self.tCons

    def startThread(self):
        if self.pause:
            self.createThread()
            self.tProd.start()
            for t in self.tCons:
                t.start()
            self.pause = not self.pause
        else:
            self.tProd.stop()
            for t in self.tCons:
                t.stop()
            self.pause = not self.pause






root = Tk()
root.title("Thread File")
root.resizable(False,False)
root.geometry("600x600")
app = Fenetre(root)


root.mainloop()
