<html>
<head>
<title>Book Details</title>
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
	<li><a href="viewbooks.html">View Books</a>
	<li><a href="/LibraryApp/viewissuedbooks">View Issued Books</a>
	<li><a href="/LibraryApp/updateprofile">Update Profile</a>
	<li><a href="/LibraryApp/processlogout">Logout</a>
	<li><a href="" class="disabled">Welcome ${sessionScope.librarian.firstName} ${sessionScope.librarian.lastName}</a>
  </ul>
</div>
<center>
<h1>Please enter the details below: </h1>
<form name="addBookForm" action="${pageContext.request.contextPath}/servlet/processaddbookform" method="POST">
Title: <input type="TEXT" name="Title">  <font size="3" color="red">${errorInTitleMsg}</font><BR>
Publisher: <input type="TEXT" name="Publisher"> <font size="3" color="red">${errorInPublisherMsg}</font><BR>
Author: <input type="TEXT" name="Author"> <font size="3" color="red">${errorInEmailIdMsg}</font><BR>
Price: <input type="TEXT" name="Price"> <font size="3" color="red">${errorInPriceMsg}</font><BR>
Number of Books: <input type="TEXT" name="NumOfBooks"> <font size="3" color="red">${errorInNumOfBooksMsg}</font><BR>
    <input type="submit" name="Add" value="Submit">
</form>
</center>
</body>
</html>