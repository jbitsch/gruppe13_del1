<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data godkendt</title>
</head>
<body>
<%
	if (request.getMethod().equals("POST")) { // brugeren har tastet p� submit
		response.sendRedirect("InputForm.jsp");
	}
%>
<b>De indtastede v�rdier er godkendte. Du indtastede:</b> <br>
Recept nr.: <%= request.getParameter("receptNr") %> <br>
Vare nr.: <%= request.getParameter("vareNr") %> <br>
Varenavn: <%= request.getParameter("vareNavn") %> <br>
Nomiel nettov�gt: <%= request.getParameter("nomNetto") %> <br>
Tolerance(p� netto)(i %): <%= request.getParameter("tolNetto") %> <br>
Dato: <%= request.getParameter("date") %> <br>

	<form method="GET" action="InputForm.jsp">
		<input type="submit" value="OK">
	</form>
</body>
</html>
