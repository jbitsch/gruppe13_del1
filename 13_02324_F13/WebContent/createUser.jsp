<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ include file = "adminLoginCheck.jsp" %>
<jsp:useBean id="function" class="funktionalitet.Funktionalitet2" type="funktionalitet.IFunktionalitet2" scope="application"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create new user</title>
</head>
<body>
<form id=create name=create action=createUser.jsp method=post>
<% if (request.getMethod().equals("POST")) {
  String error = "";
	int id;
	String name = request.getParameter("name");
	String ini = request.getParameter("ini");
	String cpr = request.getParameter("cpr");
	String password = request.getParameter("password");
	if(!function.checkName(name)) {
		error = error+"Navn, ";
	}
	if(!function.checkIni(ini)){
		error = error+"Initialer, ";
	}
	if(!function.checkCpr(cpr)){
		error = error+"Cpr, ";
	}
	if(!function.checkPassword(password)){
		error = error+"Password";
	}
	if(error == ""){
		System.out.println("sucess");
		id = function.createUser(name, cpr, ini, password);
		%> 	
		<font color='green'>Bruger oprettet med ID: <%=id%></font> <br>
		<%
		
		
	}
	else {
		System.out.println(error);
		
	%> 	
	<font color='red'>Fejl i følgende indtastninger: <%=error%></font> <br>

<%
	}

}

%>
Indtast Navn:<br>
<input id=name name=name value=""/><br>
Indtast initialer:<br>
<input id=ini name=ini value=""/><br>
Indtast Cpr:<br>
<input id=cpr name=cpr value=""/><br>
Indtast password:<br>
<input id=password name=password type=password value=""/><br>

<input id=submit type=submit value="Lav Bruger"/>
<input type=button name="button" value="Tilbage" onclick="location.href='adminMenu.jsp'">
<br> 
</form>


</body>
</html>
