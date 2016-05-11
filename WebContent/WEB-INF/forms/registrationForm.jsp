<html>
<head>
<title>Registration Details</title>
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
<form name="registerForm" action="${pageContext.request.contextPath}/servlet/processregistrationform" method="POST">
First Name: <input type="TEXT" name="FirstName">  <font size="3" color="red">${errorInFirstNameMsg}</font><BR>
Last Name: <input type="TEXT" name="LastName"> <font size="3" color="red">${errorInLastNameMsg}</font><BR>
Email ID: <input type="TEXT" name="EmailID"> <font size="3" color="red">${errorInEmailIdMsg}</font><BR>
Password: <input type="PASSWORD" name="Password"> <font size="3" color="red">${errorInPasswordMsg}</font><BR>
    <input type="submit" name="Register" value="Submit">
</form>
</center>
</body>
</html>