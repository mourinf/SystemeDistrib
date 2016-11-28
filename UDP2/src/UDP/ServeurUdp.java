package UDP;

/**
 * Nicolas Leclaire
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

/**
 * Serveur Udp
 *
 * @author leclairn
 */
public class ServeurUdp {

    HashMap<Integer, Integer> mem;

    /**
     * Programme
     *
     * @param args
     */
    public static void main(String[] args) {
        new ServeurUdp();
    }

    /**
     * Serveur Udp recevant et acquittant les messages
     */
    public ServeurUdp() {
        try {
            int attente=1;
            // Socket
            DatagramSocket socket = new DatagramSocket(
                    Constantes.PORT_SERVER, InetAddress
                            .getByName("localhost"));

            // Packet de reception
            DatagramPacket dataReceived = new DatagramPacket(new byte[1024], 1024);

            // Bucle infinito.
            while (true) {
                // Recvoir un packet
                socket.receive(dataReceived);
                System.out.print("Reçu data de "
                        + dataReceived.getAddress().getHostName() + " : ");

                // Deserializer le packet
                DataUdp data = DataUdp.fromByteArray(dataReceived.getData());
                System.out.println(data.toString());
                if ( data.num==attente || data.num == -1) {

                    DatagramPacket dataSend = new DatagramPacket(dataReceived.getData(),
                            dataReceived.getLength(), dataReceived.getAddress(), dataReceived.getPort());

                    socket.send(dataSend);
                    System.out.println("renvoi");
                    if(data.num!=-1)
                        attente++;
                } else {
                    //  System.out.println("Deja reçu!");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
