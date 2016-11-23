<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*,java.util.StringTokenizer,java.util.*,java.text.*" errorPage="" %> 

<% 
          
             Connection conn =null; 
              Class.forName("org.gjt.mm.mysql.Driver"); 
         try{ 
           conn = DriverManager.getConnection("jdbc:mysql://localhost/NethFM","root","12188"); 
         String tempNodeID=nullconv(request.getParameter("NodeID")); 
         int tempdownline=nullIntconv(nullconv(request.getParameter("downline"))); 
         setConnection(conn,out,tempNodeID,tempdownline); 
         } 
         catch(Exception e) 
         { 
           e.printStackTrace(); 
         } 
%> 

<%! 
public String nullconv(String str) 
{ 
   if(str==null) 
      str=""; 
   else if(str.equalsIgnoreCase("null")) 
      str=""; 
   else if((str.trim()).equals("")) 
      str=""; 
   return str; 
} 
public int nullIntconv(String inv) 
   { 
      if(inv==null) 
      { 
         inv="0"; 
      } 
      else if((inv.trim()).equals("null")) 
      { 
         inv="0"; 
      } 
      else if(inv.equals("")) 
      { 
         inv="0"; 
      } 
       
      return Integer.parseInt(inv); 
   } 


       Connection con=null;    

      PreparedStatement pstmt = null; 
      PreparedStatement pstmt1 = null; 

      ResultSet rs = null; 
      ResultSet rs1 = null; 

      JspWriter out=null; 
      String   NodeID =  "1"; 
       int downline = 0; 
       
   public void setConnection(Connection con,JspWriter ot,String rq,int dwline) 
   { 
      this.con=con; 
      try{ 
      this.out=ot; 
      NodeID=rq; 
      downline=dwline; 
     
       if(NodeID.equals("")) 
         { 
          NodeID="1";  ///////////////////// change this according to your requirement 
         } 
         if(downline==0) 
         { 
          downline=20;   ////////////////////// this is for advance requirement should use according to your requirement 
         } 
      } 
      catch(Exception e) 
      { 
         System.out.println("IO error :"+e); 
      } 
     
    } 
    
    
   public void outputJavascriptForRoot() 
   { 
             
      String queryString =null; 
       
      queryString ="SELECT NodeID, Name, IsFolder, ParentID, Link, level FROM NodesTable WHERE (NodeID='" + NodeID +"')"; 
      try{ 
       
      pstmt=con.prepareStatement(queryString); 
      rs=pstmt.executeQuery(); 
       
      // It should return one and one only 
      while (rs.next()) 
      { 
        out.print("foldersTree = gFld('<i>tree</i>')" + "\n"); 
        out.print("web" + rs.getString("NodeID") + " = insFld(foldersTree, gFld('" + rs.getString("Name") + "[" + rs.getString("NodeID") + "]"  + "','tree.jsp?distibutorid=" + rs.getString("NodeID") + "&downline="+downline+"'))" + "\n"); 
        outputJavascriptForSubFolder(Integer.parseInt(rs.getString("NodeID")), rs.getString("Name"),"web" + rs.getString("NodeID"), rs.getString("Link"), downline); 
      } 
       
      // close the DataReader and Connection 
       
       
    
      } 
      catch(SQLException ex) 
      { 
        System.out.println("sqll eroror :"+ex); 
       } 
       catch(Exception e) 
       { 
          System.out.println("error :"+e); 
       } 
   } 
    
    
 public void outputJavascriptForSubFolder(int folderId, String Name, String fName, String Link, int downline) 
   { 
       
   try{ 
      PreparedStatement psQueryString = null; 
      ResultSet rsqueryString = null; 
       String queryString1 ="SELECT NodeID, Name, IsFolder, ParentID, Link FROM NodesTable WHERE ((ParentID=" + folderId + ") AND (IsFolder=1) AND level<=" + downline + ") ORDER BY Name"; 
      psQueryString=con.prepareStatement(queryString1); 
      rsqueryString=psQueryString.executeQuery(); 
       
       
      // iterate through the rows in the Nodes table 
         while(rsqueryString.next()) 
         { 
         out.print("web" + rsqueryString.getString("NodeID") + "=insFld(" + fName + ",gFld('" + rsqueryString.getString("Name") + "[" + rsqueryString.getString("NodeID") + "]','tree.jsp?NodeID="+rsqueryString.getString("NodeID")+"&downline="+downline+"'))  " + "\n" ); 
         outputJavascriptForSubFolder(Integer.parseInt(rsqueryString.getString("NodeID")),rsqueryString.getString("Name"),"web" + rsqueryString.getString("NodeID"),rsqueryString.getString("Link"),downline); 
         } 
     } 
   catch(Exception e) 
      { 
        System.out.println(e); 
      } 
   } 
  
%> 

<html> 
<head> 

<title>Tree from database</title> 

<style type="text/css"> 
   BODY {background-color: white} 
   TD {font-size: 10pt; 
       font-family: verdana,helvetica; 
      text-decoration: none; 
      white-space:nowrap;} 
   A  {text-decoration: none; 
       color: black} 
</style> 

<!-- As in a client-side built tree, all the tree infrastructure is put in place 
     within the HEAD block, but the actual tree rendering is trigered within the 
     BODY --> 

<!-- Code for browser detection --> 
<script language="javascript" src="ua.js"></script> 
<script language="javascript" src="ftiens4.js"></script> 
<!-- Execution of the code that actually builds the specific tree. 
     The variable foldersTree creates its structure with calls to 
    gFld, insFld, and insDoc --> 
<script language="javascript"> 
USETEXTLINKS = 1 
STARTALLOPEN = 1 
PRESERVESTATE = 1 
ICONPATH = '' 
HIGHLIGHT = 1 
<% 
outputJavascriptForRoot(); 
%> 


// Load a page as if a node on the tree was clicked (synchronize frames) 
// (Highlights selection if highlight is available.) 
function loadSynchPage(xID) 
{ 
   var folderObj; 
   docObj = parent.treeframe.findObj(xID); 
   docObj.forceOpeningOfAncestorFolders(); 
   parent.treeframe.clickOnLink(xID,docObj.link,'basefrm'); 

    //Scroll the tree window to show the selected node 
    //Other code in these functions needs to be changed to work with 
    //frameless pages, but this code should, I think, simply be removed 
    if (typeof parent.treeframe.document.body != "undefined") //scroll doesn work with NS4, for example 
        parent.treeframe.document.body.scrollTop=docObj.navObj.offsetTop 
} 
</script> 
</head> 

<body topmargin=16 marginheight=16> 

<!-- By removing the follwoing code you are violating your user agreement. 
     Corporate users or any others that want to remove the link should check 
    the online FAQ for instructions on how to obtain a version without the link --> 
<!-- Removing this link will make the script stop from working --> 
<div style="position:absolute; top:0; left:0; "><table border=0><tr><td><font size=-2><a style="font-size:7pt;text-decoration:none;color:silver" href="http://www.treemenu.net/" target=_blank>JavaScript Tree Menu</a></font></td></tr></table></div> 

<!-- Build the browser's objects and display default view of the 
     tree. --> 
<script language="javascript"> 
initializeDocument() 
//Click the Parakeet link 
loadSynchPage(11) 
</script> 
<noscript> 
A tree for site navigation will open here if you enable JavaScript in your browser. 
</noscript> 
</body> 
</html> 