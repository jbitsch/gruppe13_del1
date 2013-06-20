<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="brugerAdmin" class="model.BrugerAdministration" type="model.BrugerAdministration" scope="session"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bruger slettet</title>
</head>
<body>
<jsp:include page="menubar.jsp" /><br>
<h1>Bruger er slettet</h1>

<font color="green"><%= brugerAdmin.getSucces() %></font><br>
<form method="post" action="">
				<input type = "submit" name="menuValg" value="Tilbage"><input type="submit" name="handling" value="Ændre">
				<input type="hidden" name="backpage" value="/WEB-INF/CDIO/chooseUser.jsp" />
</form>

</body>
</html>