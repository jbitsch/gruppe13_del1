<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<jsp:useBean id="login" class="model.Login" type="model.Login" scope="session"/>


<%
		String rolle = login.getUser().getRolle();
		String name = login.getUser().getOprNavn();
%>
<br>
<form method="POST" action="">
Du er logget ind som <%=name%> <input type="submit" class="submitLink" value="Log ud">
<input type="hidden" name="handling" value="log_ud">	
</form>

<div class=menuLine>
	<ul id="menu">
	    <li>
	        <form name="start" method="POST" action="">
	    		<input type="hidden" name="menuValg" value="Menu">
	    		<input type="hidden" name="menuBar" value="menuBar">
	    		<A HREF="javascript:document.start.submit()">Home</A>
			</form> 
	    </li>
	    <li>
	        <form name="pwForm" method="POST" action="">
	    		<input type="hidden" name="menuValg" value="Skift Adgangskode">
	    		<input type="hidden" name="menuBar" value="menuBar">
	    		<A HREF="javascript:document.pwForm.submit()">Skift adgangskode</A>
			</form> 
	    </li>
	    <li>    	
	    	<form name="" method="POST" action="">
	    		<A HREF="">Råvarebatch</A>
			</form> 
	        <ul>
	            <li>
	            	<form name="createRB" method="POST" action="">
	    				<input type="hidden" name="menuValg" value="Opret raavarebatch">
	    				<input type="hidden" name="menuBar" value="menuBar">
	    				<A HREF="javascript:document.createRB.submit()">Opret råvarebatch</A>
					</form> 
	            </li>
	            <li>
	            	<form name="showRB" method="POST" action="">
	    				<input type="hidden" name="menuValg" value="Vis raavarebatch">
	    				<input type="hidden" name="menuBar" value="menuBar">
	    				<A HREF="javascript:document.showRB.submit()">Vis råvarebatch</A>
					</form>  
	            </li>
	        </ul>
	    </li>
	    <li>    	
	    	<form name="" method="POST" action="">
	    		<A HREF="">Produktbatch</A>
			</form> 
	        <ul>
	            <li>
	            	<form name="createPB" method="POST" action="">
	    				<input type="hidden" name="menuValg" value="Opret produktbatch">
	    				<input type="hidden" name="menuBar" value="menuBar">
	    				<A HREF="javascript:document.createPB.submit()">Opret produktbatch</A>
					</form> 
	            </li>
	            <li>
	            	<form name="showPB" method="POST" action="">
	    				<input type="hidden" name="menuValg" value="Vis produktbatch">
	    				<input type="hidden" name="menuBar" value="menuBar">
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
	    	<form name="" method="POST" action="">
	    		<A HREF="">Recept</A>
			</form> 
	        <ul>
	            <li>
	            	<form name="createRecept" method="POST" action="">
	    				<input type="hidden" name="menuValg" value="Opret recept">
	    				<input type="hidden" name="menuBar" value="menuBar">
	    				<A HREF="javascript:document.createRecept.submit()">Opret recpet</A>
					</form> 
	            </li>
	            <li>
	            	<form name="showRecept" method="POST" action="">
	    				<input type="hidden" name="menuValg" value="Vis recept">
	    				<input type="hidden" name="menuBar" value="menuBar">
	    				<A HREF="javascript:document.showRecept.submit()">Vis recept</A>
					</form>  
	            </li>
	        </ul>
	    </li>
	    <li>    	
	    	<form name="" method="POST" action="">
	    		<A HREF="">Råvare</A>
			</form> 
	        <ul>
	            <li>
	            	<form name="createRaavare" method="POST" action="">
	    				<input type="hidden" name="menuValg" value="Opret raavare">
	    				<input type="hidden" name="menuBar" value="menuBar">
	    				<A HREF="javascript:document.createRaavare.submit()">Opret råvare</A>
					</form> 
	            </li>
	            <li>
	            	<form name="showRaavare" method="POST" action="">
	    				<input type="hidden" name="menuValg" value="Administrer raavare">
	    				<input type="hidden" name="menuBar" value="menuBar">
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
	    	<form name="" method="POST" action="">
	    		<A HREF="">Bruger administration</A>
			</form> 
	        <ul>
	            <li>
	            	<form name="createUser" method="POST" action="">
	    				<input type="hidden" name="menuValg" value="Opret bruger">
	    				<input type="hidden" name="menuBar" value="menuBar">
	    				<A HREF="javascript:document.createUser.submit()">Opret bruger</A>
					</form> 
	            </li>
	            <li>
	            	<form name="showUser" method="POST" action="">
	    				<input type="hidden" name="menuValg" value="Administrer bruger">
	    				<input type="hidden" name="menuBar" value="menuBar">
	    				<A HREF="javascript:document.showUser.submit()">Administrer bruger</A>
					</form>  
	            </li>
	        </ul>
	    </li>
	    <%
		}
		%>
	</ul>

</div>