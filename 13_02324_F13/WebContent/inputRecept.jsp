<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.ArrayList" %>
 
<%
String receptNo = "";
String productNo = "";
String productName = "";
String desiredWeight = "";
String tolerance = "";

String receptError;
String productNumError;
String productNameError;
String weightError;
String toleranceError;

boolean validRecept = true;
%>
  
<%!
ArrayList<String[]> getRecepts(File receptFile)
{	
	ArrayList<String[]> recepts = new ArrayList<String[]>();
	try
	{
		receptFile.createNewFile();
		FileInputStream fstream = new FileInputStream(receptFile);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		String recept;
		while((recept = br.readLine()) != null)
		{
			recepts.add(recept.split(";"));
		}
	}
	catch(FileNotFoundException e){}
	catch(IOException e){}
	return recepts;
}

String validReceptNo(int receptNo, ArrayList<String[]> recepts)
{
	String error = "Recept nummeret skal være et heltal (1-99999999)<br/>";
	
	// Heltal mellem 1 og 99999999
	if(receptNo >= 1 && receptNo <= 99999999)
	{
		// Må ikke findes i forvejen
		boolean numberTaken = false;
		for(int i = 0; i < recepts.size(); i++)
		{
			int currentNumber = Integer.parseInt(recepts.get(i)[0]);
			if(currentNumber == receptNo)
			{
				numberTaken = true;
			}
		}
		if(numberTaken)
		{
			error = "Recept nummeret er allerede i brug<br/>";
		}
		else
		{
			return "";
		}
	}
	return error;
}
String validProductNo(int productNum)
{
	String error = "Produkt nummeret skal være et heltal (1-99999999)<br/>";
	
	// Heltal mellem 1 og 99999999
	if(productNum >= 1 && productNum <= 99999999)
	{
		return "";
	}
	return error;
}
String validProductName(String productName)
{
	String error = "Prouktnavnet skal have en laengde mellem 2 og 20<br/>";
	
	if(productName.length() < 2 || productName.length() > 20)
	{
		return "Prouktnavnet skal have en laengde mellem 2 og 20<br/>";
	}
	
	// 2-20 karakterer
	if(productName.length() >= 2 && productName.length() <= 20)
	{
		// Maa ikke indeholde semikolon
		if(productName.contains(";"))
		{
			error = "Produktnavnet må ikke indeholde semikolon.<br/>";
		}
		else
		{
			return "";
		}
	}
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
File receptFile = new File(application.getRealPath("/recepts.txt"));
ArrayList<String[]> receptArray = getRecepts(receptFile);

String submitted =  request.getParameter("submit");
if(submitted!=null)
{
	if(submitted.equals("Registrer"))
	{
		receptNo = request.getParameter("receptNo");
		productNo = request.getParameter("productNo");
		productName = request.getParameter("productName");
		desiredWeight = request.getParameter("desiredWeight");
		tolerance = request.getParameter("tolerance");
	}
	else if(submitted.equals("Slet recept"))
	{
		response.sendRedirect("delete.jsp");
	}
}

receptError = validReceptNo(toInt(receptNo), receptArray);
productNumError = validProductNo(toInt(productNo));
productNameError = validProductName(productName);
weightError = validWeight(toDouble(desiredWeight));
toleranceError = validTolerance(toDouble(tolerance), toDouble(desiredWeight));

if(receptError != "")
{
	receptNo = "";
	validRecept = false;
}
if(productNumError != "")
{
	productNo = "";
	validRecept = false;
}
if(productNameError != "")
{
	productName = "";
	validRecept = false;
}
if(weightError != "")
{
	desiredWeight = "";
	validRecept = false;
}
if(toleranceError != "")
{
	tolerance = "";
	validRecept = false;
}

if(validRecept == true)
{
	session.setAttribute("receptNo", receptNo);
	session.setAttribute("productNo", productNo);
	session.setAttribute("productName", productName);
	session.setAttribute("desiredWeight", desiredWeight);
	session.setAttribute("tolerance", tolerance);
	
	response.sendRedirect("registerRecept.jsp");
}

%>
		<h2>Ny receptregistrering</h2>	
		<form method = "GET" action = "inputRecept.jsp">
			<%
			if(submitted != null)
			{
				out.println("<font color = 'red'>" + receptError + "</font>");
			}
			%>
			Recept nr: <input type = "text" name = "receptNo" value = "<%= receptNo %>"><br />
			<%
			if(submitted != null)
			{
				out.println("<font color = 'red'>" + productNumError + "</font>");
			}
			%>
			Vare nr: <input type = "text" name = "productNo" value = "<%= productNo %>"><br />
			<%
			if(submitted != null)
			{
				out.println("<font color = 'red'>" + productNameError + "</font>");
			}
			%>
			Vare navn: <input type = "text" name = "productName" value = "<%= productName %>"><br />
			<%
			if(submitted != null)
			{
				out.println("<font color = 'red'>" + weightError + "</font>");
			}
			%>
			Ønsket vægt (i gram): <input type = "text" name = "desiredWeight" value = "<%= desiredWeight %>"><br />
			<%
			if(submitted != null)
			{
				out.println("<font color = 'red'>" + toleranceError + "</font>");
			}
			%>
			Tolerance (netto, i %): <input type = "text" name = "tolerance" value = "<%= tolerance %>"><br />
			<input type = "submit" name = "submit" value = "Registrer">
			<input type = "submit" name = "submit" value = "Slet recept">
		</form>
	
	</body>
</html>