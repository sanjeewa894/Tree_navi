<%-- 
    Document   : index
    Created on : Nov 21, 2016, 4:23:11 PM
    Author     : sanjeewa
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="mytree.TreeDataRetrieve"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="main.MappedTreeStructure"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="StyleSheet" href="dtree.css" type="text/css" />
	<script type="text/javascript" src="dtree.js"></script>
        
        <title>JSP Page</title>
        
        <%
        TreeDataRetrieve tree = new TreeDataRetrieve();
        Map<String,  String> listOfNode = new HashMap<String,String >();
        Map<String, String > listOfName = new HashMap<String, String>(); //withnode
        Gson gson = new Gson(); 
            tree.initConnection();
            listOfName = tree.returnMapName();
            listOfNode = tree.returnMapNode();
            
        %>
        
        
    </head>
    <body>
	<div class="dtree">
	<p><a href="javascript: d.openAll();">open all</a> | <a href="javascript: d.closeAll();">close all</a></p>
    
            <script type="text/javascript">
		<!--
                    var listOfNamejs =  <%= gson.toJson(listOfName) %>; //convert json object to js object
                    var listOfNodejs =  <%= gson.toJson(listOfNode) %>;
                
                    d = new dTree('d');
                   
                        d.add(0,-1,'Promotion'); //add 1st node               

                    for(var x in listOfNamejs){
                        d.add(x,listOfNodejs[x],  listOfNamejs[x],'','','','img/trash.gif');    //add image               
                    } 
                    //alert(x); popup
                    //document.write(langs[1]);                 
		
                    document.write(d);//print tree
		-->
            </script>
          
         
        </div>
              
    </body>
    
</html>
