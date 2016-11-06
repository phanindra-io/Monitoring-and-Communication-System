

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Phanindra V.V.D
 */
public class Exchange_ID implements Runnable{

    String ID, hostIDS[], hostNames[], name;
    Key key;
    SVR_DataTransfer hostDT[], DT;
    
    Exchange_ID(SVR_DataTransfer obj, String ID,
            String name, Key key)
    {
        DT = obj;
        this.ID = ID;        
        this.key = key;
        this.name = name;
    }

    @Override
    public void run() {
        
        int n;
        
        //Get no. of connections opposite type connections
        n = key.getNumber();   
        DT.messageTransfer(String.valueOf(n));

        
        if(n<1)
            return;
        
        hostDT = key.getDTObjS();
        hostIDS = key.getIDS();
        hostNames = key.getNames();
        
        for(int i=0; i<n; i++)
        {
            hostDT[i].messageTransfer(ID);
            hostDT[i].messageTransfer(name);
            DT.messageTransfer(hostIDS[i]);
            DT.messageTransfer(hostNames[i]);
        }
        
        
        
    }
    
}
