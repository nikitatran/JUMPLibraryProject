<%@ include file= "header.jsp" %>

<div class="container">
	<h1 class="display-3 text-center">My Books</h1>
	<br>
	<br>
	
	<table class="table table-striped">
		
		<thead>
			<tr>
				<th>ISBN</th>
				<th>Title</th>
				<th>Description</th>
				<th>Currently rented?</th>
				<th>Date added</th>
				<th>Checkout</th>
				<th>Due Date</th>
			</tr>
		</thead>
		
		<tbody>
			
			<c:forEach var="book" items="${allBooks}">
			<tr>
				<td>
					<c:out value="${ book.isbn }" />
				</td>
				<td>
					<c:out value="${ book.title }" />
				</td>
				<td>
					<c:out value="${ book.descr }" />
				</td>
				<td>
					<c:out value="${ book.rented }" />
				</td>
				<td>
					<c:out value="${ book.added_to_library }" />
				</td>
				<td>
					<c:if test="${ book.rented == 1 }">
					<a href="<%=request.getContextPath() %>/myaccount?id=<c:out value='${ book.isbn }' />">
						<button class="btn btn-primary">Checkout</button>
					</a>
					</c:if>
					
				</td>
					<c:out value="${  }"
			</tr>
			</c:forEach>
		
		</tbody>
	
	</table>

</div>

<%@ include file= "footer.jsp" %>