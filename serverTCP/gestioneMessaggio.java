/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertcp;

import java.util.ArrayList;

/**
 *
 * @author IMPRESA MANZON
 */
public class gestioneMessaggio {
    //questa classe gestisce i messaggi e l'invio a tutti i clients
    //ricevo un messaggio e attraverso i workers tutti i client
    //vengono a conoscenza del messaggio
    
    private String messaggio;
    
    //lista di client connessi, worker
    private ArrayList<socketWork> workers= new ArrayList<>();
    
    //aggiunta client alla lista
    void addClient(socketWork w){
        this.workers.add(w);
    
    }
    
    //rimuovo il client
    void removeClient(socketWork w){
        this.workers.remove(w);
    
    }
    
    //creo interfaccia da impementare da tutti i threads
    //per inviare mex
    interface inviaMex{
        public void sendMex(String mex);
    }
    
    //metodo usato quando i workers ricevono un messaggio dal client
    synchronized void sendNewMex(String mex){
        //sinchronized perchè non si devono creare conflitti
        this.messaggio= mex;
        //invia a tutti il messaggio ricevuto
        for(socketWork worker: this.workers){
            //invia il messaggio
            worker.sendMex(this.messaggio);
        }
    }
    //serve per i thread che vogliono ricevere messaggi dal client
    //quando la implemento poi in mexRivecuto() uso sendNewMex()
    interface riceviMex{
        public void mexRicevuto(String mex);
    }
    
    
    
}
