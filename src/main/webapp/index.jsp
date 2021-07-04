<%@ include file="header.jsp"%>

<% boolean fail = request.getAttribute("fail") != null; %>

<div class="container">

	<h1 class="display-3 text-center p-5">Library Login</h1>

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
				name="username" class="form-control" id="username"
				placeholder="Enter username" />
		</div>

		<div class="form-group">
			<label for="password">Password</label> <input type="password"
				name="password" class="form-control" id="password"
				placeholder="Password">
		</div>

		<div class="row justify-content-between">
			<div class="col-6 text-left">
				<a href="<%=request.getContextPath()%>/createaccount">
					<span class="text-secondary">Create a new account</span>
				</a>
			</div>

			<div class="col-6 text-right">
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>

		</div>

	</form>


</div>

<%@ include file="footer.jsp"%>