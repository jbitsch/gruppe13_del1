<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="user" class="dto.OperatoerDTO" type="dto.OperatoerDTO" scope="session"/>
<jsp:useBean id="login" class="controller.Login" type="controller.Login" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>Menu</title>
</head>
<body>
	<h1>Menu</h1>
	
	
<form method="POST">
<input type="hidden" name="namePage" value="/WEB-INF/CDIO/menu.jsp" />	
	<%
		String rolle = login.getRolle();
	%>
	<input type="radio" name="menuValg" value="changePassword">Skift Adgangskode<br>
	
	<h3>Værkfører opgaver:</h3>	
	<input type="radio" name="menuValg" value="raavarebatchForm">Opret råvarebatch<br>
	<input type="radio" name="menuValg" value="produktbatch">Opret produktbatch<br>					
	<input type="radio" name="menuValg" value="showRaavarebatch">Vis råvarebatch<br>
	<input type="radio" name="menuValg" value="chooseProduktBatch">Vis produktbatch<br>
	<%
		if("Farmaceut".equals(rolle) || "Administrator".equals(rolle) )
		{
			%>
					<h3>Farmaceut opgaver:</h3>
					<input type="radio" name="menuValg" value="receptForm">Opret recpet<br>
					<input type="radio" name="menuValg" value="raavareForm">Opret råvare<br>
					<input type="radio" name="menuValg" value="showRaavare">Administrer råvare<br>
					<input type="radio" name="menuValg" value="chooseRecept">Vis recept<br>
			<%
		}
		if("Administrator".equals(rolle) )
		{
			%>
					<h3>Administrator opgaver::</h3>
					<input type="radio" name="menuValg" value="userForm">Opret bruger<br>
					<input type="radio" name="menuValg" value="showUsers">Administrer operatører<br>
			<%
		}
	%>
	<input type="submit" value="Vælg menu punkt">
</form>

<form method="POST">
	<input type="hidden" name="handling" value="log_ud">
	<input type="submit" value="Log ud">
</form>
	
	

</body>
</html>