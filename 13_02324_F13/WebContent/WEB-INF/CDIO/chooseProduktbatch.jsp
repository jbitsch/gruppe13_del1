<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="produktAdmin" class="model.ProduktAdministration" type="model.ProduktAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>V�lg produktbatch</title>
</head>
<body>
<jsp:include page="menubar.jsp" /><br> 
<h1>V�lg produktbatch</h1>

<form method="POST">
	<%
	for (int i=0; i<produktAdmin.getProduktbatch().size(); i++) {
		
		dto.ProduktBatchDTO produktBatch = (dto.ProduktBatchDTO) produktAdmin.getProduktbatch().get(i);
		String color = "black";
		String status = "Ikke p�begyndt";
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
			<input type="radio" name="produktbatchValg" value="<%= produktBatch.getPbId() %>">
			<font color="<%=color%>">Status:<%=status %> produktbatch id <%=produktBatch.getPbId() %>: <%=produktBatch.getRecept().getReceptNavn() %></font><br>
		<%
	}
	%>
	
	<div class="bottomSubmit">
		Indtast receptnavn for at s�ge:<input type = "text" name = "searchProduktBatch"><br>
		<input type = "submit" name="searchProduktB" value="Soeg p� recept navn"><br>
	</div>
	
	<div class="bottomSubmit">
		<input type="submit" value="V�lg produktbatch">
	</div>
	
</form>
</body>
</html>