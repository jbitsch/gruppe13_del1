<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="login" class="funktionalitet.Login" type="funktionalitet.Login" scope="session"/>
<jsp:useBean id="valg" class="funktionalitet.BrugerValg" type="funktionalitet.BrugerValg" scope="session"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vægtapplikation</title>
</head>
<body>
<p><font color="red"><%= valg.error %></font><br>
Netto <%= valg.netto %>
<form method="POST">
	<input type="hidden" name="handling" value="weight">
	Tarra: <input type="text" name="tarra" value="<%= valg.t %>"><br /> 
	Brutto: <input type="text" name="brutto" value="<%= valg.b %>"><br />
	
	<input type = "submit" name="submit" value="Tilbage"><input type = "submit" name="submit" value="Udregn">
</form>



</body>
</html>