
import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
 * @author Phanindra V.V.D and Shreyan
 */
public class S_DataTransfer implements Runnable{
    
    //Only for Receive
    
    S_ScrollPane_GUI spGUI;
    String msg;
    int index;
    S_Chat_GUI chtGUI;
    private String data;
    //to prevent the files from getting overidden
    private int scount=0,fcount=0;
    private String fileName="D:\\Download\\";
    private String dstID;
    private String ID="";
   // private String screenie_path="F:\\SE project\\Downloaded\\Capture"+ System.currentTimeMillis() +".png";
    S_DataTransfer(S_ScrollPane_GUI spGUI)
    {
        this.spGUI = spGUI;
    }
    
    @Override
    public void run() {
        receive();
    }
    
    synchronized private void receive()
    {
        String  name;                
        while(true)
        {
            //recieving data
            try {
                data = spGUI.sdtGUI.input.readUTF();
                System.out.println("Waiting for data");
                System.out.println(data);
                if(data.charAt(0)=='E')
                { 
                    ID = spGUI.sdtGUI.input.readUTF(); //Received hostID
                
                    spGUI.remove(ID);
                    
                    continue;
                }
                else if(data.charAt(0)=='P') //check for key
                {
                    name=spGUI.sdtGUI.input.readUTF();
                    spGUI.update(data,name);
                    continue;
                }              
                else if(data.charAt(0)=='F'){
                    // recieve files
                    recieveFile();
                    ID = spGUI.sdtGUI.input.readUTF();
                    
                    index = ID.charAt(1) - '0';
                    chtGUI = spGUI.chtGUI[index];
                    chtGUI.updateTextArea("New file recieved");
                    continue;
                }
                else if(data.charAt(0)=='A'){
                    recieveScreenshot();
                    continue;
                }
                else if(data.charAt(0)=='M'){
                    msg = data.substring(1, data.length());
                    ID = spGUI.sdtGUI.input.readUTF();
                    
                    index = spGUI.getIDIndex(ID);
                    
                    msg = spGUI.hostNames[index] + " - " + msg;
                    chtGUI = spGUI.chtGUI[index];
                    chtGUI.updateTextArea(msg);
                    continue;
                }
                else if(data.charAt(0)=='X'){
                    //System.out.println("Request Recieved");
                    takeScreenshot();
                    //System.out.println("Screenshot taken");
                    dstID=spGUI.sdtGUI.input.readUTF();
                    sendScreenshot();
                    //System.out.println("Screenshot sent");
                }
                
                
            } catch (IOException ex) {
                System.out.println("IO Exception ");
                ex.printStackTrace(System.out);
                break;
            }
            
            catch(InterruptedException e){
                System.out.println("Interrupted Exception ");
                e.printStackTrace(System.out);
                break;
            }

        }
    }
    
    private void recieveFile(){
        String file=new String();
        file=data.substring(1);
        char[]rbuff=new char[file.length()];
        rbuff=file.toCharArray();
        
        //writing the contents
        try{
            //destination for the recieved file
            String dest="D:\\Download\\File"+ fcount++ +".txt";
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
    
    private void recieveScreenshot(){
        try{
            // int width=1366,height=768; /* set the width and height here */
            BufferedImage inputImage;
            
            inputImage=ImageIO.read(Student_GUI.input);
            
            //obtain size of image
            
            int width=inputImage.getWidth();
            int height=inputImage.getHeight();
            BufferedReader br=new BufferedReader(new InputStreamReader(Student_GUI.input));
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
            System.out.println("Write Successful");
            
            ID = spGUI.sdtGUI.input.readUTF();
           
            index = spGUI.getIDIndex(ID);
            
            chtGUI = spGUI.chtGUI[index];
            chtGUI.updateTextArea("Image recieved");
            JLabel picLabel=new JLabel(new ImageIcon(inputImage));
            JOptionPane.showMessageDialog(null, picLabel, "About", JOptionPane.PLAIN_MESSAGE, null);
        }catch(IOException e){
            System.out.println("Failed to recieve image");
            e.printStackTrace(System.out);
        }
    }
    
    private void takeScreenshot(){
        
        try {
            
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            
            ImageIO.write(capture, "png", new File(fileName+"Monitor.png"));
            System.out.println("Screenshot taken");
        } catch (IOException ex) {
            
            System.out.println(ex);
            
        } catch (AWTException ex) {
            
            System.out.println(ex);
            
        } catch(Exception e){
            System.out.println(e);
        }
        
    }
    
    private void sendScreenshot() throws InterruptedException{
        try{
            spGUI.sdtGUI.output.writeUTF("SCREENSHOT");
            spGUI.sdtGUI.output.writeUTF(dstID);
            BufferedImage img = ImageIO.read(new File(fileName+"Monitor.png"));
            ImageIO.write(img, "png", spGUI.sdtGUI.output);
            img.flush();
            //spGUI.sdtGUI.output.writeUTF("Ping");
            
            //Thread.sleep(1000);
            //removing the screenshot from the student's PC
            File f=new File(fileName+"Monitor.png");
            f.delete();
            System.out.println("Successful");
        }catch(IOException e){
            System.out.println("Unable to read image");
            e.printStackTrace(System.out);
        }
    }
}
