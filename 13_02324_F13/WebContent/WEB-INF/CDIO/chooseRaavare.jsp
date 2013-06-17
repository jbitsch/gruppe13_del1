<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="dto.RaavareDTO"%>
<jsp:useBean id="raavareAdmin" class="model.RaavareAdministration" scope="session" />
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>Vælg Råvare</title>

</head>
<body>
<jsp:include page="menubar.jsp" /><br>
<h1>Vælg råvare</h1>

<form method="POST">
<%
	for (int i=0; i<raavareAdmin.getRaavare().size(); i++) {
		RaavareDTO raavare = (dto.RaavareDTO) raavareAdmin.getRaavare().get(i);
		%>
			<input type="radio" name="raavarevalg" value="<%= raavare.getRaavareId() %>">
			Råvare: <%= raavare.getRaavareNavn() %> <br>
		<%
	}
%>

<div class="bottomSubmit">
	<input type="submit" value="Vælg raavare">
</div>

</form>

</body>

</html>