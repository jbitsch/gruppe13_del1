<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="login" class="funktionalitet.Login" type="funktionalitet.Login" scope="session"/>
<jsp:useBean id="valg" class="funktionalitet.BrugerValg" type="funktionalitet.BrugerValg" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bruger formular</title>
</head>
<body>

<h1>Bruger formular</h1>

<p><font color="red"><%= valg.error %></font><br>
<p><font color="green"><%= valg.succes %></font><br>

<form method="POST">
	Operatør navn: <input name="oprName" type="text" size = 20 value = "<%= valg.name %>"><br>
	Initialer: <input type="text" name="ini" value = "<%= valg.ini %>"><br>
	CPR: <input type="text" name="cpr" value = "<%= valg.cpr %>"><br>
	Password:<input type="password" name="newPw" value = "<%= valg.password %>"><br>

<%
	if(valg.id==0)
	{
		%>
			<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" name="handling" value="Opret bruger">
		<%
	}
	else
	{
		%>
			<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" name="handling" value="Ændre">
			<%
			if(!(valg.id==login.getId()))
			{
				%>
					<input type = "submit" name="handling" value="Slet">
				<%
			}
			%>
		<%
	}
%>
</form>
</body>
</html>
