/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UDP.serveur;

import UDP.DataUdp;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;

/**
 *
 * @author Florian
 */
public class Application {

    File f = new File("messagesDelivres.txt");
    ServeurUdp serveur;
    int nbTraitement = 1;

    public Application(ServeurUdp serveur) {
        this.serveur = serveur;
    }

    public void traiter(DataUdp data) {
        System.out.println("Le " + data.toString() + " a bien été délivré. Nb messages traités: " + nbTraitement);
        nbTraitement++;
        try {
            FileWriter fw = new FileWriter(f, true);
            fw.write(data.toString());
            fw.write("\r\n");

            fw.close();
        } catch (IOException exception) {
            System.out.println("Erreur lors de la lecture : " + exception.getMessage());
        }
    }

}
