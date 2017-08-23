/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetaccov;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.System.exit;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author George Harik
 */
public class AvionClient {

    int altitudeMax = 35000;
    int altitudeMin = 0;
    int vitesseMax = 1000;
    int vitesseMin = 200;
    int pause = 2000;
    char[] numero_vol;
    String NumeroAvion = "";
    static Socket socket;
    String changements = "Pas de changement";

    static final String Letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static final String Numbers = "0123456789";

    static SecureRandom rnd = new SecureRandom();
    //Random rand = new Random();

    String randomString() {
        StringBuilder sb = new StringBuilder(5);
        for (int i = 0; i < 2; i++) {
            sb.append(Letters.charAt(rnd.nextInt(Letters.length())));
        }
        for (int i = 0; i < 3; i++) {
            sb.append(Numbers.charAt(rnd.nextInt(Numbers.length())));
        }

        return sb.toString();

        /*int min = 0;
         int max = 9;
        
         numero_vol = new char[5];

         numero_vol[0] = (char) ((random() % 26) + 'A');
         numero_vol[1] = (char) ((random() % 26) + 'A');
         numero_vol[2] = (char) rand.nextInt(((max - min) + 1) + min);
         rand = new Random();
         numero_vol[3] = (char) rand.nextInt(((max - min) + 1) + min);
         rand = new Random();
         numero_vol[4] = (char) rand.nextInt(((max - min) + 1) + min);
        
         return numero_vol.toString();*/
    }

    private coordonnees coord;
    private deplacement dep;

    public coordonnees getCoord() {
        return coord;
    }

    public deplacement getDep() {
        return dep;
    }

    public void setCoord(coordonnees coord) {
        this.coord = coord;
    }

    public void setDep(deplacement dep) {
        this.dep = dep;
    }

