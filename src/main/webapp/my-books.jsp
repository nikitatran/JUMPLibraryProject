<%@ include file= "header.jsp" %>

<div class="container">
	<h1 class="display-3 text-center">My Books</h1>
	<br>
	<br>
	
					<c:out value="${ patron.firstName }" />
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
				<td>
					<c:out value="${ book.isbn }" />
				</td>
				<td>
					<c:out value="${ book.checkedOutDate }" />
				</td>
				<td>
					<c:out value="${ book.dueDate }" />
				</td>
				<td>
					<c:out value="${ book.title }" />
				</td>
				<td>
					<c:out value="${ book.description }" />
				</td>
				<td>
					<a href="<%=request.getContextPath() %>/myaccount?id=<c:out value='${ book.isbn }' />">
						<button class="btn btn-primary">Return</button>
					</a>
				</td>
					<%-- <c:out value="${ patron.firstName }" /> --%>
			</tr>
			</c:forEach>
		
		</tbody>
	
	</table>

</div>

<%@ include file= "footer.jsp" %>