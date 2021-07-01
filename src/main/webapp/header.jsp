<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.cognixia.jump.connection.ConnectionManager"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Library</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
	integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
	crossorigin="anonymous">

</head>
<body>

	<header>
		<c:if test="${ signedIn }">
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<a class="navbar-brand" href="<%=request.getContextPath()%>/">LIBRARY</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse"
					data-target="#navbarNav" aria-controls="navbarNav"
					aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarNav">

					<ul class="navbar-nav">

						<li class="nav-item"><a class="nav-link"
							href="<%=request.getContextPath()%>/catalogue">All Books</a></li>
						<li class="nav-item"><a class="nav-link"
							href="<%=request.getContextPath()%>/myaccount">My Books</a></li>
						<li class="nav-item"><a class="nav-link"
							href="<%=request.getContextPath()%>/dashboard">Back</a></li>
					</ul>

				</div>
			</nav>
			</c:if>
	</header>