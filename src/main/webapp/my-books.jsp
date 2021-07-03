<%@ include file="header.jsp"%>

<div class="container">
	<h1 class="display-3 text-center p-4">
		<c:out value="${ patron.firstName }" />'s Books
	</h1>
	
	<h2 class="display-6">Currently Checked Out</h2>
	
	<table class="table table-striped">
		<thead>
			<tr>
				<th>ISBN</th>
				<th>Checked Out Date</th>
				<th>Due Date</th>
				<th>Title</th>
				<th>Description</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="book" items="${currentBooks}">
				<tr>
					<td><c:out value="${ book.isbn }" /></td>
					<td><c:out value="${ book.checkedOutDate }" /></td>
					<td><c:out value="${ book.dueDate }" /></td>
					<td><c:out value="${ book.title }" /></td>
					<td><c:out value="${ book.description }" /></td>
					<td><a
						href="<%=request.getContextPath() %>/return?isbn=<c:out value='${ book.isbn }' />">
							<button class="btn btn-primary">Return</button>
					</a></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
	
	<br />
	<br />
	<br />

	<h2 class="display-6">Previously Checked Out</h2>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>ISBN</th>
				<th>Checked Out Date</th>
				<th>Due Date</th>
				<th>Returned Date</th>
				<th>Title</th>
				<th>Description</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="book" items="${previousBooks}">
				<tr>
					<td><c:out value="${ book.isbn }" /></td>
					<td><c:out value="${ book.checkedOutDate }" /></td>
					<td><c:out value="${ book.dueDate }" /></td>
					<td><c:out value="${ book.returnedDate }" /></td>
					<td><c:out value="${ book.title }" /></td>
					<td><c:out value="${ book.description }" /></td>
					
				</tr>
			</c:forEach>

		</tbody>
	</table>




</div>

<%@ include file="footer.jsp"%>