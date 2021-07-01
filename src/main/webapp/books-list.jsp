<%@ include file= "header.jsp" %>

<div class="container">
	<h1 class="display-3 text-center">Books List</h1>
	<br>
	<br>
	
	<table class="table table-striped">
		
		<thead>
			<tr>
				<th>ISBN</th>
				<th>Title</th>
				<th>Description</th>
				<th>Date added</th>
				<th>Checkout</th>
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
					<c:out value="${ book.description }" />
				</td>
				<td>
					<c:out value="${ book.addLib }" />
				</td>
				<td>
					<c:if test="${ !book.rented }">
					<a href="<%=request.getContextPath() %>/checkout?isbn=<c:out value='${ book.isbn }' />">
						<button class="btn btn-primary">Checkout</button>
					</a>
					</c:if>
					<c:if test="${ book.rented }">
						Currently being rented
					</a>
					</c:if>
				</td>
		
			</tr>
			</c:forEach>
		
		</tbody>
	
	</table>

</div>

<%@ include file= "footer.jsp" %>