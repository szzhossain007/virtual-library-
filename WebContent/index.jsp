<!-- http://localhost:8080/LibraryApp/index.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
<title>
    Library Home Page
</title>
	<link rel="stylesheet" type="text/css" href="resources/nav.css" />
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
<h1>Welcome to NPU Library</h1>
<p>${logoutmessage}</p>
<img src="resources/library.jpg" width="400" height="400">
<br>
<br>
<br>
</center>
</div>
</body>
</html>