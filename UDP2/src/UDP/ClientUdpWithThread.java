package UDP;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    DatagramSocket socket;
    public InetAddress address;
    public int port;
    public boolean arret = false;
    Timer timerEnvoi;
    Timer timerDetect;
    int num;
    //InetAddress address;
    Semaphore sem;

    public static void main(String args[]) throws UnknownHostException {
        ClientUdpWithThread client;
        client = new ClientUdpWithThread(
                Integer.parseInt(args[0]), InetAddress.getByName(args[1]));
        client.Communiquer();
    }

    public ClientUdpWithThread() {
        this.mem = new HashMap<>();
        this.timerDetect = new Timer("Detect");
        this.timerEnvoi = new Timer("Envoi");
        this.num = 1;
        try {
            this.socket = new DatagramSocket(
                    Constantes.PORT_CLIENT, InetAddress
                    .getByName("localhost"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClientUdpWithThread(int port, InetAddress address) {
        this.mem = new HashMap<>();
        this.timerDetect = new Timer("Detect");
        this.timerEnvoi = new Timer("Envoi");
        this.port = port;
        this.num = 1;
        this.sem = new Semaphore(1);
        //this.verrou = new ReentrantLock();
        try {
            this.socket = new DatagramSocket(
                    port, address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Communiquer() {
        Thread reception = new Thread(new RecevoirMessageClient(this, this.socket), "Reception");
        //Thread lecture = new Thread(new LireMessage(this));
        timerEnvoi.schedule(new EnvoyerMessageClient(this, this.socket), 0, 300);
        timerDetect.schedule(new DetecteurFautes(this), 0, 10000);
        //lecture.start();
        reception.start();
    }

    void send(byte[] bytes) throws UnknownHostException {
        DataUdp data = new DataUdp(bytes, num);
        byte[] buffer = data.toByteArray();
        try {
            this.sem.acquire();
            mem.put(num, new DatagramPacket(buffer, buffer.length, InetAddress.getByName(Constantes.HOST_SERVER), Constantes.PORT_SERVER));
        } catch (Exception e) {
            
        } finally {
            this.sem.release();
        }
        num++;
    }
}
