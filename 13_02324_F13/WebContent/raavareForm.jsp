<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="raavareAdmin" class="controller.RaavareAdministration" type="controller.RaavareAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Råvare form</title>
</head>
<body>

<p><font color="red"><%=raavareAdmin.getError() %></font><font color="green"><%= raavareAdmin.getSucces() %></font><br>

<form method="POST">
	Råvare id: <input name="raavareId" type="text" size = 8 value = "<%= raavareAdmin.getRaavareId() %>"><br>
	Råvare navn: <input name="raavareNavn" type="text" size = 20 value = "<%= raavareAdmin.getRaavareNavn() %>"><br>
	Leverandør: <input name="leverandoer" type="text" size = 20 value = "<%= raavareAdmin.getRaavareNavn() %>"><br>
	<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" name="handling" value="Opret Raavare">
</form>



</body>
</html>