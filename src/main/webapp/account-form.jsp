<%@ include file="header.jsp"%>

<div class="container">
	<h1 class="display-3 text-center">Create Account</h1>
	<form action= "dashboard" method="post">
		<div class="form-group">
			<label for="firstname">First Name</label> 
			<input
				type="text" class="form-control" id="firstname"
				name="firstname" placeholder="Enter your first name">
		</div>
		
		<div class="form-group">
			<label for="lastname">Last Name</label> 
			<input
				type="text" class="form-control" id="lastname"
				name="lastname" placeholder="Enter your last name">
		</div>
		
		<div class="form-group">
			<label for="username">New username</label> 
			<input
				type="text" class="form-control" id="username"
				name="username" placeholder="New username">
		</div>
		
		<div class="form-group">
			<label for="password">New password</label> 
			<input
				type="password" class="form-control" id="password"
				name="password" placeholder="New password">
		</div>
		
		<button type="submit" class="btn btn-primary">Submit</button>
	</form>

</div>

<%@ include file="footer.jsp"%>