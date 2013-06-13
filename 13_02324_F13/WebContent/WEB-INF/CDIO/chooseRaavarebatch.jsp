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


<%
	if(raavareAdmin.getRaavarebatch().size()==0)
	{
		out.println("Der findes ingen raavarebatches med raavarenavnet: "+ raavareAdmin.getRaavareNavn());
	}
	for (int i=0; i<raavareAdmin.getRaavarebatch().size(); i++) {
		dto.RaavareBatchDTO raavareBatch = (dto.RaavareBatchDTO) raavareAdmin.getRaavarebatch().get(i);
		%>
			Råvarebatch id <%=raavareBatch.getRbId() %>: <%=raavareBatch.getRaavare().getRaavareNavn() %>, mængde tilbage:  <%=raavareBatch.getMaengde() %>kg., leverandør <%=raavareBatch.getLeverandoer() %>, indkøbt dato <%=raavareBatch.getDato() %> <br>
		<%
	}
%>
<form method="POST">
<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	<br>

Indtast råvare navn for at søge:<input type = "text" name = "searchRBatch"><br>
<input type = "submit" name="menuValg" value="Tilbage"><input type = "submit" name="searchRB" value="Soeg på raavare navn">
</form>
</body>
</html>