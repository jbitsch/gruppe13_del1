<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="produktAdmin" class="model.ProduktAdministration" type="model.ProduktAdministration" scope="session"/>
<jsp:useBean id="receptKomp" class="model.ProduktAdministration" type="model.ProduktAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<title>Recept formular</title>

</head>
<body>
<jsp:include page="menubar.jsp" /><br>
<h1>Recept formular</h1>
<font color="red"><%=produktAdmin.getError() %></font><font color="green"><%= produktAdmin.getSucces() %></font><br>
<form method="POST">
<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	
	Recept id: <input name="receptId" type="text" size = 8 value = "<%= produktAdmin.getReceptId() %>"><br>
	Recept navn: <input name="receptNavn" type="text" size = 20 value = "<%= produktAdmin.getReceptNavn() %>"><br>
	
	<%
	for (int i=0; i<produktAdmin.getRaavare().size(); i++) {
		dto.RaavareDTO raavare = (dto.RaavareDTO) produktAdmin.getRaavare().get(i);
		%>
			<input type="checkbox" name="raavareValg" value="<%= raavare.getRaavareId() %>">
			Navn: <%= raavare.getRaavareNavn() %> <br>
		<%
	}
%>

	<div class="bottomSubmit">
		<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" name="handling" value="Opret recept">
	</div>

</form>
</body>
</html>