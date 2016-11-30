/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP.serveur;

import UDP.DataUdp;
import java.net.DatagramPacket;

/**
 *
 * @author Florian
 */
public class Application {

    ServeurUdp serveur;
    int nbTraitement=1;

    public Application(ServeurUdp serveur) {
        this.serveur = serveur;
    }
    
    
    public void traiter(DataUdp data){
        System.out.println("Le "+ data.toString()+" a bien été délivré. Nb messages traités: " + nbTraitement);
        nbTraitement++;
    }
    
}
