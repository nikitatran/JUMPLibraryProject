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
				<th class="dateColumn">Checked Out Date</th>
				<th class="dateColumn">Due Date</th>
				<th>Title</th>
				<th>Description</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="book" items="${currentBooks}">
				<tr>
					<td><c:out value="${ book.isbn }" /></td>
					<td class="dateColumn"><c:out value="${ book.checkedOutDate }" /></td>
					<td class="dateColumn"><c:out value="${ book.dueDate }" /></td>
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
	
	<c:if test="${ currentBooks == null || currentBooks.size() == 0 }">
		<p class="display-6 tableMessage">
			You don't have any books checked out at this time.
		</p>
	</c:if>
	
	<br />
	<br />
	<br />

	<h2 class="display-6">Previously Checked Out</h2>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>ISBN</th>
				<th class="dateColumn">Checked Out Date</th>
				<th class="dateColumn">Due Date</th>
				<th class="dateColumn">Returned Date</th>
				<th>Title</th>
				<th>Description</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach var="book" items="${previousBooks}">
				<tr>
					<td><c:out value="${ book.isbn }" /></td>
					<td class="dateColumn"><c:out value="${ book.checkedOutDate }" /></td>
					<td class="dateColumn"><c:out value="${ book.dueDate }" /></td>
					<td class="dateColumn"><c:out value="${ book.returnedDate }" /></td>
					<td><c:out value="${ book.title }" /></td>
					<td><c:out value="${ book.description }" /></td>
					
				</tr>
			</c:forEach>

		</tbody>
	</table>
	
	<c:if test="${ previousBooks == null || previousBooks.size() == 0 }">
		<p class="display-6 tableMessage">
			You haven't checked out any books yet.
		</p>
	</c:if>

</div>

<%@ include file="footer.jsp"%>