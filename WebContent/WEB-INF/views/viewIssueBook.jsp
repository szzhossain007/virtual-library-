<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" %>
<html>
<head>
<title>Library</title>
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
<div>
<label>Title: ${issueBook.title}</label><br>
<label>Publisher: ${issueBook.publisher}</label><br>
<label>Author: ${issueBook.author}</label><br>
<label>Price: ${issueBook.price}</label><br>
<label>Number of Books: ${issueBook.numOfBooks}</label>
<br><br>
<form name="issueBookForm" action="${pageContext.request.contextPath}/servlet/processissuebookform" method="POST">
<input type="TEXT" name="bookId" value="${issueBook.bookId}" hidden>
<BR>
<select name="issueStudent">
<c:forEach items="${studentList}" var="curStudent">
	<c:set var="name" value="${curStudent.firstName} ${curStudent.lastName}" />
	<option value="${curStudent.studentId}">${name}</option>
</c:forEach>
</select>
<input type="submit" name="Issue" value="Issue">
</form>
</div>
</center>
</body>
</html>
<c:remove var="name" scope="session" />