<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="function" class="funktionalitet.Funktionalitet2" type="funktionalitet.IFunktionalitet2" scope="application"/>
<jsp:useBean id="user" class="data.OperatoerDTO2" type="data.OperatoerDTO2" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>

<h1>Login</h1>

<%
if (request.getMethod().equals("POST")) {
	String pw = request.getParameter("password");
	String id = request.getParameter("id");
	
	boolean loginOK = function.login(pw, id);
	if (loginOK) {
		session.setAttribute("loginId",id);
		int numID  = Integer.parseInt(id);
		session.setAttribute("user", function.getUser(numID));
		response.sendRedirect("menu.jsp");
	} 
	else
	{
		%> 	
			<font color='red'>Bruger findes ikke.</font> 
		<%
	}
}
%>

<form id=form name=form action=login.jsp method=post>

Indtast ID: <input id=id name=id	value=""/><br>
Indtast password: <input id=password name=password type = password	value=""/><br>
            	<input id=submit type=submit value="Loign"/>
</form>


</body>
</html>