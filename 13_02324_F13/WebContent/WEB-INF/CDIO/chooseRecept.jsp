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

<title>Vælg recept</title>
</head>
<body>
<jsp:include page="menubar.jsp" /><br>
<h1>Vælg recpet</h1>
<form method="POST">
	<%
	for (int i=0; i<produktAdmin.getRecepter().size(); i++) {
		
		ReceptDTO recept = (ReceptDTO) produktAdmin.getRecepter().get(i);
		%>
			<input type="radio" name="receptValg" value="<%= recept.getReceptId() %>">
			Navn: <%=recept.getReceptNavn() %><br>
		<%
	}
	%>
	
	<div class="bottomSubmit">
		<input type="submit" value="Vælg recept">
	</div>
	
</form>
</body>
</html>