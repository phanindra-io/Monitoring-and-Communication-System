
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.imageio.ImageIO;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 *
 * @author Phanindra V.V.D and Shreyan
 */
public class Exchange_Data implements Runnable{
    
    DataInputStream input;
    String msg, srcID;
    Key key;
    SVR_DataTransfer DT;
    Socket s;
    Client client;
    
    public Exchange_Data(Socket s,DataInputStream input, String ID, Key key, Client c)
    {
        this.input = input;
        srcID = ID;
        this.key = key;
        this.s=s;
        client = c;
    }
    
    @Override
    public void run() {
        try {
            Transfer();
        } catch (IOException ex) {
            System.out.println("Connection Closed : "+srcID);
            //closeConn();
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    private void closeConn()
    {
        SVR_DataTransfer hostDT[];
        int n;
        
        hostDT = key.getDTObjS();
        n = key.getNumber();
                
        while(n>0)
        {
            hostDT[n-1].messageTransfer("Exit");
            hostDT[n-1].messageTransfer(srcID);
            n--;
        }
        
        
        if(srcID.charAt(0)=='S')
        {
            client.keyS.remove(srcID);
        }
        else
            client.keyP.remove(srcID);
        
    }
    
    void closeStreams() throws IOException
    {
        //need to write call
        input.close();
        client.output.close();
        s.close();
    }
    
    synchronized private void Transfer() throws IOException, InterruptedException {
        
        String dstID, type;
        
        while(true)
        {
            type = input.readUTF();
            
            if(type.equals("exit"))
            {
                closeConn();
                break;
            }
            
            switch (type) {
                case "MESSAGE":
                    msg = input.readUTF();
                    dstID = input.readUTF();
                    System.out.println(type+msg+dstID);
                    //Get corresponding DataTransfer object
                    DT = key.getDTObj(dstID);
                    DT.messageTransfer(msg);
                    DT.messageTransfer(srcID);
                    break;
                case "MONITOR":
                    dstID=input.readUTF();
                    DT=key.getDTObj(dstID);
                    DT.messageTransfer("X");
                    DT.messageTransfer(srcID);
                    break;
                case "FILE":
                    msg=input.readUTF();
                    dstID=input.readUTF();
                    DT = key.getDTObj(dstID);
                    DT.messageTransfer(msg);
                    DT.messageTransfer(srcID);
                    break;
                case "SCREENSHOT":
                case "IMAGE":
                    dstID = input.readUTF();
                    DT = key.getDTObj(dstID);
                    //Get corresponding DataTransfer object
                    DT.messageTransfer("A");
                    BufferedImage rec=ImageIO.read(input);
                    DT.imageTransfer(rec);
                    BufferedReader br=new BufferedReader(new InputStreamReader(input));
                    int n;
                    while((n=br.read())!=0){
                        System.out.println((char)n);
                        System.out.println(".");
                    }   System.out.println("Out of loop"); 
                    
                    Thread.sleep(50); //For timing
                    DT.messageTransfer("A");
                    Thread.sleep(50); //For timing
                    DT.messageTransfer(srcID);
                    break;
            }
        }
        
    }
    
}
