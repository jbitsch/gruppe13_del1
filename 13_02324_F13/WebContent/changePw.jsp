<%@ include file = "loginCheck.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:useBean id="function" class="funktionalitet.Funktionalitet2"
	type="funktionalitet.IFunktionalitet2" scope="application" />
<jsp:useBean id="user" class="data.OperatoerDTO2"
	type="data.OperatoerDTO2" scope="session" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change user <%=user.getOprId()%> password
</title>
</head>
<body>

	<!-- Formen der bruges til input af nyt og gammelt password -->
	<form name="input" action="changePw.jsp" method="post">
		<p>
			Gammelt password: <input type="password" name="old" />
		</p>
		<p>
			Nyt password: <input type="password" name="new" />
		</p>
		<p>
			Gentag nyt password: <input type="password" name="newRep" />
		</p>
		<input type="submit" value="Submit">
	</form>

	<p>
		<%
			// Henter HTML form tjekker for fjel i nyt og gammelt password
			// Hvis alt stemmer overens ændres password og bruger sendes tilbage til menu
			// Hvis der er fejl vises de til brugeren på denne side og de kan forsøge igen
			if (request.getMethod().equals("POST")) {
				String newPassword = request.getParameter("new");
				String newPasswordRep = request.getParameter("newRep");
				String error = null;
				if (!user.getPassword().equals(request.getParameter("old"))) {
					error = "Old password is incorrect";
				} else if (!newPassword.equals(newPasswordRep) ) {
					error = "Didn't repeat the new password correctly";
				} else if (!function.checkPassword(newPasswordRep)) {
					error = "New password doesn't follow the rules";
				} else {
					user.setPassword(newPassword);
					response.sendRedirect("menu.jsp");
				}

				// Udskriver fejlmeddelelse
				out.println("<span style=\"color:#FF0000\">" + error
						+ "</span>" + "<br>");
			}
		%>

		<a href="menu.jsp">Tilbage til menu</a>
	</p>
</body>
</html>