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

<head><title>R�varebatch oversigt</title></head>
<body>
<jsp:include page="menubar.jsp" /><br>
<h1>R�varebatch oversigt</h1>


<%
	ArrayList<dto.RaavareBatchDTO>raavarebatchList = raavareAdmin.getRaavarebatch();
	if(raavarebatchList.size()==0)
	{
		out.println("Der findes ingen raavarebatches med raavarenavnet: "+ raavareAdmin.getRaavareNavn());
	}
	for (int i=0; i<raavarebatchList.size(); i++) {
		dto.RaavareBatchDTO raavareBatch = raavarebatchList.get(i);
		double maengde = raavareBatch.getMaengde();
		String color = "black";
		if(maengde<=0)
			color = "red";
			
		%>
			<font color="<%=color%>">R�varebatch id <%=raavareBatch.getRbId() %>: <%=raavareBatch.getRaavare().getRaavareNavn() %>, m�ngde tilbage:  <%=raavareBatch.getMaengde() %>kg., leverand�r <%=raavareBatch.getLeverandoer() %>, indk�bt dato <%=raavareBatch.getDato() %></font> <br/>
		<%
	}
%>


<form method="POST">
	<div class="bottomSubmit">
		Indtast r�vare navn for at s�ge:<input type = "text" name = "searchRBatch">
	</div>
	
	<div class="bottomSubmit">
		<input type = "submit" name="searchRB" value="Soeg p� raavare navn">
	</div>
	
</form>
	
</body>
</html>