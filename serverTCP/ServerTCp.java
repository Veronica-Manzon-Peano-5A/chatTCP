/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servertcp;

import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manzon.veronica
 */
public class ServerTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        int port=1234;
        //porta del server
        try {
            ServerSocket s= new ServerSocket(port);
            System.out.println("Server in ascolto...");
            
            while(true){
                //viene generato un Worker per ogni client che si conntte
                //ha un thread personale
                
                socketWork w;
                
                try{
                //accetta a connessione e crea un nuovo socket
                Socket newS= s.accept();
                
                //la socketWork si occupa di ogni client
                w= new socketWork(newS);
                
                //creo un thread per eseguire e comunicare con più client
                Thread t= new Thread((Runnable) w);
                t.start();
                }catch (IOException e) {
                    System.out.println("Connessione NON riuscita con client: ");
                    System.exit(-1);
                }
            }
        } catch (IOException ex) {
            System.out.println("Porta non disponibile");
            System.exit(-1);
            //Logger.getLogger(ServerTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
