<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="raavareAdmin" class="model.RaavareAdministration" type="model.RaavareAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style2.css" %>
</style>

<title>R�vare form</title>
</head>
<body>

	<div class="header">
		<div class="headerContent">
			<jsp:include page="menubar.jsp" />
		</div>
	</div>
	
	<div class="content">
		<h1>Indtast r�vare information</h1>
		
		<p><font color="red"><%=raavareAdmin.getError() %></font><font color="green"><%= raavareAdmin.getSucces() %></font><br>
		
		<form method="POST" action="">
		<table border="0">	
			<%
			if(raavareAdmin.getIsnew())
			{
			%>
			<tr>
			<td>R�vare id:</td>
			<td> <input name="raavareId" type="text" size = 8 value = "<%= raavareAdmin.getRaavareId() %>"></td>
			</tr>
			<%
			}
			else
			{
				%>
				<tr>
					<td>R�vare id:</td> 
					<td><%=raavareAdmin.getRaavareId()%></td>	
				</tr>
				<%
			}
			%>
			<tr>
				<td>R�vare navn:</td>
				<td><input name="raavareNavn" type="text" size = 20 value = "<%= raavareAdmin.getRaavareNavn() %>"></td>
			</tr>
		</table>
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
						<input type = "submit" name="Tilbage" value="Tilbage"><input class="onInputLine" type="submit" name="handling" value="�ndre Raavare">
						<input type="hidden" name="menuValg" value="Administrer raavare" />	
					<%
				}
				%>
			</div>
			
		</form>
	</div>


</body>
</html>