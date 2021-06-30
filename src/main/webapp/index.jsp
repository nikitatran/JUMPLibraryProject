<%@ include file="header.jsp"%>

<div class="container">

	<h1 class="display-3 text-center">Library Login</h1>

	<!-- 
			TODO: make sure form action and method are correct
		 -->
	<form action="login" method="post">
		<div class="form-group">
			<label for="username">Username</label> <input type="text"
				class="form-control" id="username" placeholder="Enter username" />
		</div>

		<div class="form-group">
			<label for="password">Password</label> <input type="password"
				class="form-control" id="password" placeholder="Password">
		</div>

		<a href="<%=request.getContextPath()%>/createaccount">
			<button type="button" class="btn btn-secondary">Create
				account</button>
		</a>

		<button type="submit" class="btn btn-primary">Submit</button>


	</form>

</div>

<%@ include file="footer.jsp"%>