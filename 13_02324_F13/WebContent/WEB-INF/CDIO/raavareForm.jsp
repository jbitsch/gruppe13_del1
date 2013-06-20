<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="raavareAdmin" class="model.RaavareAdministration" type="model.RaavareAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>Råvare form</title>
</head>
<body>
<jsp:include page="menubar.jsp" /><br>
<h1>Indtast råvare information</h1>

<p><font color="red"><%=raavareAdmin.getError() %></font><font color="green"><%= raavareAdmin.getSucces() %></font><br>

<form method="POST" action="">
	
	<%
	if(raavareAdmin.getIsnew())
	{
	%>
	Råvare id: <input name="raavareId" type="text" size = 8 value = "<%= raavareAdmin.getRaavareId() %>"><br>
	<%
	}
	else
	{
		out.println("Råvare id: "+raavareAdmin.getRaavareId()+"<br>");	
	}
	%>
	Råvare navn: <input name="raavareNavn" type="text" size = 20 value = "<%= raavareAdmin.getRaavareNavn() %>"><br>
	
	<div class="bottomSubmit">
		<%
		if(raavareAdmin.getIsnew())
		{
			%>
				<input type="submit" name="handling" value="Opret Raavare">
			<%
		}
		else
		{
			%>
				<input type = "submit" name="Tilbage" value="Tilbage"><input type="submit" name="handling" value="Ændre Raavare">
				<input type="hidden" name="menuValg" value="Administrer raavare" />	
			<%
		}
		%>
	</div>
	
</form>



</body>
</html>