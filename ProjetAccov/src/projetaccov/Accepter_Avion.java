/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetaccov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author George Harik
 */
public class Accepter_Avion implements Runnable {

    BufferedReader inFromAvion;
    private Socket socket;
    private Socket socketRadar;
    private int nbrAvion ;
    private PrintWriter outToRadar = null;
    ArrayList<String> list;

    public Accepter_Avion(Socket socket, Socket socketRadar, ArrayList<String> list,int nbrAvion) {
        this.socket = socket;
        this.socketRadar = socketRadar;
        this.list = list;
        this.nbrAvion = nbrAvion;
    }

    @Override
    public void run() {

        try {

            while (true) {

                inFromAvion = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message_From_Avion = inFromAvion.readLine();
                String message_distant = "";

                String nom_avion = message_From_Avion.substring(6, 11);

                if (!list.contains(nom_avion.trim())) {                
                    list.add(nom_avion.trim());
                }

                outToRadar = new PrintWriter(socketRadar.getOutputStream());
                message_From_Avion = message_From_Avion + "-NumeroAvion:" +nbrAvion;
                outToRadar.println(message_From_Avion); 
                outToRadar.flush();

            }

        } catch (IOException e) {

            System.out.println("Fin accepter avion");
            e.printStackTrace();
        }
    }
}
