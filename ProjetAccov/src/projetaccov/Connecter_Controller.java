/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetaccov;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author George Harik
 */
public class Connecter_Controller extends Thread  
{    
    ServerSocket SacaSocket;
    ArrayList<String> list;
    Socket socket;

    public Connecter_Controller(ServerSocket SacaSocket,ArrayList<String> list) {
        this.SacaSocket = SacaSocket;      
        this.list = list;
    }

    @Override
    public void run() {
        int nbrController = 1;
        while (true) {

            
            try {
                
                System.out.println(list.toString());
                
                socket = SacaSocket.accept();
                Thread t = new Thread(new Accepter_Controller(socket,list,nbrController));
                t.start();
                AfficherMessage("Le controlleur numero " + nbrController + "a demarré");
                nbrController++;

            } catch (IOException ex) {
                
                try {
                socket.close();
                System.out.println("Fin");
                
            } catch (IOException ex2) {
                Logger.getLogger(Accepter_Avion.class.getName()).log(Level.SEVERE, null, ex2);
            } 
                
                Logger.getLogger(Connecter_Avion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void AfficherMessage(String msg) {
        System.out.println(msg);        
    }   
    
}
