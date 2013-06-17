<jsp:useBean id="brugerAdmin" class="model.BrugerAdministration" scope="session" />
<html>

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<head><title>Vælg bruger</title></head>
<body>
<h1>Vælg bruger</h1>

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
	<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" value="Vælg bruger">
</div>

</form>

</body>

</html>