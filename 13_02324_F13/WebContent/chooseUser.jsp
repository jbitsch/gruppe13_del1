<jsp:useBean id="valg" class="controller.BrugerValg" scope="session" />
<html>
<head><title>V�lg bruger</title></head>
<body>
<h1>V�lg bruger</h1>

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
<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" value="V�lg bruger">
</form>

</body>

</html>