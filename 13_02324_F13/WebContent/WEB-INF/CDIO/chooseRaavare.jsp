<%@page import="dto.RaavareDTO"%>
<jsp:useBean id="raavareAdmin" class="controller.RaavareAdministration" scope="session" />
<html>
<head><title>Vælg Råvare</title></head>
<body>
<h1>Vælg råvare</h1>

<form method="POST">
<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	
<%
	for (int i=0; i<raavareAdmin.getRaavare().size(); i++) {
		RaavareDTO raavare = (dto.RaavareDTO) raavareAdmin.getRaavare().get(i);
		%>
			<input type="radio" name="raavarevalg" value="<%= raavare.getRaavareId() %>">
			Råvare: <%= raavare.getRaavareNavn() %> Leverandør <%= raavare.getLeverandoer() %><br>
		<%
	}
%>
<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" value="Vælg raavare">
</form>

</body>

</html>