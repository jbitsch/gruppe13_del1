<%@page import="dto.ReceptKompDTO"%>
<%@page import="dto.ReceptDTO"%>
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

<title>Recept</title>
</head>
<body>
<%
ReceptDTO recept = produktAdmin.getRecept();
%>
<jsp:include page="menubar.jsp" /><br>

<h1><%=recept.getReceptNavn()%></h1>


<p>Recept id: <%=recept.getReceptId() %></p>
<p>Recept navn:<%= recept.getReceptNavn() %></p>
<p><b>Receptkomoponeneter:</b></p>
<%


for (int i=0; i<produktAdmin.getReceptKomp().size(); i++) {
		
		ReceptKompDTO receptKomp = (dto.ReceptKompDTO) produktAdmin.getReceptKomp().get(i);
		%>
		<b><%=i+1 %> Raavarenavn:</b> <%=receptKomp.getRaavare().getRaavareNavn() %><br>
		Netto: <%=receptKomp.getNomNetto() %> <br>
		Tolerance <%=receptKomp.getTolerance() %><br>
		<%
}
%>

<form method="POST" action="">
<input type="hidden" name="menuValg" value="Vis recept">	

<div class="bottomSubmit">
	<input type = "submit" name="Tilbage" value="Tilbage">
</div>

</form>



</body>
</html>