<%@page contentType="text/html" %>
<html>
<head>
<title>Login Failure</title>
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
<center>
<h2>An Error Occurred while login</h2>
<h3><font COLOR="#00008B">
&nbsp;&nbsp;&nbsp;&nbsp;
</font>
</h3>
<h2>
Failure Reason: ${LoginFailureMsg}
<br>
Please try again or report the failure
</h2>
</center>
</div>
</body>
</html>