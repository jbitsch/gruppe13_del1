<%@page import="Recept.Recept"%>
<%@ page language="java" import="java.util.*" %>
 

<%!
	ArrayList<Recept> orders = new ArrayList<Recept>();

	String validateRecept (String recept)
	{	
		String error = "";
		int receptInt = 0;

		try {
			receptInt = Integer.parseInt(recept);
		} catch (Exception e) {
			error = error +"Recept nr. skal være et tal. <br>";
		}
		if (receptInt > 99999999||receptInt<1)
			error = error + "Recept nr. skal være mellem 1 og 99999999 <br>";
	
		return error;
	}
	
	String validateVareNr (String vareNr)
	{	
		String error = "";
		int vareNrInt = 0;

		try {
			vareNrInt = Integer.parseInt(vareNr);
		} catch (Exception e) {
			error = error +"Vare nr. skal være et tal.<br> ";
		}
		if (vareNrInt > 99999999||vareNrInt<1)
			error = error + "Vare nr. skal være mellem 1 og 99999999 <br>";
	
		return error;
	}
	String validateVareNavn (String vareNavn)
	{	
		String error = "";

		int length = vareNavn.length();
		if (length > 20)
			error = error + "Længden på navnet må maks være 20 karakterer.<br> ";
		else if (length < 2)
			error = error + "Længden på navnet må skal minimum være 2 karakterer.<br> ";
		return error;
	}
	String validateNetto (String netto, String tol)
	{	
		String error = "";
		
		double nettoDou = 0;
		double tolDou = 0;
		
		try {
			tolDou = Double.parseDouble(tol);
		} catch (Exception ee) {
			error = error +"Nomiel netto skal være et tal.<br> ";
		}
		
		try {
			nettoDou = Double.parseDouble(netto);
		} catch (Exception e) {
			error = error +"Nomiel netto skal være et tal.<br> ";
		}
		if (nettoDou > 6000.0||nettoDou<50.0)
			error = error + "Nomiel netto skal være mellem 50 og 6000.<br> ";
	
		if(tolDou > 10.0)
			error = error + "Tolerancen må maks være 10 procent.<br> ";
		else if(tolDou < 0.1)
			error = error + "Tolerancen skal minimum være 0.1 procent.<br> ";
		
		double tolNetto = (tolDou/100)*nettoDou;
		if(tolNetto < 1.0)
			error = error + "Tolerancen skal minimum udgøre 1g af netto vægten.<br> ";
			
		return error;
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<title>Indtastningsformular</title>
<body>
<%
	
	if (request.getMethod().equals("POST")) { // brugeren har tastet på submit
		String recept = request.getParameter("receptNr");
		String vareNr = request.getParameter("vareNr");
		String vareNavn = request.getParameter("vareNavn");
		String nomNetto = request.getParameter("nomNetto");
		String tolNetto = request.getParameter("tolNetto");
		
		String error1 = validateRecept(recept);
		String error2 = validateVareNr(vareNr);
		String error3 = validateVareNavn(vareNavn);
		String error4 = validateNetto(nomNetto,tolNetto);
		
		String error = error1+error2+error3+error4;
		Date date = new Date();
		if (error.equals(""))
		{
			response.sendRedirect("VisOkData.jsp?receptNr="+recept+"&vareNr="+vareNr+"&vareNavn="+vareNavn+"&nomNetto="+nomNetto+"&tolNetto="+tolNetto+"&date="+date);
			Recept r = new Recept();
			r.setReceptNr(Integer.parseInt(recept));
			r.setVareNr(Integer.parseInt(vareNr));
			r.setvNavn(vareNavn);
			r.setNetto(Double.parseDouble(nomNetto));
			r.setTolerance(Double.parseDouble(tolNetto));
			r.setDate(date);
			orders.add(r);
		}
		else
			response.sendRedirect("VisError.jsp?error="+error);
	}
%>
<form method="POST" action="InputForm.jsp">
	Recept nr: 
		<input type="text" name="receptNr" value=""><br>
    Vare nr: 
    	<input type="text" name="vareNr" value=""><br>
    Varenavn:
    	<input type="text" name="vareNavn" value=""><br>
    Nomiel nettovægt(i gram):
    	<input type="text" name="nomNetto" value=""><br>
    Tolerance(På netto)(i %):
    	<input type="text" name="tolNetto" value=""><br>
	<input type="submit" name = "sub" value="OK">
</form>

<H1>Gemte ordre: </H1>
<%

for (int i=0; i < orders.size(); i++)
{
	out.println(orders.get(i)+"<br />");
}
%>

</body>
</html>