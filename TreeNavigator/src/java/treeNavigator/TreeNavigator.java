/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treeNavigator;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.MappedTreeStructure;
import com.google.gson.*;
import static java.lang.System.out;
import jdk.nashorn.internal.runtime.Version;
/**
 *
 * @author sanjeewa
 * @param <T>
 */
public  class TreeNavigator  {

    private static Map<String, String> listOfName = new HashMap<String, String>();
      
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        MappedTreeStructure<String> tree = new MappedTreeStructure<String>();

     
      
      Gson g = new Gson();      
      tree= tree.initConnection();
      System.out.println(tree.getChildren("2") + " " + tree);
      tree.getList();
    }
    
}
