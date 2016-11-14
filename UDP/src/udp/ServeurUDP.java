/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
import udp.Data;


/**
 *
 * @author mourinf
 */
public class ServeurUDP {
    
  final static int port = 2042;
  final static int taille = 1024;
  static byte buffer[] = new byte[taille];

  public static void main(String argv[]) throws Exception {
    DatagramSocket socket = new DatagramSocket(port);
    String donnees = "";
    String message = "";
    int taille = 0;

    System.out.println("Lancement du serveur");
    while (true) {
      DatagramPacket paquet = new DatagramPacket(buffer, buffer.length);
      DatagramPacket envoi = null;
      socket.receive(paquet);

      System.out.println("\n"+paquet.getAddress());
      taille = paquet.getLength();
      donnees = ((Data)deserialize(paquet.getData())).toString();    
      System.out.println("Donnees reçues = "+donnees);

      message = "Bonjour "+donnees;
      System.out.println("Donnees envoyees = "+message);
      envoi = new DatagramPacket(message.getBytes(), 
        message.length(), paquet.getAddress(), paquet.getPort());
      socket.send(envoi);
    }
  }
  
   public static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(obj);
                o.close();
            }
            return b.toByteArray();
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                Object obj= o.readObject();
                o.close();
                return obj;
            }
        }
    }
}
