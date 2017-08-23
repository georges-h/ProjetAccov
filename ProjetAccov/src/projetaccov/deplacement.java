/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetaccov;

/**
 *
 * @author George Harik
 */
public class deplacement {
    
    private int cap;
    private int vitesse;

    public deplacement(int cap, int vitesse) {
        this.cap = cap;
        this.vitesse = vitesse;
    }

    public int getCap() {
        return cap;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;       
    }  
    
    
    
}
