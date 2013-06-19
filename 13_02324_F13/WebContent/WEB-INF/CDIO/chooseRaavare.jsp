<%@page import="dto.RaavareBatchDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="dto.RaavareDTO"%>
<jsp:useBean id="raavareAdmin" class="model.RaavareAdministration" scope="session" />
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>V�lg R�vare</title>

</head>
<body>
<jsp:include page="menubar.jsp" /><br>
<h1>V�lg r�vare</h1>

<form method="POST">
<%
	ArrayList<RaavareDTO> raavareList = raavareAdmin.getRaavare();
	for (int i=0; i<raavareList.size(); i++) {
		RaavareDTO raavare = raavareList.get(i);
		%>
			<input type="radio" name="raavarevalg" value="<%= raavare.getRaavareId() %>">
			R�vare id <%=raavare.getRaavareId() %>: <%= raavare.getRaavareNavn() %> <br>
		<%
	}
%>

<div class="bottomSubmit">
	<input type="submit" value="V�lg raavare">
</div>

</form>

</body>

</html>