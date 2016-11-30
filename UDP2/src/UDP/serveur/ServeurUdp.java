package UDP.serveur;

/**
 * Nicolas Leclaire
 */
import UDP.Constantes;
import UDP.DataUdp;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Serveur Udp
 *
 * @author leclairn
 */
public class ServeurUdp {

    HashMap<Integer,Integer> messagesRecus;
    Application application;

    /**
     * Programme
     *
     * @param args
     */
    public static void main(String[] args) {

        ServeurUdp serveur = new ServeurUdp();
        serveur.serveurStart();
    }

    /**
     * Serveur Udp recevant et acquittant les messages
     */
    public ServeurUdp() {
        messagesRecus = new HashMap<>();
        application = new Application(this);
    }

    public void serveurStart() {
        DataUdp ack;
        try {
            // Socket
            DatagramSocket socket = new DatagramSocket(
                    Constantes.PORT_SERVER, InetAddress
                            .getByName("localhost"));

            // Packet de reception
            DatagramPacket dataReceived = new DatagramPacket(new byte[10000],
                    10000);

            while (true) {
                // Recevoir un packet
                socket.receive(dataReceived);
                System.out.print("Reçu data de "
                        + dataReceived.getAddress().getHostName() + " : ");

                // Deserializer le packet
                DataUdp data = DataUdp.fromByteArray(dataReceived.getData());
                System.out.println(data.toString());
                if ((!messagesRecus.containsKey(data.num))&& data.num!=-1) {
                    messagesRecus.put(data.num, data.num); //on rempli la memoire contenant les numero de messages reçus et on ajoute le message à la memoire des messages à délivrer
                    application.traiter(data);
                }
                //acquittement
                ack=new DataUdp("ACK".getBytes(), data.num );
                DatagramPacket dataSend = new DatagramPacket(ack.toByteArray(),
                        ack.toByteArray().length, dataReceived.getAddress(),
                        dataReceived.getPort());
                socket.send(dataSend);
                System.out.println("renvoi");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
