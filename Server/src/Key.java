

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Phanindra V.V.D
 */
public class Key {
    
    String id[]=new String[100];
    String names[] = new String[100];
    int count, tot, i;
    SVR_DataTransfer DT[];
    
    public Key()
    {
        count = 0;
        tot = 0;
        //inititalise 100 output streams for 100 clients
        DT = new SVR_DataTransfer[100];
    }
        
    synchronized String setKey(int i, SVR_DataTransfer obj, String name) {
        
        if(i==1)
        {
            id[tot] = "P" + String.valueOf(count);
        }
        else
        {
            id[tot] = "S" + String.valueOf(count);
        }      
        
        
        
        DT[tot] = obj;
        this.names[tot] = name;
        
        count++;
        return id[tot++];
    }
    
    void remove(String s)
    {
        for(i=0; i<tot; i++)
        {
            if(s.equals(id[i]))
                break;
        }
        
        
        for(;i<tot-1; i++)
        {
            id[i] = id[i+1];
            names[i] = names[i+1];
        }
        
        tot--;
    }
        
    SVR_DataTransfer getDTObj(String s)
    {
        for(i=0; i<tot; i++)
        {
            if(s.equals(id[i]))
                break;
        }
        return DT[i];
    }
    
    SVR_DataTransfer[] getDTObjS()
    {        
        return DT;
    }
    
    String getName(String s)
    {
        for(i=0; i<tot; i++)
        {
            if(s.equals(id[i]))
                break;
        } 
        
        return names[i];
    }
    
    String[] getIDS()
    {
        return id;
    }
    
    String[] getNames()
    {
        return names;
    }
    
    int getNumber()
    {
        //Returns no.of connections
        return tot;
    }
    
    
}
