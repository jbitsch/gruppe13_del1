<html>
<head><title>Loginside</title></head>
<body>
<h1>Log ind</h1>

<form method=post action="j_security_check" >
  Brugernavn:   <input type="text" name="j_username" size="10">     <br>
  Adgangskode:  <input type="password" name= "j_password" size="10"><br>
  <input type="submit" value="Log ind">
</form>

<br>request.getUserPrincipal()= <%= request.getUserPrincipal() %>
</body>
</html>