<%@page import="com.cognixia.jump.models.PatronsModel"%>
<%@ include file="header.jsp"%>

<%
	boolean isLibrarian = request.getAttribute("isLibrarian") != null;

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
	String failMessage = (String) request.getAttribute("failMessage");
	if (failMessage == null) failMessage = "Invalid form submission. Do not leave any fields blank. Please try again.";
%>

<div class="container">
	<h1 class="display-3 text-center p-4">
		<%= heading %>
	</h1>
	
	<% if (fail) { %>
		<div class="alert alert-danger text-center alert-dismissable fade show" role="alert">
		 	<button type="button" class="close" data-dismiss="alert" aria-label="Close">
    			<span aria-hidden="true">&times;</span>
  			</button>
			<h3 class="display-5 text-center">Account <%= actionName %> Failed</h3>
			<p style="margin: 0.3rem 0 0;"><%= failMessage %></p>
		</div>
	<% } %>
	
	<form action="<%= formAction %>" method="post">
		<div class="form-group">
			<label for="firstname">First Name</label> 
			<input
				type="text" class="form-control" id="firstname"
				name="firstname" placeholder="Enter your first name"
				value="<%= firstName %>" required
			>
		</div>
		
		<div class="form-group">
			<label for="lastname">Last Name</label> 
			<input
				type="text" class="form-control" id="lastname"
				name="lastname" placeholder="Enter your last name"
				value="<%= lastName %>" pattern="^.{2,}$" title="at least 2 characters long" 
				oninput="setCustomValidity('')">
		</div>
		
		<div class="form-group">
			<label for="username">New username</label> 
			<input
				type="text" class="form-control" id="username"
				name="username" placeholder="New username"
				value="<%= username %>" pattern="^.{3,}$" title="at least 3 characters long">
		</div>
		
		<div class="form-group">
			<label for="password">New password</label> 
			<input
				type="password" class="form-control" id="password"
				name="password" placeholder="New password"
				value="<%= password %>" pattern="^.{4,}$" title="at least 4 characters long">
		</div>
		
		<button type="submit" class="btn btn-primary">Submit</button>
	</form>

</div>

<%@ include file="footer.jsp"%>