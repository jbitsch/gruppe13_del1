<%@page import="dto.ReceptDTO" %>
<%@page import="java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="produktAdmin" class="model.ProduktAdministration" type="model.ProduktAdministration" scope="session"/>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />

		<style type="text/css">
			<%@include file="../../static/style2.css" %>
		</style>
		
		<title>Opret produktbatch</title>
	</head>
	
	<body>

		<div class="header">
			<div class="headerContent">
				<jsp:include page="menubar.jsp" />
			</div>
		</div>
		
		<div class="content">
			<div class="page">
				
				<h1>Vaelg recept der skal oprettes som produktbatch</h1>
				
				<p><font color="red"><%=produktAdmin.getError() %></font><font color="green"><%= produktAdmin.getSucces() %></font><br>
				
				<div class="indhold">
					<div  id="receptListe">
					   <div id="radios">
						   <form method="POST">
								<%
									ArrayList<ReceptDTO>recepter =  produktAdmin.getRecepter();
							
									for (int i=0; i<recepter.size(); i++) {
								
									ReceptDTO recept = recepter.get(i);
									int id = recept.getReceptId();
								%>
									<div class="row">
										<input type="radio" name="produktbatchReceptId" value="<%=id %>" style="display:inline"/>
										Receptnavn: <%= recept.getReceptNavn() %>
									</div>
								<%
									}
								%>
									<div class="bottomSubmit">
										<input type="submit" name="handling" value="Opret produktbatch">
									</div>
							</form>
					   
					   </div>
					   <div id="visRaavareKnapper">
					   <%
					   	for (int i=0; i<recepter.size(); i++) {
					   	%>
					   	<div class="row">
						   	<form method="post">
							   	<input class="submitLink" type ="submit" name="show" value="Vis raavare">
							   	<input type="hidden" name="visInfo" value="<%=recepter.get(i).getReceptId()%>"/> <br>
						   	</form>
						</div>
					   	<%
					   	}
					   
					   %>
					   </div>
				   </div>
				   <div id="raavareBox">
					<%
						ArrayList<dto.ReceptKompDTO> recepterKomp = produktAdmin.getReceptKomp();
						if(recepterKomp!=null)
						{
							out.println("<h2 class='noMaring'>Raavare:</h2>");
							if(recepterKomp.size()==0)
								out.println("Indeholder ingen raavare");
							for(int i=0; i<recepterKomp.size(); i++)
							{
								out.print("Varenavn: "+recepterKomp.get(i).getRaavare().getRaavareNavn()+"<br>");
								out.print("Netto: "+recepterKomp.get(i).getNomNetto()+"<br>");
								out.print("<br>");
							}
						}	
					%>   
				   </div>
				</div>
				<div style="clear:both"></div>
			</div>
		</div>
		
		
	</body>
	
</html>