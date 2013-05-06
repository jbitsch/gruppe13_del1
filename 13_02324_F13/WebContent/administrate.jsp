<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="data.OperatoerDTO2" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="function" class="funktionalitet.Funktionalitet2" type="funktionalitet.IFunktionalitet2" scope="application"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Administrær operatører</title>
</head>
<body>
	<%
	if(session.getAttribute("loginId") == null)
	{
		response.sendRedirect("login.jsp");
	}
	else if(!((String)session.getAttribute("loginId")).equals("10"))
	{
		response.sendRedirect("menu.jsp");
	}
	else
	{
		if(request.getParameter("foldOut") != null)
		{
			try
			{
				if(((Boolean)session.getAttribute(request.getParameter("id"))) != false)
				{
					session.setAttribute(request.getParameter("id"), false);
				}
				else
				{
					session.setAttribute(request.getParameter("id"), true);
				}
			}
			catch (NullPointerException e){};
		}
		

		out.print("<h1 style='text-align:center'>Operatører</h1>");
		ArrayList<OperatoerDTO2> users = function.getUsers();
		
		for(int i = 0; i < users.size(); i++)
		{
			out.print("<form name=userList action=administrate.jsp method=get style='text-align:center'>");
			
			OperatoerDTO2 user = users.get(i);
			out.print("<input type=hidden name='id' value='" + user.getOprId() + "'/>");
			out.print("<input type=submit name='foldOut' value='" + user.getOprId() + ": " + user.getOprNavn() + "' size='40' style='width:300px; font-size:180%'/><br/>");
			try
			{
				String curId = Integer.toString(users.get(i).getOprId());
				if((Boolean)session.getAttribute(curId) == true)
				{
					out.print("Cpr-nr: " + user.getCpr() + "<br/>");
					out.print("Password: " + user.getPassword() + "<br/>");
					out.print("<a href=editUser.jsp?id=" + user.getOprId() + ">Rediger/slet</a><br/>");
				}
			}
			catch (NullPointerException e)
			{
				session.setAttribute(Integer.toString(users.get(i).getOprId()), false);
			}
			
			out.print("</form>");
		}
	}
	%>
</body>
</html>