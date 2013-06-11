<jsp:useBean id="valg" class="controller.BrugerValg" scope="session" />
<html>
<head><title>Vælg bruger</title></head>
<body>
<h1>Vælg bruger</h1>

<form method="POST">
<%
	for (int i=0; i<valg.getUsers().size(); i++) {
		dto.OperatoerDTO user = (dto.OperatoerDTO) valg.getUsers().get(i);
		%>
			<input type="radio" name="brugervalg" value="<%= user.getOprId() %>">
			Bruger: <%= user.getOprId() %> Navn <%= user.getOprNavn() %><br>
		<%
	}
%>
<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" value="Vælg bruger">
</form>

</body>

</html>