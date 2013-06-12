<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="login" class="controller.Login" type="controller.Login" scope="session"/>
<jsp:useBean id="brugerAdmin" class="controller.BrugerAdministration" type="controller.BrugerAdministration" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bruger formular</title>
</head>
<body>

<%!

String[] roller = new String[]{"Administrator","Farmaceut","Værkfører","Operatør"};
%>

<h1>Bruger formular</h1>

<p><font color="red"><%=brugerAdmin.getError() %></font><font color="green"><%= brugerAdmin.getSucces() %></font><br>
<form method="POST">
	Operatør navn: <input name="oprName" type="text" size = 20 value = "<%= brugerAdmin.name %>"><br>
	Initialer: <input type="text" name="ini" value = "<%= brugerAdmin.ini %>"><br>
	CPR: <input type="text" name="cpr" value = "<%= brugerAdmin.cpr %>"><br>
	Password:<input type="password" name="newPw" value = "<%= brugerAdmin.password %>"><br>
<%
	if(brugerAdmin.id==0 || !("Operatør").equals(brugerAdmin.rolle))
	{
		%>
				Rolle: <select name="rolle">
				<option value="<%=brugerAdmin.rolle%>" selected="selected"><%=brugerAdmin.rolle%></option>
				<%
				for(int i = 0; i < roller.length; i++)
				{
					if(!roller[i].equals(brugerAdmin.rolle))
					{
						%>
							<option value="<%=roller[i]%>"><%=roller[i] %></option>
						<%
						
					}
				}
				%>
				</select><br>
				<%
	}else
	{
		out.println("Rolle: "+brugerAdmin.rolle+"<br>");
	}
	
	if(brugerAdmin.id==0)
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
			if(!(brugerAdmin.id==login.getId()) && !("Operatør".equals(brugerAdmin.rolle)))
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
