package UDP;

/**
 * Nicolas Leclaire
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DataUdp implements Serializable
{
    /**
     * serial uid
     */
    private static final long serialVersionUID = 3258698714674442547L;

    /**
     * Stucture à envoyer
     * @param message
     */
    public DataUdp (String message, int numeroPacket)
    {
        this.message=message;
        this.num=numeroPacket;
    }
    public String message;
    public int num;
    
    @Override
    public String toString(){
        return "message numéro " + num + " : " + message;
    }
    
    /**
     * Serialization de la classe
     * @return La classe convertit en array de bytes.
     */
    public byte [] toByteArray()
    {
        try
        {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream (bytes);
            os.writeObject(this);
            os.close();
            return bytes.toByteArray();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Deserialization d'un byte[] en DataUdp
     * @param bytes un array de bytes
     * @return Un DataUdp.
     */
    public static DataUdp fromByteArray (byte [] bytes)
    {
        try
        {
            // Se realiza la conversion usando un ByteArrayInputStream y un
            // ObjectInputStream
            ByteArrayInputStream byteArray = new ByteArrayInputStream(bytes);
            ObjectInputStream is = new ObjectInputStream(byteArray);
            DataUdp aux = (DataUdp)is.readObject();
            is.close();
            return aux;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
