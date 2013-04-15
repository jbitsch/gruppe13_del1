<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Data godkendt</title>
</head>
<body>
<%
	if (request.getMethod().equals("POST")) { // brugeren har tastet på submit
		response.sendRedirect("InputForm.jsp");
	}
%>
<b>De indtastede værdier er godkendte. Du indtastede:</b> <br>
Recept nr.: <%= request.getParameter("receptNr") %> <br>
Vare nr.: <%= request.getParameter("vareNr") %> <br>
Varenavn: <%= request.getParameter("vareNavn") %> <br>
Nomiel nettovægt: <%= request.getParameter("nomNetto") %> <br>
Tolerance(på netto)(i %): <%= request.getParameter("tolNetto") %> <br>
Dato: <%= request.getParameter("date") %> <br>

	<form method="GET" action="InputForm.jsp">
		<input type="submit" value="OK">
	</form>
</body>
</html>
