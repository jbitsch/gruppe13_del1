<%@page import="dto.RaavareDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="raavareAdmin" class="model.RaavareAdministration" type="model.RaavareAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>Råvarebatch form</title>
</head>
<body>

<jsp:include page="menubar.jsp" /><br>
<h1>Opret raavarebatch</h1>

<p><font color="red"><%=raavareAdmin.getError() %></font><font color="green"><%= raavareAdmin.getSucces() %></font><br>

<form method="POST">
	Råvarebatch id: <input name="raavarebatchId" type="text" size = 8 value = "<%=raavareAdmin.getRaavareBatchId()  %>"><br>
	Leverandøer: <input name="leverandoer" type="text" size = 20 value = "<%=raavareAdmin.getLeverandoer()  %>"><br>
	Mængde: <input name="raavareMaengde" type="text" size = 8 value = "<%= raavareAdmin.getMaengde() %>"><br><br/>
	
	Vælg råvare:<br>
	<%
	for (int i=0; i<raavareAdmin.getRaavare().size(); i++) {
		RaavareDTO raavare = (dto.RaavareDTO) raavareAdmin.getRaavare().get(i);
		%>
			<input type="radio" name="raavarevalgBatch" value="<%= raavare.getRaavareId() %>">
			Råvare: <%= raavare.getRaavareNavn() %><br>
		<%
	}
%>

<div class="bottomSubmit">
	<input type="submit" name="handling" value="Opret Raavarebatch">
</div>

</form>



</body>
</html>