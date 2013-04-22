<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<% 
String connectionURL = "jdbc:mysql://localhost:3306/test";

Connection connection = null;

Class.forName("com.mysql.jdbc.Driver").newInstance();

connection = DriverManager.getConnection(connectionURL, "root", "root");
try
{
if(!connection.isClosed())
{
%>
<font size="+3 color=green"><b>
<%
out.println("Success!!");
connection.close();
}
%>
</b></font>
<font size="+3 color=green"><b>
<%
}
catch(Exception ex)
{
	out.println("fail...");
}
%>
</b></font>
</body>
</html>