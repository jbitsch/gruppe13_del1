<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="login" class="model.Login" type="model.Login" scope="session"/>
<jsp:useBean id="brugerAdmin" class="model.BrugerAdministration" type="model.BrugerAdministration" scope="session"/>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style2.css" %>
</style>

<title>Bruger formular</title>
</head>
<body>

	<%!
	
	String[] roller = new String[]{"Administrator","Farmaceut","Værkfører","Operatør"};
	%>

	<div class="header">
		<div class="headerContent">
			<jsp:include page="menubar.jsp" />
		</div>
	</div>
	
	<div class="content">
		<h1>Bruger formular</h1>
		
		<p><font color="red"><%=brugerAdmin.getError() %></font><font color="green"><%= brugerAdmin.getSucces() %></font><br>
		
		<form method="POST" action="">
		<table border="0">	
			<tr>
				<td> Operatør navn: </td>
				<td><input name="oprName" type="text" size = 20 value = "<%=brugerAdmin.getName() %>"></td>
			</tr>
			<tr>
				<td>Initialer:</td>
				<td><input type="text" name="ini" value = "<%= brugerAdmin.getIni() %>"></td>
			</tr>
			<tr>
				<td>CPR:</td>
				<td><input type="text" name="cpr" value = "<%= brugerAdmin.getCpr() %>"></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="newPw" value = "<%= brugerAdmin.getPassword() %>"></td>
		<%
			if(brugerAdmin.getId()==0 || !("Operatør").equals(brugerAdmin.getRolle()))
			{
				%>
						<tr>
							<td>Rolle:</td>
							 <td><select name="rolle">
							<option value="<%=brugerAdmin.getRolle()%>" selected="selected"><%=brugerAdmin.getRolle()%></option>
							<%
							for(int i = 0; i < roller.length; i++)
							{
								if(!roller[i].equals(brugerAdmin.getRolle()))
								{
									%>
										<option value="<%=roller[i]%>"><%=roller[i] %></option>
									<%
								
								}
							}
							%>
							</select>
							</td>
						</tr>
						<%
			}else
			{
				%>
				<tr>
					<td>Rolle:</td>
					<td> <%=brugerAdmin.getRolle() %></td>
				</tr>
				<%
			}
		%>
		</table>
		<%
			if(brugerAdmin.getId()==0)
			{
			%>
					<div class="bottomSubmit">	
						<input type="submit" name="handling" value="Opret bruger">
					</div>
			<%
			}
			else
			{
				%>
					<div class="bottomSubmit">
						<input type = "submit" name="Tilbage" value="Tilbage"><input type="submit" name="handling" value="Ændre">
						<input type="hidden" name="menuValg" value="Administrer bruger" />	
						<%
						if(!(brugerAdmin.getId()==login.getId()) && !("Operatør".equals(brugerAdmin.getRolle())))
						{
							%>
								<input type = "submit" name="handling" value="Slet bruger">
							<%
						}
						%>
					</div>
				<%
			}
		%>
		</form>
	</div>
		
</body>
</html>
