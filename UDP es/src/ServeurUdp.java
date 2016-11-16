/**
 * Nicolas Leclaire
 */
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Serveur Udp 
 * 
 * @author leclairn
 */
public class ServeurUdp
{

    /**
     * Programme
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        new ServeurUdp();
    }

    /**
     * Serveur Udp recevant et acquittant les messages
     */
    public ServeurUdp()
    {
        try
        {

            // Socket
            DatagramSocket socket = new DatagramSocket(
                    Constantes.PUERTO_DEL_SERVIDOR, InetAddress
                            .getByName("localhost"));

            // Packet de reception
            DatagramPacket dataReceived = new DatagramPacket(new byte[100], 100);
            
            // Bucle infinito.
            while (true)
            {
                // Recvoir un packet
                socket.receive(dataReceived);
                System.out.print("Reçu data de "
                        + dataReceived.getAddress().getHostName() + " : ");
                
                // Deserializer le packet
                DataUdp data = DataUdp.fromByteArray(dataReceived.getData());
                System.out.println(data.toString());
                
                DatagramPacket dataSend = new DatagramPacket(dataReceived.getData(),
                    dataReceived.getLength(), dataReceived.getAddress(), dataReceived.getPort());
                
                socket.send(dataSend);
                
                
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
