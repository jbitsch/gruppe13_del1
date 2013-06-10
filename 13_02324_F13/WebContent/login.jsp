<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="user" class="dto.OperatoerDTO" type="dto.OperatoerDTO" scope="session"/>
<jsp:useBean id="login" class="controller.Login" type="controller.Login" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>

<h1>Log ind</h1>
<font color="red"><%= login.getMeddelelse() %></font><br>
<form method="POST">
<input type="hidden" name="handling" value=null>
<table>
<tr>
	<td>ID:</td>
	<td><input name="id" type="text"></td>
</tr>
<tr>
	<td>Adgangskode:</td>
	<td><input type="password" name="password"></td>
</tr>
<tr>
	<td></td>
	<td><input type="submit" name="handling" value="log ind"></td>
</tr>
</table>
</form>
<h2>Login med:</h2>
	Administrator: ID = 10, password = 12<br>
	Operatør: ID = 11, password = 12


</body>
</html>