<html>
<head>
<title>Login Details</title>
<link rel="stylesheet" type="text/css" href="./resources/nav.css" />
</head>
<body> 
<div id="titlebar">
   <h1>NPU Library</h1>
</div>
<div id="wrap">
  <ul id="nav">
	<li><a href="/LibraryApp/loginform">Login</a> 
	<li><a href="/LibraryApp/registrationform">Register</a> 
  </ul>
</div>
<center>
<h1>Please enter the details below: </h1>
<form name="loginForm" action="${pageContext.request.contextPath}/processloginform" method="POST">
Email ID: <input type="TEXT" name="EmailID"> <font size="3" color="red">${errorInEmailIdMsg}</font><BR>
Password: <input type="PASSWORD" name="Password"> <font size="3" color="red">${errorInPasswordMsg}</font><BR>
<font size="3" color="red">${incorrectlogin}</font><BR>
<input type="submit" name="Register" value="Login">
</form>
</center>
</body>
</html>