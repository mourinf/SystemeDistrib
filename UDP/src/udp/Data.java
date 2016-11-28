/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author mourinf
 */
public class Data implements Serializable{
    private String message;
    private int numS;
    
    private static final long serialVersionUID = 3258698714674442547L;


    public Data(String message, int numS) {
        this.message = message;
        this.numS = numS;
    }

    public String getMessage() {
        return message;
    }

    public int getNumS() {
        return numS;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNumS(int numS) {
        this.numS = numS;
    }
    
    
    @Override
    public String toString(){
        String s= "message: "+ this.message + " numero: "+ this.numS;
        return s;
    }
    
    public byte[] toByteArray() throws IOException{
        try (ByteArrayOutputStream b = new ByteArrayOutputStream()) {
            try (ObjectOutputStream o = new ObjectOutputStream(b)) {
                o.writeObject(this);
                o.close();
            }
            return b.toByteArray();
        }
    }
    
    public static Data fromByteArray(byte[] bytes) throws IOException, ClassNotFoundException{
        try (ByteArrayInputStream b = new ByteArrayInputStream(bytes)) {
            try (ObjectInputStream o = new ObjectInputStream(b)) {
                Object obj= o.readObject();
                o.close();
                return (Data)obj;
            }
        }
    }
    
}
