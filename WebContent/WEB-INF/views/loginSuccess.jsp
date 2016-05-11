<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" %>
<html>
<head>
<title>Library</title>
<link rel="stylesheet" type="text/css" href="../resources/nav.css" />
</head>
<body>
<div id="titlebar">
   <h1>NPU Library</h1>
</div>
<div id="wrap">
<ul id="nav">
	<li><a href="">Home</a>
	<li><a href="/LibraryApp/addbookform">Add Book</a>
	<li><a href="booklistjson.html">View Books</a>
	<li><a href="/LibraryApp/viewissuedbooks">View Issued Books</a>
	<li><a href="/LibraryApp/updateprofile">Update Profile</a>
	<li><a href="/LibraryApp/processlogout">Logout</a>
	<li><a href="" class="disabled">Welcome ${sessionScope.librarian.firstName} ${sessionScope.librarian.lastName}</a>
  </ul>
</div>
<center>
<h1>
	Welcome  <font color="blue"/>${sessionScope.librarian.firstName} ${sessionScope.librarian.lastName}</font>
</h1>
<br>
<img src="../resources/library1.jpg" width="600" height="400">
</center>
</body>
</html>
<c:remove var="name" scope="session" />