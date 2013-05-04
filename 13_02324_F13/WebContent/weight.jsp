<%@ include file = "loginCheck.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<jsp:useBean id="function" class="funktionalitet.Funktionalitet2" type="funktionalitet.IFunktionalitet2" scope="application" />

<%
String tara = "";
String brutto = "";
String netto = "";

String taraError;
String bruttoError;
String nettoError;
%>

<%!
double toDouble(String var)
{
	try
	{
		double varAsDouble = Double.parseDouble(var);
		return varAsDouble;
	}
	catch (NumberFormatException e) {}
	catch (NullPointerException e) {}
	return -1;
}

String validTara(double tara){
	String error = "Indtast et positivt tal mindre eller lig brutto <br>";
	
	if (tara != -1 && tara >= 0){
		return "";
	}
	
	return error;
}

String validBrutto(double brutto){
	String error = "Indtast et positivt tal større eller lig tara <br>" ;
	
	if (brutto != -1 && brutto >= 0){
		return "";
	}
	
	return error;
}

String validNetto(double netto){
	String error = "Tara er ikke mindre eller lig brutto <br>";
	
	if (netto != -1){
		return "";
	}
	return error;
}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Nettoudregner</title>
</head>
<body>

<%
String submitted = request.getParameter("submit");
if (submitted != null){
	if ("Udregn".equals(submitted)){
		tara = request.getParameter("tara");
		brutto = request.getParameter("brutto");
	} else if ("Tilbage".equals(submitted)){
		response.sendRedirect("menu.jsp");
	}
}

taraError = validTara(toDouble(tara));
bruttoError = validBrutto(toDouble(brutto));
nettoError = validNetto(function.calculateWeight(toDouble(tara), toDouble(brutto)));

if (taraError != ""){
	tara = "";
}

if (bruttoError != ""){
	brutto = "";
}
%>

<form method="GET" action="weight3.jsp">
	<%
	if ("Udregn".equals(submitted)){
		out.println("<font color = 'red'>" + taraError + "</font>");
	}
	%>
	Tara: <input type="text" name="tara" value="<%= tara %>"><br /> 
	<%
	if ("Udregn".equals(submitted)){
		out.println("<font color = 'red'>" + bruttoError + "</font>");
	}
	%>
	Brutto: <input type="text" name="brutto" value="<%= brutto %>"><br />
	<%
	if ("Udregn".equals(submitted)){
		out.println("<font color = 'red'>" + nettoError + "</font>");
		out.println("Netto: " + function.calculateWeight(toDouble(tara), toDouble(brutto)) + "<br />");
	}
	%>
	<input type = "submit" name="submit" value="Tilbage"><input type = "submit" name="submit" value="Udregn">
</form>

</body>
</html>