<%@page import="org.apache.jasper.tagplugins.jstl.core.If"%>
<%@page import="dto.ReceptKompDTO"%>
<%@page import="dto.ProduktBatchDTO"%>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.text.DecimalFormat" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="produktAdmin" class="model.ProduktAdministration" type="model.ProduktAdministration" scope="session"/>
<%
double tara;
double netto;
int pb_id;
double sTara = 0;
double sNetto = 0;
boolean check = true;
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
	<%@include file="../../static/style2.css" %>
</style>


<title>Produktbatch</title>
</head>
<body>
<div class="header">
		<div class="headerContent">
			<jsp:include page="menubar.jsp" />
		</div>
	</div>
	
	<div class="content">
	<%
	ProduktBatchDTO produktBatch = produktAdmin.getProduktBatch();
	%>
<%
String status = "Ikke påbegyndt";

String dStart = "Ikke startet";
if(produktBatch.getDatoStart()!=null)
	dStart = produktBatch.getDatoStart().toString();

String dSlut = "Ikke afsluttet";
if(produktBatch.getDatoSlut()!=null)
	dSlut = produktBatch.getDatoSlut().toString();

if(produktBatch.getStatus()==1)
{
	status = "Under produktion";
}
else if(produktBatch.getStatus()==2)
{
		status = "Afsluttet";
}
else if(produktBatch.getStatus()==3)
{
		status = "FEJL i afvejning";
}
SimpleDateFormat formatter=new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
java.util.Date mydate = new java.util.Date();
String dates=formatter.format(mydate);
%>		
<p class="information">
Udskrevet:          <%=dates %> <br>
Produkt Batch id:   <%=produktBatch.getPbId() %> <br>
Recept nr.:         <%=produktBatch.getRecept().getReceptId() %>
</p>
	<%
	for (int i=0; i<produktAdmin.getReceptKomp().size(); i++) {
			
			if (i+1 <= produktAdmin.getproduktbatchKomp().size()){
				dto.ProduktBatchKompDTO produktbatchKomp = (dto.ProduktBatchKompDTO) produktAdmin.getproduktbatchKomp().get(i);
				tara = produktbatchKomp.getTara();
				sTara = sTara + tara;
				netto = produktbatchKomp.getNetto();
				sNetto = sNetto + netto;
				pb_id = produktbatchKomp.getPbId();
			}else{
				check = false;
				tara = 0;
				netto = 0;
				pb_id = 0;
			}
			ReceptKompDTO receptKomp = (dto.ReceptKompDTO) produktAdmin.getReceptKomp().get(i);
%>
<div class="hr"></div>
<p class="information">
Råvare nr.:	<%=receptKomp.getRaavare().getRaavareId() %><br>
Råvare navn: <%=receptKomp.getRaavare().getRaavareNavn() %><br>
</p>
<div class="punkHr"></div>
<table id="tableTemplate">
		<tbody>		
			<tr>
				<td style="vertical-align: top;">Del<br>
				</td>
				<td style="vertical-align: top;">Mængde<br>
				</td>
				<td style="vertical-align: top;">Tolerance<br>
				</td>
				<td style="vertical-align: top;">Tara<br>
				</td>
				<td style="vertical-align: top;">Netto(kg)<br>
				</td>
				<td style="vertical-align: top;">Batch<br>
				</td>
				<td style="vertical-align: top;">Opr.<br>
				</td>
				<td style="vertical-align: top;">Terminal<br>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: top;">1</td>
				<td style="vertical-align: top;"><%=receptKomp.getNomNetto() %></td>
				<td style="vertical-align: top;">&plusmn<%=receptKomp.getTolerance() %>%<br></td>
				<%
				if(check){
				%>
				<td style="vertical-align: top;"><%=tara %></td>
				<td style="vertical-align: top;"><%=netto %></td>
				<td style="vertical-align: top;"><%=pb_id %></td>
				<td style="vertical-align: top;"><%=produktAdmin.getProduktBatch().getOpr().getIni() %></td>
				<td style="vertical-align: top;">1</td>
				<%} %>
			</tr>
		</tbody>
	</table>
<%
}%>
<p class=information>
<%
	DecimalFormat df = new DecimalFormat("#.000");
	df.format(sNetto); 
%>
Sum Tara: 	<%=sTara %><br>
Sum Netto:	<%=df.format(sNetto) %><br>
</p>

<p class=information>
Produktion Status:  <%=status %><br>
Produktion Startet: <%=dStart %><br>
Produktion Slut:    <%=dSlut %><br>
</p>
<form method="POST">
<input type="hidden" name="menuValg" value="Vis produktbatch" />

<div class="bottomSubmit">	
	<input type = "submit" name="Tilbage" value="Tilbage">
</div>

</form>
</div>



</body>
</html>
