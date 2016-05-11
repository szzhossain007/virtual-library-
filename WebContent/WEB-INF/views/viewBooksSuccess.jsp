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
<c:choose>
    <c:when test='${books == null || empty books}'>
         No books available
    </c:when>
    <c:otherwise>
<div id="tableview">
    <table>
    <div id="columnview">
		<tr>
  			<th>Title</th>
  			<th>Publisher</th> 
  			<th>Author</th>
  			<th>Price</th>
  			<th>Number of Books</th>
		</tr>
		<c:forEach items="${books}" var="curBook">
		<form name="issuebk" action="issuebook.html" method="POST">
		<tr>
  			<td>${curBook.title}</td>
  			<td>${curBook.publisher}</td>
  			<td>${curBook.author}</td>
  			<td>${curBook.price}</td>
  			<td>${curBook.numOfBooks}</td>
  			<input type="text" name="issuebookid" value="${curBook.bookId}" hidden>
  			<td><input type="submit" name="bookaction" value="IssueBook"></td>
  			<td><input type="submit" name="bookaction" value="Edit"></td>
  			<td><input type="submit" name="bookaction" value="Delete"></td>
  			<!-- <td><a href='issuebook.html?bookId=${curBook.bookId}&action=issueBook'>IssueBook</a></td> -->
		</tr>
		</form>
		</c:forEach>
	</table>
    </c:otherwise>
    </c:choose>
</div>
</center>
</body>
</html>
<c:remove var="name" scope="session" />