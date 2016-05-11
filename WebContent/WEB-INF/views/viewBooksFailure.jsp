<%@page contentType="text/html" %>
<html>
<head>
<title>Add Book Failure</title>
<link rel="stylesheet" type="text/css" href="./resources/nav.css" />
</head>
<body>
<div id="titlebar">
   <h1>NPU Library</h1>
</div>
<div id="wrap">
<ul id="nav">
	<li><a href="/LibraryApp/home">Home</a>
	<li><a href="/LibraryApp/addbookform">Add Book</a>
	<li><a href="booklistjson.html">View Books</a>
	<li><a href="/LibraryApp/viewissuedbooks">View Issued Books</a>
	<li><a href="/LibraryApp/updateprofile">Update Profile</a>
	<li><a href="/LibraryApp/processlogout">Logout</a>
	<li><a href="" class="disabled">Welcome ${sessionScope.librarian.firstName} ${sessionScope.librarian.lastName}</a>
  </ul>
</div>
<center>
<h2>An Error Occurred while viewing books</h2>
<h3><font COLOR="#00008B">
&nbsp;&nbsp;&nbsp;&nbsp;
${sessionScope.librarian.firstName} ${sessionScope.librarian.lastName}
</font>
</h3>
<h2>
Failure Reason: ${ViewBooksFailureMsg}
<br>
Please try again or report the failure
</h2>
</center>
</body>
</html>