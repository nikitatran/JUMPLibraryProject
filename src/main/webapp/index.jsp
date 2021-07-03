<%@ include file="header.jsp"%>

<% boolean fail = request.getAttribute("fail") != null; %>

<div class="container">

	<h1 class="display-3 text-center">Library Login</h1>

	<% if (fail) { %>
		<div class="alert alert-danger text-center" role="alert">
			<h3 class="display-5 text-center">Login Failed</h3>
			<p style="margin: 0.3rem 0;">No user could be found for that username and password combination.</p>
			<p style="margin: 0;">Please try again.</p>
		</div>
	<% } %>
	
	<form action="login" method="post">
		<div class="form-group">
			<label for="username">Username</label> <input type="text"
				name="username" class="form-control" id="username" placeholder="Enter username" />
		</div>

		<div class="form-group">
			<label for="password">Password</label> <input type="password"
				name="password" class="form-control" id="password" placeholder="Password">
		</div>

		<a href="<%=request.getContextPath()%>/createaccount">
			<button type="button" class="btn btn-secondary">Create
				account</button>
		</a>

		<button type="submit" class="btn btn-primary">Submit</button>

	</form>


</div>

<%@ include file="footer.jsp"%>