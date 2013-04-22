<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
  
<%
String receiptNo = null;
String productNo = null;
String productName = null;
String desiredWeight = null;
String tolerance = null;

String receiptError;
String productMatchError;
String weightError;
String toleranceError;

boolean validReceipt = true;
%>
  
<%!
String validReceiptNo(int receiptNo)
{
	String error = "Receipt nummeret skal være et heltal (1-99999999)<br/>";
	
	// Heltal mellem 1 og 99999999
	if(receiptNo >= 1 && receiptNo <= 99999999)
	{
		return "";
	}
	return error;
	
	
	// Må ikke findes i forvejen
	// Kan flere så tilføje samme receiptnavn, hvis de gør det samtidig? syncronize?.. og tjek igangværende
	
}

String validProductMach(String productNo, String productName)
{
	String error = "Indtast et gyldigt produktnummer<br/>";
	
	if(productNo == null || productName == null)
	{
		return error;
	}
	
	// 2-20 karakterer
	if(productName.length() >= 2 && productName.length() <= 20)
	{
		return "";
	}
	// productName skal findes i databasen udfor angivet productNo (gør den det, indeholder den IKKE semikolon)
	return error;
}

String validWeight(double weight)
{
	String error = "Indtast vægt (50-6000g)<br/>";
	
	// 50-6000g
	if(weight >= 50 && weight <= 6000)
	{
		return "";
	}
	// Skal være nok tilbage i database + nuværende recepter (med tolerance indberegnet?)
	return error;
}

String validTolerance(double tolerance, double weight)
{
	String error = "Tolerancen skal være 0,1-10% af vægten (min 1 gram)<br/>";

	// Min 0,1 % (dog mindst 1 g), max 10 %
	if(tolerance >= 0.1 && tolerance <= 10)
	{
		if(tolerance/100 * weight < 1)
		{
			error = "Tolerancen skal minimum være på 1 gram (her: " + 1/weight * 100 + " %)<br/>";
		}
		else
		{
			return "";
		}
	}
	return error;
}

int toInt(String var)
{
	try
	{
		int varAsInt = Integer.parseInt(var);
		return varAsInt;
	}
	catch (NumberFormatException e) {}
	catch (NullPointerException e) {}
	return -1;
}

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
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Enter recept</title>
	</head>
	
	<body>
	
<%
String submitted = request.getParameter("submit");

if(submitted != null)
{
	receiptNo = request.getParameter("receptNo");
	productNo = request.getParameter("productNo");
	productName = request.getParameter("productName");
	desiredWeight = request.getParameter("desiredWeight");
	tolerance = request.getParameter("tolerance");
}

receiptError = validReceiptNo(toInt(receiptNo));
productMatchError = validProductMach(productNo, productName);
weightError = validWeight(toDouble(desiredWeight));
toleranceError = validTolerance(toDouble(tolerance), toDouble(desiredWeight));

if(receiptError != "")
{
	receiptNo = "";
	validReceipt = false;
}
if(productMatchError != "")
{
	productNo = "";
	productName = "";
	validReceipt = false;
}
if(weightError != "")
{
	desiredWeight = "";
	validReceipt = false;
}
if(toleranceError != "")
{
	tolerance = "";
	validReceipt = false;
}

if(validReceipt == true)
{
	session.setAttribute("receptNo", receiptNo);
	session.setAttribute("productNo", productNo);
	session.setAttribute("productName", productName);
	session.setAttribute("desiredWeight", desiredWeight);
	session.setAttribute("tolerance", tolerance);
	
	response.sendRedirect("registerRecept.jsp");
}

%>
	
		<form method = "GET" action = "inputRecept.jsp">
			<%
			if(submitted != null)
			{
				out.println(receiptError);
			}
			%>
			Receipt nr: <input type = "text" name = "receptNo" value = "<%= receiptNo %>"><br />
			<%
			if(submitted != null)
			{
				out.println(productMatchError);
			}
			%>
			Vare nr: <input type = "text" name = "productNo" value = "<%= productNo %>"><br />
			Vare navn: <input type = "text" name = "productName" value = "<%= productName %>"><br />
			<%
			if(submitted != null)
			{
				out.println(weightError);
			}
			%>
			Ønsket vægt (i gram): <input type = "text" name = "desiredWeight" value = "<%= desiredWeight %>"><br />
			<%
			if(submitted != null)
			{
				out.println(toleranceError);
			}
			%>
			Tolerance (netto, i %): <input type = "text" name = "tolerance" value = "<%= tolerance %>"><br />
			<input type = "submit" name = "submit" value = "Registrer">
		</form>
	
	</body>
</html>