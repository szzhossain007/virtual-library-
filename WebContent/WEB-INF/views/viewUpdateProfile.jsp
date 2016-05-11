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
<h1>Edit the details below: </h1>
<form name="registerForm" action="${pageContext.request.contextPath}/servlet/processupdateprofileform" method="POST">
<input type="TEXT" name="librarianId" value="${librarian.librarianId}" hidden>
First Name: <input type="TEXT" name="FirstName" value="${librarian.firstName}">  <font size="3" color="red">${errorInFirstNameMsg}</font><BR>
Last Name: <input type="TEXT" name="LastName" value="${librarian.lastName}"> <font size="3" color="red">${errorInLastNameMsg}</font><BR>
Email ID: <input type="TEXT" name="EmailID" value="${librarian.emailId}"> <font size="3" color="red">${errorInEmailIdMsg}</font><BR>
Password: <input type="PASSWORD" name="Password"> <font size="3" color="red">${errorInPasswordMsg}</font><BR>
    <input type="submit" name="Update" value="Update">
</form>
</center>
</body>
</html>