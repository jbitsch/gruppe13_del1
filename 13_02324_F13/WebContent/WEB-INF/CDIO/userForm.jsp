<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="login" class="controller.Login" type="controller.Login" scope="session"/>
<jsp:useBean id="brugerAdmin" class="controller.BrugerAdministration" type="controller.BrugerAdministration" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>Bruger formular</title>
</head>
<body>

<%!

String[] roller = new String[]{"Administrator","Farmaceut","V�rkf�rer","Operat�r"};
%>

<h1>Bruger formular</h1>

<p><font color="red"><%=brugerAdmin.getError() %></font><font color="green"><%= brugerAdmin.getSucces() %></font><br>
<form method="POST">
	Operat�r navn: <input name="oprName" type="text" size = 20 value = "<%=brugerAdmin.getName() %>"><br>
	Initialer: <input type="text" name="ini" value = "<%= brugerAdmin.getIni() %>"><br>
	CPR: <input type="text" name="cpr" value = "<%= brugerAdmin.getCpr() %>"><br>
	Password:<input type="password" name="newPw" value = "<%= brugerAdmin.getPassword() %>"><br>
<%
	if(brugerAdmin.getId()==0 || !("Operat�r").equals(brugerAdmin.getRolle()))
	{
		%>
				Rolle: <select name="rolle">
				<option value="<%=brugerAdmin.getRolle()%>" selected="selected"><%=brugerAdmin.getRolle()%></option>
				<%
				for(int i = 0; i < roller.length; i++)
				{
					if(!roller[i].equals(brugerAdmin.getRolle()))
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
		out.println("Rolle: "+brugerAdmin.getRolle()+"<br>");
	}
	
	if(brugerAdmin.getId()==0)
	{
	%>
			<div class="bottomSubmit">	
				<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" name="handling" value="Opret bruger">
			</div>
			<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	
	<%
	}
	else
	{
		%>
			<div class="bottomSubmit">
				<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" name="handling" value="�ndre">
				<input type="hidden" name="backpage" value="/WEB-INF/CDIO/chooseUser.jsp" />	
				<%
				if(!(brugerAdmin.getId()==login.getId()) && !("Operat�r".equals(brugerAdmin.getRolle())))
				{
					%>
						<input type = "submit" name="handling" value="Slet">
					<%
				}
				%>
			</div>
		<%
	}
%>
</form>
</body>
</html>
