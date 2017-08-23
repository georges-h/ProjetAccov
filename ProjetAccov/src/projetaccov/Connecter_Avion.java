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
import static projetaccov.AvionClient.socket;

/**
 *
 * @author George Harik
 */
public class Connecter_Avion extends Thread {


    ServerSocket SacaSocket;
    Socket SocketRadar;
    ArrayList<String> list;
    Socket socket;
    

    public Connecter_Avion(ServerSocket SacaSocket, Socket SocketRadar, ArrayList<String> list) {
        this.SacaSocket = SacaSocket;
        this.SocketRadar = SocketRadar;
        this.list = list;
    }
    
    

    @Override
    public void run() {
        int nbrAvion = 0;
        while (true) {

            
            try {
                
                socket = SacaSocket.accept();
                Thread t = new Thread(new Accepter_Avion(socket, SocketRadar,list,nbrAvion));
                t.start();
                AfficherMessage("L'avion numero " + nbrAvion + "a demarré");
                nbrAvion++;
                
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
