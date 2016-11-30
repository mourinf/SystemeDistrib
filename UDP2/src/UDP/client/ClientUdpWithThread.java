package UDP.client;

import UDP.Constantes;
import UDP.DataUdp;
import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    File f = new File("messagesEnvoyes.txt");
    DatagramSocket socket;
    public boolean arret = false;
    boolean activeDetect = true;
    Timer timerEnvoi;
    Timer timerDetect;
    int num;
    Semaphore sem;
    long latence;
    long nbMessage;
    boolean first = true;

    public ClientUdpWithThread(long nb) {
        this.mem = new HashMap<>();
        this.nbMessage = nb;
        this.timerDetect = new Timer("Detect");
        this.timerEnvoi = new Timer("Envoi");
        this.num = 1;
        this.sem = new Semaphore(1);
        this.latence = 0;
        try {
            this.socket = new DatagramSocket(
                    Constantes.PORT_CLIENT,
                    InetAddress.getByName(Constantes.HOST_CLIENT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Communiquer() {
        Thread reception = new Thread(new RecevoirMessageClient(this,
                this.socket), "Reception");
        timerEnvoi.schedule(new EnvoyerMessageClient(this, this.socket),
                0, 300);
        timerDetect.schedule(new DetecteurFautes(this), 0, 10000);
        reception.start();
    }

    void send(byte[] bytes) throws UnknownHostException {
        DataUdp data = new DataUdp(bytes, num);
        byte[] buffer = data.toByteArray();
        try {

            FileWriter fw = new FileWriter(f, true);
            fw.write(String.valueOf(data.toString()));
            fw.write("\r\n");

            fw.close();

            this.sem.acquire();
            mem.put(num, new DatagramPacket(buffer, buffer.length,
                    InetAddress.getByName(Constantes.HOST_SERVER),
                    Constantes.PORT_SERVER));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.sem.release();
        }
        num++;
    }
}