    Boolean ouvrir_communication() {

        try {
            socket = new Socket("localhost", 2009);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(AvionClient.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    void fermer_communication() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(AvionClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    String envoyer_caracteristiques() {
        // fonction à implémenter qui envoie l'ensemble des caractéristiques
        // courantes de l'avion au gestionnaire de vols

        return "Avion:" + NumeroAvion + " - X:" + coord.getX() + "-Y:" + coord.getY() + "-Z:" + coord.getAltitude() + "-Vitesse:" + dep.getVitesse() + "-Cap:" + dep.getCap() + "-Notes:" + changements;
    }

    public AvionClient() {

       // int nextInt = rnd.nextInt(Numbers.length());
        int x = (int) (1000 + rnd.nextInt(Numbers.length()) % 1000);
        int y = (int) (1000 + rnd.nextInt(Numbers.length()) % 1000);
        int z = (int) (900 + rnd.nextInt(Numbers.length()) % 100);

        int cap = (int) (rnd.nextInt(Numbers.length()) % 360);
        int vitesse = (int) (600 + rnd.nextInt(Numbers.length()) % 200);

        NumeroAvion = randomString();

        coord = new coordonnees(x, y, z);
        dep = new deplacement(cap, vitesse);

    }

    // modifie la valeur de l'avion avec la valeur passé en paramètre
    void changer_vitesse(int vitesse) {
        if (vitesse < 0) {
            dep.setVitesse(0);
        } else if (vitesse > vitesseMax) {
            dep.setVitesse(vitesseMax);
        } else {
            dep.setVitesse(vitesse);
        }
    }

    // modifie le cap de l'avion avec la valeur passée en paramètre
    void changer_cap(int cap) {
        if ((cap >= 0) && (cap < 360)) {
            dep.setCap(cap);
        }
    }

    // modifie l'altitude de l'avion avec la valeur passée en paramètre
    void changer_altitude(int alt) {
        if (alt < 0) {
            coord.setAltitude(0);
        } else if (alt > altitudeMax) {
            coord.setAltitude(altitudeMax);
        } else {
            coord.setAltitude(alt);
        }
    }

    void afficher_donnees() {

        // to be changed to display data from server side
        System.out.println("Avion:" + NumeroAvion + "   Localisation:" + coord.getX() + "," + coord.getY() + "," + coord.getAltitude() + "   Vitesse:" + dep.getVitesse() + "   Cap:" + dep.getCap());

    }

    void calcul_deplacement() {
        float cosinus, sinus;
        float dep_x, dep_y, dep_z;
        int nb;

        if (dep.getVitesse() < vitesseMin) {
            System.out.println("Vitesse trop faible : crash de l'avion\n");
            fermer_communication();
            exit(2);
        }
        if (coord.getAltitude() == 0) {
            System.out.println("L'avion s'est ecrase au sol\n");
            fermer_communication();
            exit(3);
        }
        //cos et sin ont un paramétre en radian, dep.cap en degré nos habitudes francophone
        /* Angle en radian = pi * (angle en degré) / 180 
         Angle en radian = pi * (angle en grade) / 200 
         Angle en grade = 200 * (angle en degré) / 180 
         Angle en grade = 200 * (angle en radian) / pi 
         Angle en degré = 180 * (angle en radian) / pi 
         Angle en degré = 180 * (angle en grade) / 200 
         */

        cosinus = (float) cos(dep.getCap() * 2 * Math.PI / 360);
        sinus = (float) sin(dep.getCap() * 2 * Math.PI / 360);

        //newPOS = oldPOS + Vt
        dep_x = cosinus * dep.getVitesse() * 10 / vitesseMin;
        dep_y = sinus * dep.getVitesse() * 10 / vitesseMin;

        // on se d�place d'au moins une case quels que soient le cap et la vitesse
        // sauf si cap est un des angles droit
        if ((dep_x > 0) && (dep_x < 1)) {
            dep_x = 1;
        }
        if ((dep_x < 0) && (dep_x > -1)) {
            dep_x = -1;
        }

        if ((dep_y > 0) && (dep_y < 1)) {
            dep_y = 1;
        }
        if ((dep_y < 0) && (dep_y > -1)) {
            dep_y = -1;
        }

        //printf(" x : %f y : %f\n", dep_x, dep_y);
        coord.setX(coord.getX() + (int) dep_x);
        coord.setY(coord.getY() + (int) dep_y);

        //afficher_donnees();
    }

    void se_deplacer() throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            //while (true) {
            sleep(pause);
            calcul_deplacement();
            //envoyer_caracteristiques();
        }
    }

    public void demarrer() {

        PrintWriter out;
        try {

            if (ouvrir_communication() == true) {
                String caracteristiques = envoyer_caracteristiques();

                out = new PrintWriter(socket.getOutputStream());
                out.println(caracteristiques);

                while (true) {
                    sleep(pause);
                   // out = new PrintWriter(avion.socket.getOutputStream());
                    // out.println(caracteristiques);

                    calcul_deplacement();
                    caracteristiques = envoyer_caracteristiques();
                    out.println(caracteristiques);
                    out.flush();
                }
                //avion.fermer_communication();

            }
        } catch (Exception ex) {
            System.out.println("Fin Avion");
        }

    }

    public static void main(String[] args) throws InterruptedException {

        PrintWriter out;

        try {

            AvionClient avion = new AvionClient();
            if (avion.ouvrir_communication() == true) {

                String caracteristiques = avion.envoyer_caracteristiques();

                out = new PrintWriter(avion.socket.getOutputStream());
                out.println(caracteristiques);
                while (true) {
                    sleep(avion.pause);
                   // out = new PrintWriter(avion.socket.getOutputStream());
                    // out.println(caracteristiques);

                    avion.calcul_deplacement();
                    caracteristiques = avion.envoyer_caracteristiques();
                    out.println(caracteristiques);
                    out.flush();
                }
                //avion.fermer_communication();                
            }

        } catch (Exception ex) {
            System.out.println("Fin Avion");
        }

    }

}
