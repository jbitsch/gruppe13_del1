<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="login" class="model.Login" type="model.Login" scope="session"/>
<jsp:useBean id="brugerAdmin" class="model.BrugerAdministration" type="model.BrugerAdministration" scope="session"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>Skift password</title>
</head>
<body>
<jsp:include page="menubar.jsp" /><br>
<h1>Skift adgangskode </h1>

<p><font color="red"><%= brugerAdmin.getError() %></font><font color="green"><%= brugerAdmin.getSucces() %></font><br>

<form method="POST" action="">
	<input type="hidden" name="handling" value="changePw">
	<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp">	
	Gammelt password: <input type="password" name="old"><br> 
	Indtast nyt password: <input type="password" name="new1"><br>
	Gentag nyt password: <input type="password" name="new2"><br>
	
	<div class="bottomSubmit">
		<input type = "submit" name="submit" value="Skift">
	</div>
	
</form>



</body>
</html>