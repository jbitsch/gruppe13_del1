<%@page import="dto.RaavareDTO"%>
<jsp:useBean id="raavareAdmin" class="controller.RaavareAdministration" scope="session" />
<html>
<head><title>V�lg R�vare</title></head>
<body>
<h1>V�lg r�vare</h1>

<form method="POST">
<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	
<%
	for (int i=0; i<raavareAdmin.getRaavare().size(); i++) {
		RaavareDTO raavare = (dto.RaavareDTO) raavareAdmin.getRaavare().get(i);
		%>
			<input type="radio" name="raavarevalg" value="<%= raavare.getRaavareId() %>">
			R�vare: <%= raavare.getRaavareNavn() %> Leverand�r <%= raavare.getLeverandoer() %><br>
		<%
	}
%>
<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" value="V�lg raavare">
</form>

</body>

</html>