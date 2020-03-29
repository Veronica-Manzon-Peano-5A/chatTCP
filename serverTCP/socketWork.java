/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertcp;

import java.io.*;
import java.net.*;
import servertcp.gestioneMessaggio.inviaMex;
import servertcp.gestioneMessaggio.riceviMex;



/**
 *
 * @author manzon.veronica
 */
public class socketWork implements Runnable, inviaMex, riceviMex{
    private Socket client;
    private PrintWriter out=null;
    private static final gestioneMessaggio gest = new gestioneMessaggio();
    socketWork (Socket client){
        this.client= client;
        gest.addClient(this);
        
        System.out.println("Connesso");
    }
    
    //ogni volta che si riceve un messaggio in automatico
    //si invia agli altri client connessi
    @Override
    public void sendMex(String mex){ 
        out.println("Server riceverà: " + mex);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void mexRicevuto(String mex) {
        this.gest.sendNewMex(mex);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void run(){
        BufferedReader in=null;
        //connessione per inviare e ricevere
        try {
            in= new BufferedReader(new InputStreamReader(client.getInputStream()));
            out= new PrintWriter(client.getOutputStream(),true);
        } catch (IOException ex) {
            System.out.println("Errore: in|out fallito");
            System.exit(-1);
            //Logger.getLogger(socketWork.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String linea="";
        //diamo un nome al client
        int clientPorta= client.getPort();
        
        while(linea!= null){
            try {
                //ricevo messaggio da client (aspetto)
                linea= in.readLine();
                //il messaggio ricevuto viene mandato a tutti i client (chat)
                mexRicevuto(linea);
                System.out.println("Il client sulla porta "+clientPorta +" ha inviato >> " + linea);
            } catch (IOException ex) {
                System.out.println("Lettura fallita");
                System.exit(-1);
            }
        }
            try {
                client.close();
                System.out.println("Connessione chiusa con client "+ client);

            } catch (IOException ex) {
                System.out.println("Connessione non chiusa");
                //Logger.getLogger(socketWork.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        }
    
    
    }
    
    

   

   

   
    
    

