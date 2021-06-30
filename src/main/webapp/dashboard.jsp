<%@ include file="header.jsp"%>

<div class="container">

	<h1 class="display-3 text-center">User Dashboard</h1>
	<a href="<%=request.getContextPath()%>/catalogue">
		<button type="button" class="mt-3 btn btn-primary btn-lg btn-block">All books</button>
	</a> 

	<a href="<%=request.getContextPath()%>/myaccount">
		<button type="button" class="mt-2 btn btn-dark btn-lg btn-block">My books</button>
	</a>


</div>

<%@ include file="footer.jsp"%>