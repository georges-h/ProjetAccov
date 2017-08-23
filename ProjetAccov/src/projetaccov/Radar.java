/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetaccov;

import JFrames.RadarFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author George Harik
 */
public class Radar {

    public static Socket socket = null;
    public static String message = "";
    public static Thread t1;
    static String Nom_Avion="";
    static String X="";
    static String Y="";
    static String Z="";
    static String Vitesse="";
    static String Cap="";
    static String Changements="";
    static int NumeroAvion;
           
      
    public static void main(String[] args) {

        try {

            System.out.println("Demande de connexion du radar");
            socket = new Socket("127.0.0.1", 2010);
            BufferedReader inFromSaca = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            RadarFrame rf = new  RadarFrame();
            rf.setVisible(true);
        
            while (true) {

                message = inFromSaca.readLine();
                String[] split = message.split("-");           
                    Nom_Avion = split[0].split(":")[1];
                    X = split[1].split(":")[1];
                    Y = split[2].split(":")[1];
                    Z = split[3].split(":")[1];
                    Vitesse = split[4].split(":")[1];
                    Cap = split[5].split(":")[1];
                    Changements = split[6].split(":")[1];
                    NumeroAvion = Integer.parseInt(split[7].split(":")[1]);
                    
                    rf.UpdateRadar(Nom_Avion, X, Y, Z, Vitesse, Cap,Changements,NumeroAvion);
            }

        } catch (IOException ex) {
            Logger.getLogger(Radar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
