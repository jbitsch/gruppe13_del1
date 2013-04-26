<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"><title>Register recept</title>
</head>
<body>
	<% 
	File receptFile = new File(application.getRealPath("/recepts.txt"));
	try
	{
		receptFile.createNewFile();
		
		// Tjekker, om et nyt recept skal gemmes og gemmer det
		if(session.getAttribute("receptNo") != null)
		{		
			String receiptNo = (String)session.getAttribute("receptNo");
			String productNo = (String)session.getAttribute("productNo");
			String productName = (String)session.getAttribute("productName");
			String desiredWeight = (String)session.getAttribute("desiredWeight");
			String tolerance = (String)session.getAttribute("tolerance");
			
			FileWriter fileWriter = new FileWriter(receptFile, true);
			BufferedWriter outStream = new BufferedWriter(fileWriter);
			outStream.write(receiptNo + ";" + productNo + ";" + productName + ";" + desiredWeight + ";" + tolerance + "\n");
			outStream.close();
			fileWriter.close();
			
			out.println("Din recept er nu gemt i filen:");
			out.println(receptFile.getAbsoluteFile() + "<br/><br/>");
			session.invalidate();
		}
		
		
		// Hent alle recepter fra recepts.txt
		FileInputStream fstream = new FileInputStream(receptFile);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		ArrayList<String[]> recepts = new ArrayList<String[]>();
		
		String recept;
		while((recept = br.readLine()) != null)
		{
			recepts.add(recept.split(";"));
		}
		
		// Vis tabel over recepter, hvis der er nogle
		if(recepts.size() != 0)
		{
			String[] categories = {"Receptnummer", "Produktnummer", "Produktnavn", "Desired weight", "Tolerance"};	
			
			out.print("<h2>Tabel over recepter</h2>");
			for(int i = 0; i < recepts.size(); i++)
			{
				out.print("<p>");
				for(int j = 0; j < categories.length; j++)
				{
					out.print(categories[j] + ": " + recepts.get(i)[j] + "<br/>");
				}
				out.print("</p>");
			}
		}
		else
		{
			out.print("<p>Der er i øjeblikket ingen recepter registreret.</p>");
		}
		
		br.close();
		in.close();
	}
	catch(IOException e)
	{
		out.print("<p>Der kan ikke oprettes kontakt til databasen. Proev venligst igen senere.</p>");
	}
	%>
</body>
</html>