<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:useBean id="raavareAdmin" class="controller.RaavareAdministration" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head><title>Vælg Råvarebatch</title></head>
<body>
<h1>Vælg råvarebatch</h1>

<form method="POST">
<%
	for (int i=0; i<raavareAdmin.getRaavarebatch().size(); i++) {
		dto.RaavareBatchDTO raavareBatch = (dto.RaavareBatchDTO) raavareAdmin.getRaavarebatch().get(i);
		%>
			Råvare: <%=raavareBatch.getRaavare().getRaavareNavn() %>, mængde tilbage:  <%=raavareBatch.getMaengde() %>kg., leverandør <%=raavareBatch.getRaavare().getLeverandoer() %>, indkøbt dato <%=raavareBatch.getDato() %> <br>
		<%
	}
%>
<input type = "submit" name="menuValg" value="Tilbage">
</form>
</body>
</html>