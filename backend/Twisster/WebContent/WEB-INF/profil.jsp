<%@page import="com.twister.tools.JSONResponse"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<ul>
	<%  JSONResponse rep = (JSONResponse)request.getAttribute("login");
		out.print("<h3>salut yanis</h3>");
	   	if ( rep.isAccepted()){
	   	out.print("<p> connexion avec succès </p>");
	   	out.print("<li>"+rep.getString("login")+"</li>");   	
		out.print("<li>"+rep.getString("key")+"</li>");   	
		out.print("<li>"+rep.getString("id")+"</li>");   	
	   	}else
	   		out.print("<li>"+rep.getMotif()+"</li>");   	
			
	%>
	</ul>
</body>
</html>