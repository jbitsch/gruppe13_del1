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
	<%@include file="../../static/style2.css" %>
</style>

<title>R�varebatch oversigt</title>
</head>
<body>

	<div class="header">
		<div class="headerContent">
			<jsp:include page="menubar.jsp" />
		</div>
	</div>
	
	<div class="content">
		<h1>R�varebatch oversigt</h1>
		
		<form method="POST" action="">
			<div class="bottomSubmit">
				Indtast r�vare navn for at s�ge:<input type = "text" name = "searchRBatch"><input type = "submit" name="searchRB" value="Soeg p� raavare navn">
			</div>
			
		</form>
		
			<table border="1" cellpadding="2">	
		  	<tr>
		    	<th>R�varenavn</th>
		    	<th>R�vare id</th>
		    	<th>R�varebatch id</th>
		    	<th>M�ngde tilbage</th>
		    	<th>Leverand�r</th>
		    	<th>Indk�bs dato</th>
		  	</tr>
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
					<tr>
					<td><font color="<%=color%>"><%=raavareBatch.getRaavare().getRaavareNavn()%></font></td>
					<td align="right"><font color="<%=color%>"><%=raavareBatch.getRaavare().getRaavareId()%></font></td>
					<td align="right"><font color="<%=color%>"><%=raavareBatch.getRbId()%></font></td>
					<td align="right"><font color="<%=color%>"><%=raavareBatch.getMaengde()%></font></td>
					<td><font color="<%=color%>"><%=raavareBatch.getLeverandoer()%></font></td>
					<td><font color="<%=color%>"><%=raavareBatch.getDato() %></font></td>
					</tr>
				<%
			}
		%>
		
		</table>
	</div>
	
</body>
</html>