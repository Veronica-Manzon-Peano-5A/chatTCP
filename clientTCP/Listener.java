/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienttcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manzon.veronica
 */
class Listener implements Runnable{

    private Socket client;

    public Listener(Socket client) {
        this.client = client;
    }

    public void run() {
        //classe "runnable" che serve quando il Client riceve testo dal Server
        //il server non invia suoi messaggi ma inoltra quelli degli altri client
        
        //connessione al socket per poter leggere i messaggi che arrivano
        BufferedReader in = null;
        
        try {
            in= new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }

        //scrivere ciò che si è ricevuto
        String testoRicevuto = "";
        try {

            while ((testoRicevuto = in.readLine()) != null) {
                System.out.println(testoRicevuto);
                //messaggio di chiusura
                if (testoRicevuto.contains("Ciao")) {
                    client.close();
                    System.exit(0);
                    break;
                }
            }
        } catch (IOException ex) {
            //Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex);
            try {
                System.out.println("Connessione terminata dal server");
                client.close();
                System.exit(-1);
            } catch (IOException ex1) {
                //Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("Error nella chiusura del socket");
                System.exit(-1);
            }
            
            
        }

    }
}
