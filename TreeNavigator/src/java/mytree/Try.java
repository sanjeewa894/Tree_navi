/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytree;

import static java.lang.System.out;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sanjeewa
 */
public class Try {
    
    public static void main(String[] args){
        
         TreeDataRetrieve tree = new TreeDataRetrieve();
        Map<String,  String> listOfNode = new HashMap<String,String >();
        Map<String, String > listOfName = new HashMap<String, String>(); //withnode
  
            tree.initConnection();
            listOfName = tree.returnMapName();
            listOfNode = tree.returnMapNode();
       // out.println(listOfName);
              out.println(listOfNode);
    }
    
}
