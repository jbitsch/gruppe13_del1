<%@page import="dto.ReceptDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="produktAdmin" class="controller.ProduktAdministration" type="controller.ProduktAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Opret produktbatch</title>
</head>
<body>

<h1>Vælg recept der skal oprettes som produktbatch</h1>

<p><font color="red"><%=produktAdmin.getError() %></font><font color="green"><%= produktAdmin.getSucces() %></font><br>

<form method="POST">
<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	
<%
	for (int i=0; i<produktAdmin.getRecepter().size(); i++) {
		ReceptDTO recept = (dto.ReceptDTO) produktAdmin.getRecepter().get(i);
		%>
			<input type="radio" name="produktbatchReceptId" value="<%= recept.getReceptId() %>">
			Receptnavn: <%= recept.getReceptNavn() %><br>
			
		<%
		//getReceptKomp(int id)
	}
%>
<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" name="handling" value="Opret produktbatch">
</form>



</body>
</html>