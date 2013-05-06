<%@ include file = "loginCheck.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="function" class="funktionalitet.Funktionalitet2" type="funktionalitet.IFunktionalitet2" scope="application"/>
<jsp:useBean id="user" class="data.OperatoerDTO2" type="data.OperatoerDTO2" scope="session"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menu</title>
</head>
<body>
	<h1>Menu</h1>
	<a href="logOut.jsp">1.Log ud</a><br>
	<a href="weight.jsp">2.Vægt applikation</a><br>
	<a href="changePw.jsp">3.Skift password</a><br>
	
<%
	if(user.getOprId()==10)
	{
			out.println("<a href=adminMenu.jsp>4.Administrer operatører</a><br>");
	}

%>
</body>
</html>