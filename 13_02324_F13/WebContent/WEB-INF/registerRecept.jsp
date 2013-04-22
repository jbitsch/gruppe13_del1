<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register recept</title>
</head>
<body>
	<% 
	File test = new File(application.getRealPath("/test.txt"));
	
	// Tjekker, om et nyt recept skal gemmes og gemmer det
	if(session.getAttribute("receptNo") != null)
	{		
		String receiptNo = (String)session.getAttribute("receptNo");
		String productNo = (String)session.getAttribute("productNo");
		String productName = (String)session.getAttribute("productName");
		String desiredWeight = (String)session.getAttribute("desiredWeight");
		String tolerance = (String)session.getAttribute("tolerance");
		
		FileWriter fileWriter = new FileWriter(test, true);
		BufferedWriter outStream = new BufferedWriter(fileWriter);
		outStream.write(receiptNo + ";" + productNo + ";" + productName + ";" + desiredWeight + ";" + tolerance + "\n");
		outStream.close();
		fileWriter.close();
		
		out.println("Din recept er nu gemt i filen:");
		out.println(test.getAbsoluteFile());
	}
	
	
	// Hent alle recepter fra recepts.txt
	FileInputStream fstream = new FileInputStream(test);
	DataInputStream in = new DataInputStream(fstream);
	BufferedReader br = new BufferedReader(new InputStreamReader(in));
	
	ArrayList<String> recepts = new ArrayList<String>();
	
	String recept;
	while((recept = br.readLine()) != null)
	{
		recepts.add(recept);
	}
	
	// Vis tabel over recepter, hvis der er nogle
	if(recepts.size() != 0)
	{
		String[] categories = {"Receptnummer: ", "Produktnummer: ", "Produktnavn: ", "Desired weight: ", "Tolerance: "};	
		int categoryNumber;
		
		out.println("<br/><br/><p>Tabel over recepter</p>");
		for(int i = 0; i < recepts.size(); i++)
		{
			out.println("<p>");
			categoryNumber = 0;
			out.print("<br/>" + categories[categoryNumber]);
			
			for(char character: recepts.get(i).toCharArray())
			{
				if(character == ';')
				{
					categoryNumber++;
					out.print("<br/>" + categories[categoryNumber]);
				}
				else
				{
					out.print(character);
				}
			}
			out.println("</p>");
		}
	}
	
	br.close();
	in.close();
	%>
</body>
</html>