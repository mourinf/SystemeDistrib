/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udp;

import java.io.Serializable;

/**
 *
 * @author mourinf
 */
public class Data implements Serializable{
    private String message;
    private int numS;

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
}
