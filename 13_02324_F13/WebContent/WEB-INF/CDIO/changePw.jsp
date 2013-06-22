<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="login" class="model.Login" type="model.Login" scope="session"/>
<jsp:useBean id="brugerAdmin" class="model.BrugerAdministration" type="model.BrugerAdministration" scope="session"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style2.css" %>
</style>

<title>Skift password</title>
</head>
<body>

	<div class="header">
		<div class="headerContent">
			<jsp:include page="menubar.jsp" />
		</div>
	</div>
	
	
	<div class="content">
		
		<h1>Skift adgangskode </h1>
		
		<P>Adgangskoden skal være mellem 7 0g 8 tegn, samt indeholde 3 af følgende tegn typer:<p>
		- Små bogstaver<br>
		- Store bogstaver<br>
		- Tal<br>
		- Specialtegn
		
		
		<p><font color="red"><%= brugerAdmin.getError() %></font><font color="green"><%= brugerAdmin.getSucces() %></font><br>
		
		<form method="POST" action="">
		<table border="0">	
			
			<tr>
				<td><input type="hidden" name="handling" value="changePw">	
				Gammelt password:</td>
				<td> <input type="password" name="old"></td> 
			</tr>
			<tr>
				<td>Indtast nyt password:</td>
				<td> <input type="password" name="new1"></td>
			</tr>
			<tr>
				<td>Gentag nyt password:</td>
				<td> <input type="password" name="new2"></td>
			</tr>
		</table>	
			<div class="bottomSubmit">
				<input type = "submit" name="submit" value="Skift">
			</div>
			
		</form>
	
	</div>



</body>
</html>