<%@ include file="header.jsp"%>
<html>
<body>
	<div class="container">

		<h1 class="display-3">Library Login</h1>

		<form action="index" method="post">
			<div class="form-group">
				<label for="username">Username</label> <input type="text"
					class="form-control" id="username" placeholder="Enter username" />
			</div>

			<div class="form-group">
				<label for="password">Password</label> <input type="password"
					class="form-control" id="password" placeholder="Password">
			</div>

			<button type="button" class="btn btn-secondary">Create
				account</button>
			<button type="submit" class="btn btn-primary">Submit</button>

		</form>

	</div>

	<%@ include file="footer.jsp"%>