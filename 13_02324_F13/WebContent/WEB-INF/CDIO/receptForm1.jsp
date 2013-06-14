<%@page import="dto.RaavareDTO"%>
<%@page import="dto.ReceptKompDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="produktAdmin" class="controller.ProduktAdministration" type="controller.ProduktAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Recept formular</title>

</head>
<body>
<h1>Opret recept</h1>

<font color="red"><%=produktAdmin.getError() %></font><font color="green"><%= produktAdmin.getSucces() %></font><br>


<div style="width: 100%;">
   <div style="float:left; width: 35%">
  <%
ArrayList<ReceptKompDTO> receptKomp = produktAdmin.getReceptKompToadd();
if(receptKomp!=null && receptKomp.size()>0 )
{
	out.print("<h2>Raavare tilfoejet recepten</h2>");
	%>
	<table border="1">	
  	<tr>
    	<th WIDTH="300">Navn</th>
    	<th WIDTH="50">Netto</th>
    	<th WIDTH="50">Slet</th>
  	</tr>
  	</table>
	
	<%
	for (int i=0; i<receptKomp.size(); i++)
	{
	%>
	<form method="POST">
		<table border="1">	
  		<tr>	 
			<td WIDTH="300"><%= receptKomp.get(i).getRaavare().getRaavareNavn()%></td>		
			<td WIDTH="50"><%= receptKomp.get(i).getNomNetto()%></td>
			<td WIDTH="50"><input type="submit" name="handling" value="Slet">
			<input type="hidden" name="raavareToDelete" value="<%= receptKomp.get(i).getRaavare().getRaavareId() %>"/>	</td>
		</tr>
		</table>
	</form>
	<%
	}
	
}

%>
<br>
<form method="POST">	
	Recept id: <input name="receptId" type="text" size = 8/><br>
	Recept navn: <input name="receptNavn" type="text" size = 20 value = "<%= produktAdmin.getReceptNavn() %>"/><br>
	<input type="hidden" name="backpage" value="/WEB-INF/CDIO/menu.jsp" />	
	<input type = "submit" name="menuValg" value="Tilbage"/><input type="submit" name="handling" value="Opret recept"/>

</form>
   </div>
   <div style="float:left;">
	<table border="1">	
  	<tr>
    	<th WIDTH="300">Navn</th>
    	<th WIDTH="100">Netto</th>
    	<th WIDTH="100">Tolerance</th>
    	<th WIDTH="130">Tilf�j</th>
  	</tr>
  	</table>	
	<%
	ArrayList<RaavareDTO> raavareList = produktAdmin.getRaavare();
	
	for (int i=0; i<raavareList.size(); i++) {
		RaavareDTO raavare = raavareList.get(i);
		%>
		
			<form method="POST">
		<table border="1">	
  		<tr>	 
			<td WIDTH="300"><%= raavare.getRaavareNavn() %></td>		
			<td WIDTH="100"><input type = "text" size = 8 name="netto"/></td>
			<td WIDTH="100"><input type = "text" size = 8 name="tolerance"/></td>
			<td WIDTH="130"><input type="submit" name="handling" value="Tilfoej raavare"/>
			<input type="hidden" name="raavareToAdd" value="<%= raavare.getRaavareId() %>"/></td>
		</tr>
		</table>
	</form>
			
			
		<%
	}
	%>
   </div>
</div>
<div style="clear:both"></div>

</body>
</html>