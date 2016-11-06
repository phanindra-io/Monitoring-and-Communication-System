
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Phanindra V.V.D
 */
public class Connection{
    
    private ServerSocket server;
    private Socket socket;
    private Client obj;
    private Thread t;
    private Key keyP, keyS;


    private void startRunning() {
        try{
            server = new ServerSocket(4555);
            keyP = new Key();
            keyS = new Key();
            waitForConnections();          
        }
        catch(IOException e)
        {
            e.printStackTrace(System.out);
        }
        
    }
    
    private void waitForConnections() throws IOException
    {
        while(true){
            
            socket = server.accept();
            
            obj = new Client(socket, keyP, keyS);
            
            t = new Thread(obj);
            
            t.start();         
        }
    }
    
    public static void main(String ar[])
    {
        new Connection().startRunning();
    }
    
    
}
