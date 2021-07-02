<%@ include file="header.jsp" %>

<div class="container">
	<h1 class="display-2 text-center">
		404
	</h1>
	<h2 class="display-4 text-center" style="margin: 10px 0 30px;">
		The page you are looking for could not be found.
	</h2>
	<h2 class="display-6 text-center">
		Please check that the url is typed correctly or 
		<a href="<%=request.getContextPath() %>/">go to the login page</a>.
	</h2>
</div>

<%@ include file="footer.jsp" %>
