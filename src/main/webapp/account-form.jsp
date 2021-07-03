<%@page import="com.cognixia.jump.models.PatronsModel"%>
<%@ include file="header.jsp"%>

<%
	PatronsModel patron = (PatronsModel) request.getAttribute("patron");
	String heading, formAction, firstName = "", lastName = "", username = "", password = "", actionName;
	if (patron == null) {
		heading = "Create Account";
		actionName = "Creation";
		formAction = "add-patron";
	} else {
		heading = "Update Account";
		actionName = "Update";
		formAction = "update-patron";
		firstName = patron.getFirstName();
		lastName = patron.getLastName();
		username = patron.getUserName();
		password = patron.getPassword();
	}
	boolean fail = request.getAttribute("fail") != null;
%>

<script>
	console.log("username: ", "<%= username %>");
	console.log("password: ", "<%= password %>");
</script>

<div class="container">
	<h1 class="display-3 text-center">
		<%= heading %>
	</h1>
	
	<% if (fail) { %>
		<div class="alert alert-danger text-center" role="alert">
			<h3 class="display-5 text-center">Account <%= actionName %> Failed</h3>
			<p style="margin: 0.3rem 0;">NInvalid form submission.</p>
			<p style="margin: 0;">Please try again.</p>
		</div>
	<% } %>
	
	<form action="<%= formAction %>" method="post">
		<div class="form-group">
			<label for="firstname">First Name</label> 
			<input
				type="text" class="form-control" id="firstname"
				name="firstname" placeholder="Enter your first name"
				value="<%= firstName %>"
			>
		</div>
		
		<div class="form-group">
			<label for="lastname">Last Name</label> 
			<input
				type="text" class="form-control" id="lastname"
				name="lastname" placeholder="Enter your last name"
				value="<%= lastName %>">
		</div>
		
		<div class="form-group">
			<label for="username">New username</label> 
			<input
				type="text" class="form-control" id="username"
				name="username" placeholder="New username"
				value="<%= username %>">
		</div>
		
		<div class="form-group">
			<label for="password">New password</label> 
			<input
				type="password" class="form-control" id="password"
				name="password" placeholder="New password"
				value="<%= password %>">
		</div>
		
		<button type="submit" class="btn btn-primary">Submit</button>
	</form>

</div>

<%@ include file="footer.jsp"%>