<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Slet recept</title>
</head>
<body>
	<%
	File receptFile = new File(application.getRealPath("/recepts.txt"));
	try
	{
		receptFile.createNewFile();
		
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
		
		if(request.getMethod().equals("POST"))
		{
			String writeToFile = "";
			String num = request.getParameter("number");
			int toRemove = -1;
			
			for(int i = 0; i < recepts.size(); i++)
			{
					String receptNr = recepts.get(i)[0];
					String temp = recepts.get(i)[0]+";"+recepts.get(i)[1]+";"+recepts.get(i)[2]+";"+recepts.get(i)[3]+";"+recepts.get(i)[4]+";"+recepts.get(i)[5]+"\n";
					
					if(receptNr.equals(num))
					{
						toRemove = i;
					}
					else
					{
						writeToFile = writeToFile+temp; 
					}
						
			}
			if(toRemove!=-1)
			{
				recepts.remove(toRemove);
				response.sendRedirect("inputRecept.jsp");
			}
			FileWriter fileWriter = new FileWriter(receptFile, false);
			BufferedWriter outStream = new BufferedWriter(fileWriter);
			outStream.write(writeToFile);
			outStream.close();
			fileWriter.close();
			
		}
		
		
		// Vis tabel over recepter, hvis der er nogle
		if(recepts.size() != 0)
		{
			String[] categories = {"Receptnummer", "Produktnummer", "Produktnavn", "Desired weight", "Tolerance","Dato"};	
			
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
			
			%>
				<form method="POST" action="delete.jsp">
					Recept nr. der skal slettes: <input type = "text" name = "number" value =""><br />
					<input type = "submit" name = "submit" value = "Slet">
				</form>
			<%
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