<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.OperatoerDTO2" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<jsp:useBean id="function" class="funktionalitet.Funktionalitet2" type="funktionalitet.IFunktionalitet2" scope="application"/>
<jsp:useBean id="operator" class="data.OperatoerDTO2" type="data.OperatoerDTO2" scope="session"/>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit user <%= operator.getOprId() %></title>
</head>
<body>

	
	<%
	boolean validName = true;
	boolean validIni = true;
	boolean validCpr = true;
	boolean validPassword = true;
	
	if(session.getAttribute("loginId") == null)
	{
		response.sendRedirect("login.jsp");
	}
	else if(!((String)session.getAttribute("loginId")).equals("10") || (request.getParameter("id") == null && request.getParameter("delete") == null && request.getParameter("edit") == null))
	{
		response.sendRedirect("menu.jsp");
	}
	else if(request.getParameter("delete") != null)
	{
		function.deleteUser(operator);
		response.sendRedirect("administrate.jsp");
	}
	else if(request.getParameter("edit") != null)
	{
		validName = function.checkName(request.getParameter("oprName"));
		validIni = function.checkIni(request.getParameter("ini"));
		validCpr = function.checkCpr(request.getParameter("cpr"));
		validPassword = function.checkPassword(request.getParameter("password"));
		
		if(validName && validIni && validCpr && validPassword)
		{			
			function.updateUser(operator.getOprId(),
					request.getParameter("oprName"),
					request.getParameter("ini"),
					request.getParameter("cpr"),
					request.getParameter("password"));
		}
		response.sendRedirect("administrate.jsp");
	}
	else if(request.getParameter("id") != null)
	{
		OperatoerDTO2 user = function.getUser(Integer.parseInt(request.getParameter("id")));
		operator.setOprId(user.getOprId());
		operator.setOprNavn(user.getOprNavn());
		operator.setIni(user.getIni());
		operator.setCpr(user.getCpr());
		operator.setPassword(user.getPassword());
		session.removeAttribute(Integer.toString(user.getOprId()));
	}


	%>
	<h2>Operatør <%=operator.getOprId()%></h2>
	<form name=editUser method=get action=editUser.jsp>
		Navn: <input name='oprName' value='<%=operator.getOprNavn()%>' /><br/>
		Initialer: <input name='ini' value='<%=operator.getIni()%>' /><br/>
		Cpr-nummer: <input name='cpr' value='<%=operator.getCpr()%>' /><br/>
		Password: <input name='password' value='<%=operator.getPassword()%>' /><br/>
		<input type=submit name='delete' value='delete' />
		<input type=submit name='edit' value='edit' />
	</form>
	<a href=administrate.jsp>Tilbage til operatørliste</a>
	

</body>
</html>