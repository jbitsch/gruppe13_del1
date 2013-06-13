<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="raavareAdmin" class="controller.RaavareAdministration" type="controller.RaavareAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>R�vare form</title>
</head>
<body>

<h1>Indtast r�vare information</h1>

<p><font color="red"><%=raavareAdmin.getError() %></font><font color="green"><%= raavareAdmin.getSucces() %></font><br>

<form method="POST">
	
	<%
	if(raavareAdmin.getIsnew())
	{
	%>
	R�vare id: <input name="raavareId" type="text" size = 8 value = "<%= raavareAdmin.getRaavareId() %>"><br>
	<%
	}
	else
	{
		out.println("R�vare id: "+raavareAdmin.getRaavareId()+"<br>");	
	}
	%>
	R�vare navn: <input name="raavareNavn" type="text" size = 20 value = "<%= raavareAdmin.getRaavareNavn() %>"><br>
	
	<%
	if(raavareAdmin.getIsnew())
	{
		%>
			<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" name="handling" value="Opret Raavare">
			<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	
		<%
	}
	else
	{
		%>
			<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" name="handling" value="�ndre Raavare">
			<input type="hidden" name="backpage" value="/WEB-INF/CDIO/chooseRaavare.jsp" />	
		<%
	}
%>
</form>



</body>
</html>