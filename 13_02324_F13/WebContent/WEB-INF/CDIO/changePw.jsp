<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="login" class="controller.Login" type="controller.Login" scope="session"/>
<jsp:useBean id="brugerAdmin" class="controller.BrugerAdministration" type="controller.BrugerAdministration" scope="session"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>Skift password</title>
</head>
<body>
<p><font color="red"><%= brugerAdmin.getError() %></font><br>

<form method="POST">
	<input type="hidden" name="handling" value="changePw">
	<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	
	Gammelt password: <input type="password" name="old"><br /> 
	Indtast nyt password: <input type="password" name="new1"><br />
	Gentag nyt password: <input type="password" name="new2"><br />
	
	<input type = "submit" name="menuValg" value="Tilbage"><input type = "submit" name="submit" value="Skift">
</form>



</body>
</html>