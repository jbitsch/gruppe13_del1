<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="login" class="funktionalitet.Login" type="funktionalitet.Login" scope="session"/>
<jsp:useBean id="valg" class="funktionalitet.BrugerValg" type="funktionalitet.BrugerValg" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>

<h1>Opret bruger</h1>

<p><font color="red"><%= valg.error %></font><br>
<p><font color="green"><%= valg.succes %></font><br>

<form method="POST">
<input type="hidden" name="handling" value="createUser">
<table>
<tr>
	<td>Operatør navn::</td> <td><input name="oprName" type="text" size = 20 value = "<%= valg.name %>"></td>
</tr>
<tr>
	<td>Initialer:</td>
	<td><input type="text" name="ini" value = "<%= valg.ini %>"></td>
</tr>
<tr>
	<td>CPR:</td>
	<td><input type="text" name="cpr" value = "<%= valg.cpr %>"></td>
</tr>
<tr>
	<td>Password:</td>
	<td><input type="password" name="newPw" value = "<%= valg.password %>"></td>
</tr>
<tr>
	<td></td>
	<td><input type="submit" name="submit" value="Opret bruger"></td>
</tr>
</table>
</form>


</body>
</html>
