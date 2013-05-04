<%@ include file = "adminLoginCheck.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="function" class="funktionalitet.Funktionalitet2" type="funktionalitet.IFunktionalitet2" scope="application" />
<jsp:useBean id="user" class="data.OperatoerDTO2" type="data.OperatoerDTO2" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administrator menu</title>
</head>
<body>

<h1>Administrator menu</h1>
	<a href="users.jsp">1.Administrere operatør</a><br>
	<a href="createUser.jsp">2.Opret operatør</a><br>
	<a href="menu.jsp">3.Tilbage</a><br>
</body>
</html>