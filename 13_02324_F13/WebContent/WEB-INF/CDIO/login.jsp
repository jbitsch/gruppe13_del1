<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="user" class="dto.OperatoerDTO" type="dto.OperatoerDTO" scope="session"/>
<jsp:useBean id="login" class="model.Login" type="model.Login" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style2.css" %>
</style>

<title>Login</title>
</head>
<body>

	<div class="content">

		<h1>Log ind</h1>
		<font color="red"><%= login.getMeddelelse() %></font><br>
		<form method="POST" action="">
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
			Administrator: ID = 10, password = Qwerty!<br>
			Farmaceut: ID = 2, password = Qwerty!<br>
			Værkfører: ID = 3, password = Qwerty!
			
	</div>


</body>
</html>