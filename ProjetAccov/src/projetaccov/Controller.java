/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetaccov;

import JFrames.ControllerJFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static projetaccov.Radar.message;
import static projetaccov.Radar.socket;

/**
 *
 * @author George Harik
 */
public class Controller {

    public static Socket socket = null;

    Boolean ouvrir_communication() {

        try {
            socket = new Socket("localhost", 2011);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AvionClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

   
    public static void main(String[] args) throws InterruptedException {

        PrintWriter out;

        try {

            Controller cont = new Controller();
            
            ControllerJFrame cjf =  new ControllerJFrame();
            cjf.setVisible(true);
            
            if (cont.ouvrir_communication() == true) {
                while (true) {
                    BufferedReader inFromSaca = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    message = inFromSaca.readLine();
                    
                    String[] split = message.split("-");
                    cjf.UpdateTitle(split[0]);
                    cjf.Updatebody(split[1]);
                           
                    
                }
            }
            
        } catch (Exception ex) {
           System.out.println("Fin Controller");
        }

    }

}
