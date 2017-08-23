/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetaccov;

import static java.lang.System.exit;

/**
 *
 * @author George Harik
 */
public class ProjetAccov {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        /
        AvionClient av = new AvionClient();
        
        av.afficher_donnees();

   
    if (av.ouvrir_communication() == false) {
        System.err.println("Impossible de contacter le gestionnaire de vols\n");
        exit(1);
    }

    av.se_deplacer();
        
     
    }
    
}
