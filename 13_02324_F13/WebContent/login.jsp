<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="function" class="funktionalitet.Funktionalitet2" type="funktionalitet.IFunktionalitet2" scope="application"/>
<jsp:useBean id="user" class="data.OperatoerDTO2" type="data.OperatoerDTO2" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>

<h1>Log ind</h1>

<form method="POST">
<input type="hidden" name="handling" value="null">
<table>
<tr>
	<td>id:</td>
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


</body>
</html>