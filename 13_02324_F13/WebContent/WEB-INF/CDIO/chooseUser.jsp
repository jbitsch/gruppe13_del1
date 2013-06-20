<%@page import="dto.OperatoerDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="brugerAdmin" class="model.BrugerAdministration" type="model.BrugerAdministration" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>Vælg bruger</title>
</head>
<body>
<jsp:include page="menubar.jsp" /><br>
<h1>Vælg bruger</h1>

<form method="POST" action="">	
<%
	ArrayList<OperatoerDTO> userList = brugerAdmin.getUsers();
	for (int i=0; i<userList.size(); i++) {
			OperatoerDTO user = userList.get(i);
		%>
			<input type="radio" name="brugervalg" value="<%= user.getOprId() %>">
			Bruger: <%= user.getOprId() %> Navn <%= user.getOprNavn() %><br>
		<%
	}
%>

<div class="bottomSubmit">
	<input type="submit" value="Vælg bruger">
</div>

</form>

</body>
</html>
