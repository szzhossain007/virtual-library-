
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" %>
<html>
<head>
<title>Registration Result</title>
<link rel="stylesheet" type="text/css" href="../resources/nav.css" />
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
<h2>Your information has been successfully saved:</h2>
<h3><font COLOR="#00008B">
&nbsp;&nbsp;&nbsp;&nbsp;
${sessionScope.librarian.firstName} ${sessionScope.librarian.lastName}
</font>
</h3>
<a href="/LibraryApp/loginform">Login</a>
</center>
</div>
</body>
</html>
<c:remove var="name" scope="session" />