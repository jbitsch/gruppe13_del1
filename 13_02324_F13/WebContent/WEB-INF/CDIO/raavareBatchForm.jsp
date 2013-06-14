<%@page import="dto.RaavareDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="raavareAdmin" class="controller.RaavareAdministration" type="controller.RaavareAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>R�varebatch form</title>
</head>
<body>


<h1>Opret raavarebatch</h1>

<p><font color="red"><%=raavareAdmin.getError() %></font><font color="green"><%= raavareAdmin.getSucces() %></font><br>

<form method="POST">
<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	
	R�varebatch id: <input name="raavarebatchId" type="text" size = 8 value = "<%=raavareAdmin.getRaavareBatchId()  %>"><br>
	Leverand�er: <input name="leverandoer" type="text" size = 20 value = "<%=raavareAdmin.getLeverandoer()  %>"><br>
	M�ngde: <input name="raavareMaengde" type="text" size = 8 value = "<%= raavareAdmin.getMaengde() %>"><br>
	
	V�lg r�vare:<br>
	<%
	for (int i=0; i<raavareAdmin.getRaavare().size(); i++) {
		RaavareDTO raavare = (dto.RaavareDTO) raavareAdmin.getRaavare().get(i);
		%>
			<input type="radio" name="raavarevalgBatch" value="<%= raavare.getRaavareId() %>">
			R�vare: <%= raavare.getRaavareNavn() %><br>
		<%
	}
%>
<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" name="handling" value="Opret Raavarebatch">

</form>



</body>
</html>