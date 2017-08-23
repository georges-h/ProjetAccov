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
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author George Harik
 */
public class Accepter_Controller implements Runnable {

    Socket SacaSocket;
    ArrayList<String> list;
    int num;
    private PrintWriter outToRadar = null;

    public Accepter_Controller(Socket SacaSocket, ArrayList<String> list, int num) {
        this.SacaSocket = SacaSocket;
        this.list = list;
        this.num = num;
    }

    @Override
    public void run() {

        while (true) {
            try {
                
                outToRadar = new PrintWriter(SacaSocket.getOutputStream());
                outToRadar.println("Controller: " + num + "  -  " + list.toString()); 
                outToRadar.flush();
                Thread.sleep(2000);
                
            } catch (IOException ex) {
                Logger.getLogger(Accepter_Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Accepter_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
