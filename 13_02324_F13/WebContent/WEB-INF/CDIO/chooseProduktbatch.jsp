<%@page import="dto.ProduktBatchDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="produktAdmin" class="model.ProduktAdministration" type="model.ProduktAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>Vælg produktbatch</title>
</head>
<body>
<jsp:include page="menubar.jsp" /><br> 
<h1>Vælg produktbatch</h1>

<form method="POST" action="">
	<%
	ArrayList<ProduktBatchDTO> produktbatchList = produktAdmin.getProduktbatch();
	for (int i=0; i<produktbatchList.size(); i++) {
		
		dto.ProduktBatchDTO produktBatch = produktbatchList.get(i);
		String color = "black";
		String status = "Ikke påbegyndt";
		if(produktBatch.getStatus()==1)
		{
			color = "green";
			status = "Under produktion";
		}
		else if(produktBatch.getStatus()==2)
		{
			color = "";
			status = "Afsluttet";
		}
		else if(produktBatch.getStatus()==3)
		{
			color = "red";
			status = "FEJL under afvejning";
		}
		%>
			<input type="radio" name="produktbatchValg" value="<%= produktBatch.getPbId() %>">
			<font color="<%=color%>">Status:<%=status %> produktbatch id <%=produktBatch.getPbId() %>: <%=produktBatch.getRecept().getReceptNavn() %></font><br>
		<%
	}
	%>
	
	<div class="bottomSubmit">
		Indtast receptnavn for at søge:<input type = "text" name = "searchProduktBatch"><br>
		<input type = "submit" name="searchProduktB" value="Soeg på recept navn"><br>
	</div>
	
	<div class="bottomSubmit">
		<input type="submit" value="Vælg produktbatch">
	</div>
	
</form>
</body>
</html>