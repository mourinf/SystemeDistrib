package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author leclairn
 */
public class ClientUdpWithThread {

    HashMap<Integer, DatagramPacket> mem;
    HashMap<Integer, DatagramPacket> memAttente;
    DatagramSocket socket;
    public int port;
    public boolean arret=false;
    Timer timerEnvoi;
    Timer timerDetect;
    long debut;
    int num;
    //InetAddress address;
    
    public static void main(String args[]){
        ClientUdpWithThread client = new ClientUdpWithThread(Integer.parseInt(args[0]));
        client.Communiquer();
    }

    public ClientUdpWithThread() {
        this.mem = new HashMap<>();
        this.memAttente=new HashMap<>();
        this.timerDetect = new Timer();
        this.timerEnvoi = new Timer();
        this.num = 1;
        try {
            this.socket = new DatagramSocket(
                    Constantes.PORT_CLIENT, InetAddress
                            .getByName("localhost"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ClientUdpWithThread(int port) {
        this.mem = new HashMap<>();
        this.memAttente=new HashMap<>();
        this.timerDetect = new Timer();
        this.timerEnvoi = new Timer();
        this.port = port;
        this.num = 1;
        try {
            this.socket = new DatagramSocket(
                    port, InetAddress
                            .getByName("localhost"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Communiquer() {
        Thread reception = new Thread(new RecevoirMessageClient(this,this.socket));
        //Thread lecture = new Thread(new LireMessage(this));
        timerEnvoi.schedule(new EnvoyerMessageClient(this, this.socket),0 , 1000);
        timerDetect.schedule(new DetecteurFautes(this),0, 10000);
        //lecture.start();
        reception.start();
    }
    
    void send(String s) throws UnknownHostException{
            DataUdp data = new DataUdp(s, num);
            byte[] buffer = data.toByteArray();
            mem.put(num, new DatagramPacket(buffer, buffer.length, InetAddress.getByName(Constantes.HOST_SERVER), Constantes.PORT_SERVER));
            num++;
    }
}
