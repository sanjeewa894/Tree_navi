package main;

import com.google.gson.Gson;
//import com.mysql.jdbc.Connection;
import java.io.Serializable;
import java.util.List;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.runtime.Version;
//import tree.MutableTree;

public class MappedTreeStructure<N extends Serializable>  {

  

    private final Map<N, N> nodeParent = new HashMap<N, N>();
    private final LinkedHashSet<N> nodeList = new LinkedHashSet<N>();
    private final Map<N, String> listOfName = new HashMap<N, String>();
    
      
    public MappedTreeStructure initConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
    
          MappedTreeStructure<String> tree = new MappedTreeStructure<String>();
       //   Gson g = new Gson();
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

                //System.out.println(rs.getString(1)+", "+ rs.getString(2) +", "+ rs.getString(6));//coloumn 1
                tree.add(rs.getString(6), rs.getString(1),rs.getString(2));
              //  listOfName.put(rs.getString(1),  rs.getString(2) );
            }
/*
            System.out.println( tree);
            //tree.add("A", "B");
            
            System.out.println(g.toJson(tree));*/


        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(Version.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

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
    return tree;
    }
       
    private void checkNotNull(N node, String parameterName) {
        if (node == null)
            throw new IllegalArgumentException(parameterName + " must not be null");
    }

   // @Override
    public boolean add(N parent, N node,String des) {
        checkNotNull(parent, "parent");
        checkNotNull(node, "node");
        
        //set root as promotion
        if(listOfName.get("0") == null){
            listOfName.put((N)"0" ,  "Promotion" );
        }    
        
        // check for cycles
        N current = parent;
        do {
            if (node.equals(current)) {
                throw new IllegalArgumentException(" node must not be the same or an ancestor of the parent");
            }
        } while ((current = getParent(current)) != null);

        boolean added = nodeList.add(node);
        nodeList.add(parent);
        nodeParent.put(node, parent);
        listOfName.put(node, des);   //add activity name to map.
        return added;
    }

    //@Override
    public boolean remove(N node, boolean cascade) {
        checkNotNull(node, "node");

        if (!nodeList.contains(node)) {
            return false;
        }
        if (cascade) {
            for (N child : getChildren(node)) {
                remove(child, true);
            }
        } else {
            for (N child : getChildren(node)) {
                nodeParent.remove(child);
            }
        }
        nodeList.remove(node);
        return true;
    }

    //@Override
    public List<N> getRoots() {
        return getChildren(null);
    }

    //@Override
    public N getParent(N node) {
        checkNotNull(node, "node");
        return nodeParent.get(node);
    }

    //@Override
    public List<N> getChildren(N node) {
        List<N> children = new LinkedList<N>();
        for (N n : nodeList) {
            N parent = nodeParent.get(n);
            if (node == null && parent == null) {
                children.add(n);
            } else if (node != null && parent != null && parent.equals(node)) {
                children.add(n);
            }
        }
        return children;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();      
        dumpNodeStructure(builder, null, "** ");
        return builder.toString();
    }

    private void dumpNodeStructure(StringBuilder builder, N node, String prefix) {
        String namePage;
        if (node != null) {
            builder.append(prefix);
            namePage = listOfName.get(node);
            
            if(namePage != null )
                builder.append(namePage); //add activity name 
            //builder.append(node.toString()); //ADD node number 
            //System.out.println(node+ listOfName.get("0") +" " + namePage + "  " +"builder");
            builder.append('\n');
            prefix = "    " + prefix;
        
        }
        for (N child : getChildren(node)) {
            dumpNodeStructure(builder, child, prefix);
                   
        }
 
    }
    
    public void getList(){
        
       System.out.println( nodeParent);
    }
    
}