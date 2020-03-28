<%@page import="java.io.PrintWriter"%>
<%@page import="com.twister.tools.JSONResponse"
		import="org.json.JSONArray"
		import="org.json.JSONException"
		import="org.json.JSONObject"
		import="com.twister.services.Friend"
 %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
	</head>
<body>
	<div>
		<h2> Page Principale  </h2>
		<p> Bienvenue sur twister</p>
		<h4>amis :</h4>
		
		<ul>
		<%
		//recuperer la cle
			String key = "";
			try{
				JSONResponse resultat = (JSONResponse)request.getAttribute("res");
				
				if(resultat.isAccepted()) {
					key = resultat.getString("Key");
					out.print("<p><a href=\"http://localhost:8080/Twisster/logout?key=" + key + "\"/>deconnexion</a></p>");
					out.println("<lu> votre liste d'amis :");
					JSONArray listeAmis = ((JSONObject) request.getAttribute("amis")).getJSONArray("Liste amis ");
				
					for (int i = 0; i < listeAmis.length(); i++) {
						JSONObject amis = new JSONObject(listeAmis.getString(i));
						out.println("<li> " + amis.getString("nom") + " " + amis.getString("prenom") + "</li>");
					}
					out.print("</lu>");
				} else {
					out.println(resultat.getMotif());
				}
			}catch(Exception e){
				out.print("exception");
			}
	    %>
	</div>
</body>
</html>