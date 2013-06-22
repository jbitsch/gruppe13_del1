<%@page import="dto.RaavareDTO"%>
<%@page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="raavareAdmin" class="model.RaavareAdministration" type="model.RaavareAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style2.css" %>
</style>

<title>Råvarebatch form</title>
</head>
<body>

<jsp:include page="menubar.jsp" /><br>
<h1>Opret raavarebatch</h1>

<p><font color="red"><%=raavareAdmin.getError() %></font><font color="green"><%= raavareAdmin.getSucces() %></font><br>

<form method="POST" action="">
	<table border="0">
	<tr>
		<td>Råvarebatch id:</td>
		<td> <input name="raavarebatchId" type="text" size = 8 value = "<%=raavareAdmin.getRaavareBatchId()  %>"></td>
	</tr>
	<tr>
		<td>Leverandøer:</td>
		<td> <input name="leverandoer" type="text" size = 20 value = "<%=raavareAdmin.getLeverandoer()  %>"></td>
	</tr>
	<tr>
		<td>Mængde:</td>
		<td> <input name="raavareMaengde" type="text" size = 8 value = "<%= raavareAdmin.getMaengde() %>"></td>
	</tr>
	</table>
	
	<br><b>Vælg råvare:</b><br>
	<%
	ArrayList<RaavareDTO> raavareList = raavareAdmin.getRaavare();
	for (int i=0; i<raavareList.size(); i++) {
		RaavareDTO raavare = raavareList.get(i);
		%>
			<input type="radio" name="raavarevalgBatch" value="<%= raavare.getRaavareId() %>">
			Råvare: <%= raavare.getRaavareNavn() %><br>
		<%
	}
%>

<div class="bottomSubmit">
	<input type="submit" name="handling" value="Opret Raavarebatch">
</div>

</form>



</body>
</html>