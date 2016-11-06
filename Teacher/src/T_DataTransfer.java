
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

/**
 *
 * @author Shreyan
 */
public class T_DataTransfer implements Runnable{
    
    //Only for Receive
    
    T_ScrollPane_GUI spGUI;
    String msg;
    int index;
    T_Chat_GUI chtGUI;
    private String data=null;
    private int scount=0,fcount=0;
    private String ID="";
    T_DataTransfer(T_ScrollPane_GUI spGUI)
    {
        this.spGUI = spGUI;
    }
    
    @Override
    public void run() {
        receive();
    }
    
    synchronized private void receive()
    {
        String name;
        while(true)
        {
            try {
                data = Teacher_GUI.input.readUTF();
                
                if(data.charAt(0)=='E')
                { 
                    ID = spGUI.tgGUI.input.readUTF(); //Received hostID
                    spGUI.remove(ID);
                    continue;
                }
                else if(data.charAt(0)=='S')
                {
                    name = Teacher_GUI.input.readUTF();
                    spGUI.update(data, name);
                    continue;
                }
                else if(data.charAt(0)=='F')
                {
                    //write receive code for file
                    recieveFile();
                    ID = spGUI.tgGUI.input.readUTF();
                    index = spGUI.getIDIndex(ID);
                    chtGUI = spGUI.chtGUI[index];
                    chtGUI.updateTextArea("New file recieved");
                    continue;
                    
                }
                
                else if(data.charAt(0)=='A')
                {
                    recieveScreenshot();
                    
                }
                else if(data.charAt(0)=='M'){
                    msg = data.substring(1, data.length());
                    ID = Teacher_GUI.input.readUTF();
                    index = spGUI.getIDIndex(ID);
                    
                    msg = spGUI.hostNames[index] + " - " + msg;
                    
                    //Get corresponding chat panel
                    chtGUI = spGUI.chtGUI[index];
                    chtGUI.updateTextArea(msg);
                    
                }
                
            } catch (IOException ex) {
                System.out.println("IO Exception : "+ex);
                break;
            }
            
            //Get the specific index of the user
            
        }
    }
    
    private void recieveFile() {
        
        String file=new String();
        file=data.substring(1);
        char[]rbuff=new char[file.length()];
        rbuff=file.toCharArray();
        
        //writing the contents
        try{
            //destination for the recieved file
            String dest="D:\\Download\\Files"+ fcount++ +".txt";
            //creates the output file
            FileOutputStream fos = new FileOutputStream(dest);
            Writer writer = new OutputStreamWriter(fos);
            writer.write(rbuff);
            writer.close();
        }catch(IOException e){
            System.out.println("Unable to write to file");
            e.printStackTrace(System.out);
        }
    }
    
    
    private void recieveScreenshot() {
        try{
            BufferedImage inputImage;
            System.out.println("receivess");
            
            inputImage=ImageIO.read(Teacher_GUI.input);
            int width=inputImage.getWidth();
            int height=inputImage.getHeight();
            BufferedReader br=new BufferedReader(new InputStreamReader(Teacher_GUI.input));
            int n;
            
            while((n=br.read())!=(char)'A'){
            System.out.println(n);
            }
            
            BufferedImage outputImage=new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g=outputImage.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g.clearRect(0, 0, width, height);
            g.drawImage(inputImage, 0, 0, width, height, null);
            
            g.dispose();
            //save the image
            
            
            String path1 = "D:\\Download\\";
            String path2="Capture"+ System.currentTimeMillis() +".png";
            String path=path1+path2;
            File dest=new File(path);
            ImageIO.write(outputImage,"png",dest);
            
            ID = spGUI.tgGUI.input.readUTF();
            index = spGUI.getIDIndex(ID);
            System.out.println(ID+index);
            chtGUI = spGUI.chtGUI[index];
            System.out.println(ID+index);
            chtGUI.updateTextArea("New image recieved:");
            //to display image after recieving
            JLabel picLabel=new JLabel(new ImageIcon(inputImage));
            JOptionPane.showMessageDialog(null, picLabel, "About", JOptionPane.PLAIN_MESSAGE, null);
        }catch(IOException e){
            System.out.println("Failed to recieve image");
            e.printStackTrace(System.out);
        }
    }
}
