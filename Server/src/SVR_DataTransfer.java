
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 *
 * @author Phanindra V.V.D
 */
public class SVR_DataTransfer {
    
    DataOutputStream output;
    
    SVR_DataTransfer(DataOutputStream obj)
    {
        output = obj;
    }
    
    synchronized void messageTransfer(String s)
    {
        try {
            output.writeUTF(s);
            System.out.println("Message - "+s);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    synchronized void fileTransfer(String s)
    {
        //file Transfer code
        try {
            output.writeUTF(s);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    synchronized void imageTransfer(BufferedImage img){
        try{
            
            //BufferedImage img=ImageIO.read(rec);
            boolean write = ImageIO.write(img, "png", output);
            img.flush();
            if(write==true){
                Client.setupStreams();
            }
            else
                System.out.println("Failed to send message");
        }catch(IOException e){
            System.out.println("Unable to transfer screenshot");
            e.printStackTrace(System.out);
        }
    }
}
