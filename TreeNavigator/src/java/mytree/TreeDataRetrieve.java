/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytree;

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
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import sun.misc.Version;
import org.mozilla.javascript.NativeObject;


/**
 *
 * @author sanjeewa
 */
public class TreeDataRetrieve {
    
    private final Map<String,  String> listOfNode = new HashMap<String,String >();
      private final Map<String, String > listOfName = new HashMap<String, String>(); //withnode
  
    
    public void initConnection() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/NethFM";
        String user = "root";
        String password = "12188";

        try {


            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = (Connection)DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM ACTIVITY_TAB;");
            ResultSetMetaData md = rs.getMetaData();

            int columns = md.getColumnCount(); //count of the column in table

            while( rs.next()) {//get first result

              //  System.out.println(rs.getString(1)+", "+ rs.getString(2) +", "+ rs.getString(6));//coloumn 1
                listOfNode.put(rs.getString(1), rs.getString(6));
                listOfName.put(rs.getString(1),  rs.getString(2) );
            }

          

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TreeDataRetrieve.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(TreeDataRetrieve.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(TreeDataRetrieve.class.getName()).log(Level.SEVERE, null, ex);
        } finally {  //close the connection
            try {
                if (rs != null ) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(Version.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
    
    public Map returnMapNode(){
        return listOfNode;
    }
     public Map returnMapName(){
        return listOfName;
    }
       
     private Object getJSLocalizedValueMap() {

    Map<String, String> langSel = new HashMap<String, String>();
    langSel.put("en", "true");
    langSel.put("de", "false");
    langSel.put("fr", "false");

    //Now convert this map into Javascript Map
    NativeObject nobj = new NativeObject();
    ScriptEngineManager factory = new ScriptEngineManager();
    ScriptEngine engine = factory.getEngineByName("javascript");
    for (Map.Entry<String, String> entry : langSel.entrySet()) {
        nobj.defineProperty(entry.getKey(), entry.getValue(), NativeObject.READONLY);
    }
    engine.put("langSel", nobj);
    return langSel;
}
    
}
