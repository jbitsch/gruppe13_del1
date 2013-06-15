<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="produktAdmin" class="controller.ProduktAdministration" type="controller.ProduktAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>Vælg produktbatch</title>
</head>
<body>
<h1>Vælg produktbatch</h1>
<form method="POST">
<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	
	<%
	for (int i=0; i<produktAdmin.getProduktbatch().size(); i++) {
		
		dto.ProduktBatchDTO produktBatch = (dto.ProduktBatchDTO) produktAdmin.getProduktbatch().get(i);
		String color = "black";
		String status = "Ikke påbegyndt";
		if(produktBatch.getStatus()==1)
		{
			color = "green";
			status = "Under produktion";
			
		}
		else if(produktBatch.getStatus()==2)
		{
			color = "red";
			status = "Afsluttet";
		}
		%>
			<input type="radio" name="produktvatchValg" value="<%= produktBatch.getPbId() %>">
			<font color="<%=color%>">Status:<%=status %> produktbatch id <%=produktBatch.getPbId() %>: <%=produktBatch.getRecept().getReceptNavn() %></font><br>
		<%
	}
	%>
	
	Indtast receptnavn for at søge:<input type = "text" name = "searchProduktBatch"><br>
	<input type = "submit" name="searchProduktB" value="Soeg på recept navn"><br>
	<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" value="Vælg produktbatch">
</form>
</body>
</html>