<%@ include file="header.jsp"%>

<div class="container">

	<h1 class="display-3 text-center p-5">Library Login</h1>

	<!-- 
			TODO: make sure form action and method are correct
		 -->
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