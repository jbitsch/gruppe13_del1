<jsp:useBean id="brugerAdmin" class="model.BrugerAdministration" scope="session" />
<html>

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<head><title>V�lg bruger</title></head>
<body>
<h1>V�lg bruger</h1>

<form method="POST">
<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	
<%
	for (int i=0; i<brugerAdmin.getUsers().size(); i++) {
		dto.OperatoerDTO user = (dto.OperatoerDTO) brugerAdmin.getUsers().get(i);
		%>
			<input type="radio" name="brugervalg" value="<%= user.getOprId() %>">
			Bruger: <%= user.getOprId() %> Navn <%= user.getOprNavn() %><br>
		<%
	}
%>

<div class="bottomSubmit">
	<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" value="V�lg bruger">
</div>

</form>

</body>

</html>