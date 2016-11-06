
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Phanindra V.V.D
 */
public class S_Chat_GUI extends javax.swing.JPanel{

    /**
     * Creates new form chat_GUI
     */
    
    private String msg, dstID;
    private int index;
    private S_ScrollPane_GUI spGUI;
    private final JFileChooser chooser = new JFileChooser();

    public S_Chat_GUI(S_ScrollPane_GUI obj, int i) {
        initComponents();
        
        index = i;
        spGUI = obj;
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chatPanel = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox1 = new javax.swing.JComboBox();

        setLayout(new java.awt.CardLayout());

        chatPanel.setBackground(new java.awt.Color(0, 0, 51));
        chatPanel.setForeground(new java.awt.Color(0, 0, 51));

        jTextField1.setToolTipText("Enter the Message");

        jButton1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "File", "Screenshot" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout chatPanelLayout = new javax.swing.GroupLayout(chatPanel);
        chatPanel.setLayout(chatPanelLayout);
        chatPanelLayout.setHorizontalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatPanelLayout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        chatPanelLayout.setVerticalGroup(
            chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatPanelLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(chatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        add(chatPanel, "card2");
    }// </editor-fold>//GEN-END:initComponents
  
    
    protected void updateTextArea(String msg)
    {
        jTextArea1.append(msg+"\n");
    }
    
   
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        sendMessage();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButton1KeyPressed
        // TODO add your handling code here:
        
        //write Code for send message using enter key        
    }//GEN-LAST:event_jButton1KeyPressed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        jComboBox1 = (JComboBox) evt.getSource();
        Object selected = jComboBox1.getSelectedItem();
        switch (selected.toString()) {
            case "File":
                sendFile();
                break;
            case "Screenshot":
                try {
                    sendImage();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
        }
      
    }//GEN-LAST:event_jComboBox1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JPanel chatPanel;
    protected javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTextArea jTextArea1;
    protected javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
     private void sendMessage()
    {
        msg = jTextField1.getText();
        
        String trim = msg.trim();
        
        if(msg.equals(""))
            return;
        
        jTextField1.setText("");
        dstID = spGUI.hostID[spGUI.index];
        
        try {
            spGUI.sdtGUI.output.writeUTF("MESSAGE");
            spGUI.sdtGUI.output.writeUTF("M"+msg);
            spGUI.sdtGUI.output.writeUTF(dstID);
            System.out.println(msg+dstID);
            msg = spGUI.sdtGUI.name + " - " + msg;
            updateTextArea(msg);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
     //sends the contents of the file in the form of a string
    private void sendFile()
    {
        String source=null;
        try {
           
            
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                source=chooser.getSelectedFile().getAbsolutePath();
            }
            
            if(source==null){
                return;
            }
           dstID = spGUI.hostID[spGUI.index];
            //obtain the location of the file to be sent
            spGUI.sdtGUI.output.writeUTF("FILE");
           
            //read the contents of the file to a character array
            FileInputStream fis=new FileInputStream(source);
            char rbuff[]=new char[fis.available()];
            Reader reader=new InputStreamReader(fis);
            reader.read(rbuff);
            //reader.close();
            
            //send it over the network
            String mainfile=new String(rbuff);
            String id="F";
            String file=id+mainfile;
            spGUI.sdtGUI.output.writeUTF(file);
            spGUI.sdtGUI.output.writeUTF(dstID);
            
        } catch (IOException e) {
            System.out.println("Fail");
            e.printStackTrace(System.out);
        }
        
    }
    
    //sends the screenshot in the form of a string
    private void sendImage() throws InterruptedException {
        String source=null;
//       
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           source= chooser.getSelectedFile().getAbsolutePath();
    }
        if(source==null){
            return;
        }
        
       try{
        dstID = spGUI.hostID[spGUI.index];
        spGUI.sdtGUI.output.writeUTF("IMAGE");
        spGUI.sdtGUI.output.writeUTF(dstID);
        BufferedImage img = ImageIO.read(new File(source));
        ImageIO.write(img, "png", spGUI.sdtGUI.output);
        img.flush();
        spGUI.sdtGUI.output.writeUTF("Ping");
        
        Thread.sleep(100);
       }catch(IOException e){
           System.out.println("Unable to read image");
           e.printStackTrace(System.out);
       }	
    }
    
  
}