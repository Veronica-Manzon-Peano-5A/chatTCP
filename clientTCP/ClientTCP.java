/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienttcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manzon.veronica
 */
public class ClientTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String hostName= "127.0.0.1";
        int portNumber=1234;
        
        try {
            //trasfrma la stringa in un indirizzo
            InetAddress address= InetAddress.getByName(hostName);
            Socket clientSocket= new Socket(address,portNumber);
            System.out.println("Per terminare usare Ctrl-C e ENTER per spedire la linea di testo.");
           
            //ricevere dati dal server
            Listener l;
            
            try{
                l= new Listener(clientSocket);
                Thread t= new Thread((Runnable) l);
                t.start();
            }catch (Exception e) { 
                System.out.println("Connessione NON riuscita con server: ");
            }
            //messaggi client --> server
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            //serve per leggere ciò che è stato scritto e successivamente inviarlo
            BufferedReader in= new BufferedReader(new InputStreamReader(System.in));
            String input; // input dell'utente
            System.out.println(">");
            
            //finchè c'è testo, viene inviato
            while((input=in.readLine())!=null){
                out.println(input);
                System.out.println(input + "--> è il messaggio inviato al server");
                
            
            }
            //System.out.println('\n'+">");
            clientSocket.close();
            
        } catch (IOException ex) {
            //Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connessione terminata");
            ex.printStackTrace();
        }  
        
        
        
        
        
        
    }
    
}
