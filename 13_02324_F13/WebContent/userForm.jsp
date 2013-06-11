<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="login" class="controller.Login" type="controller.Login" scope="session"/>
<jsp:useBean id="userValg" class="controller.BrugerAdministration" type="controller.BrugerAdministration" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bruger formular</title>
</head>
<body>

<h1>Bruger formular</h1>

<p><font color="red"><%=userValg.getError() %></font><font color="green"><%= userValg.getSucces() %></font><br>
ID er <%=userValg.id %>
<form method="POST">
	Operatør navn: <input name="oprName" type="text" size = 20 value = "<%= userValg.name %>"><br>
	Initialer: <input type="text" name="ini" value = "<%= userValg.ini %>"><br>
	CPR: <input type="text" name="cpr" value = "<%= userValg.cpr %>"><br>
	Password:<input type="password" name="newPw" value = "<%= userValg.password %>"><br>
	Rolle: <select name="rolle">
			<option value="Administrator">Administrator</option>
			<option value="Farmaceut">Farmaceut</option>
			<option value="Værkfører">Værkfører</option>
			<option value="Operatør" selected="selected">Operatør</option>
			</select>
<%
	if(userValg.id==0)
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
			if(!(userValg.id==login.getId()))
			{
				%>
					<input type = "submit" name="handling" value="Slet">
				<%
			}
			%>
		<%
	}
%>
<%
out.println(userValg.name);


%>
</form>
</body>
</html>
