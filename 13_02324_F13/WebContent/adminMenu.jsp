<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="user" class="data.OperatoerDTO2" type="data.OperatoerDTO2" scope="session"/>
<jsp:useBean id="login" class="funktionalitet.Login" type="funktionalitet.Login" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Menu</title>
</head>
<body>
	<h1>Menu</h1>
	
	
<form method="POST">
	<input type="hidden" name="handling" value="null">	
	<input type="radio" name="adminValg" value="showUsers">Administrer operat�rer<br>	
	<input type="radio" name="adminValg" value="createUser">Opret bruger<br>		
	
	<input type="submit" value="V�lg menu punkt">
</form>

<form method="POST">
	<input type="hidden" name="handling" value="log_ud">
	<input type="submit" value="Log ud">
</form>
	
	

</body>
</html>