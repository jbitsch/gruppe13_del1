<%@page import="dto.RaavareBatchDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList" %>
<jsp:useBean id="raavareAdmin" class="model.RaavareAdministration" scope="session" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

<head><title>Vælg Råvarebatch</title></head>
<body>
<jsp:include page="menubar.jsp" /><br>
<h1>Vælg råvarebatch</h1>


<%
	ArrayList<dto.RaavareBatchDTO>raavarebatchList = raavareAdmin.getRaavarebatch();
	if(raavarebatchList.size()==0)
	{
		out.println("Der findes ingen raavarebatches med raavarenavnet: "+ raavareAdmin.getRaavareNavn());
	}
	for (int i=0; i<raavarebatchList.size(); i++) {
		dto.RaavareBatchDTO raavareBatch = raavarebatchList.get(i);
		%>
			Råvarebatch id <%=raavareBatch.getRbId() %>: <%=raavareBatch.getRaavare().getRaavareNavn() %>, mængde tilbage:  <%=raavareBatch.getMaengde() %>kg., leverandør <%=raavareBatch.getLeverandoer() %>, indkøbt dato <%=raavareBatch.getDato() %> <br/>
		<%
	}
%>


<form method="POST">
	<div class="bottomSubmit">
		Indtast råvare navn for at søge:<input type = "text" name = "searchRBatch">
	</div>
	
	<div class="bottomSubmit">
		<input type = "submit" name="searchRB" value="Soeg på raavare navn">
	</div>
	
</form>
	
</body>
</html>