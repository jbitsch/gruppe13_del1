<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:useBean id="login" class="model.Login" type="model.Login" scope="session"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<style type="text/css">
	<%@include file="../../static/style.css" %>
</style>

</head>
<body>
<%
		String rolle = login.getUser().getRolle();
		String name = login.getUser().getOprNavn();
%>
<br>
<form method="POST">
Du er logget ind som <%=name%> <input type="submit" class="submitLink" value="Log ud">
<input type="hidden" name="handling" value="log_ud">	
</form>
<ul id="menu">
    <li>
        <form name="start" method="POST">
    		<input type="hidden" name="menuValg" value="Menu">
    		<A HREF="javascript:document.start.submit()">Home</A>
		</form> 
    </li>
    <li>
        <form name="pwForm" method="POST">
    		<input type="hidden" name="menuValg" value="Skift Adgangskode">
    		<A HREF="javascript:document.pwForm.submit()">Skift adgangskode</A>
		</form> 
    </li>
    <li>    	
    	<form name="" method="POST">
    		<A HREF="">Råvarebatch</A>
		</form> 
        <ul>
            <li>
            	<form name="createRB" method="POST">
    				<input type="hidden" name="menuValg" value="Opret raavarebatch">
    				<A HREF="javascript:document.createRB.submit()">Opret råvarebatch</A>
				</form> 
            </li>
            <li>
            	<form name="showRB" method="POST">
    				<input type="hidden" name="menuValg" value="Vis raavarebatch">
    				<A HREF="javascript:document.showRB.submit()">Vis råvarebatch</A>
				</form>  
            </li>
        </ul>
    </li>
    <li>    	
    	<form name="" method="POST">
    		<A HREF="">Produktbacth</A>
		</form> 
        <ul>
            <li>
            	<form name="createPB" method="POST">
    				<input type="hidden" name="menuValg" value="Opret produktbatch">
    				<A HREF="javascript:document.createPB.submit()">Opret produktbatch</A>
				</form> 
            </li>
            <li>
            	<form name="showPB" method="POST">
    				<input type="hidden" name="menuValg" value="Vis produktbatch">
    				<A HREF="javascript:document.showPB.submit()">Vis produktbatch</A>
				</form>  
            </li>
        </ul>
    </li>
    <%
	if("Farmaceut".equals(rolle) || "Administrator".equals(rolle) )
	{
	%>
    <li>    	
    	<form name="" method="POST">
    		<A HREF="">Recept</A>
		</form> 
        <ul>
            <li>
            	<form name="createRecept" method="POST">
    				<input type="hidden" name="menuValg" value="Opret recpet">
    				<A HREF="javascript:document.createRecept.submit()">Opret recpet</A>
				</form> 
            </li>
            <li>
            	<form name="showRecept" method="POST">
    				<input type="hidden" name="menuValg" value="Vis recept">
    				<A HREF="javascript:document.showRecept.submit()">Vis recept</A>
				</form>  
            </li>
        </ul>
    </li>
    <li>    	
    	<form name="" method="POST">
    		<A HREF="">Råvare</A>
		</form> 
        <ul>
            <li>
            	<form name="createRaavare" method="POST">
    				<input type="hidden" name="menuValg" value="Opret raavare">
    				<A HREF="javascript:document.createRaavare.submit()">Opret råvare</A>
				</form> 
            </li>
            <li>
            	<form name="showRaavare" method="POST">
    				<input type="hidden" name="menuValg" value="Administrer raavare">
    				<A HREF="javascript:document.showRaavare.submit()">Administrer råvare</A>
				</form>  
            </li>
        </ul>
    </li>
    <%
	}
	if("Administrator".equals(rolle) )
	{
	%>
    <li>    	
    	<form name="" method="POST">
    		<A HREF="">Bruger administration</A>
		</form> 
        <ul>
            <li>
            	<form name="createUser" method="POST">
    				<input type="hidden" name="menuValg" value="Opret bruger">
    				<A HREF="javascript:document.createUser.submit()">Opret bruger</A>
				</form> 
            </li>
            <li>
            	<form name="showUser" method="POST">
    				<input type="hidden" name="menuValg" value="Administrer operatoerer">
    				<A HREF="javascript:document.showUser.submit()">Administrer operatører</A>
				</form>  
            </li>
        </ul>
    </li>
    <%
	}
	%>
</ul>

</body>
</html>