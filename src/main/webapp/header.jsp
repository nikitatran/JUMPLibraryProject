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
<style>
<%-- reference: https://stackoverflow.com/questions/65391991/css-in-bootstrap-4-mask --%>
	.bg-image{
	    position: relative;
	    overflow: hidden;
	    background-repeat: no-repeat;
	    background-size: cover;
	}
	
	.bg-image .mask{
	    position: absolute;
	    top: 0;
	    right: 0;
	    bottom: 0;
	    left: 0;
	    width: 100%;
	    height: 100%;
	    overflow: hidden;
	    color:#fff;
	}
	
	.bg-image .mask .text{
	    height: 100%;
	    display: flex!important;
	    align-items: center!important;
	    justify-content: center!important;
	}
	
	.bg-image .mask .text p{
	    margin:0;
	}
	
	<%@ include file="style.css"  %>
</style>
</head>
<body>

	<header>
		<c:if test="${ signedIn }">
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<a class="navbar-brand" href="<%=request.getContextPath()%>/dashboard">LIBRARY</a>
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
						<li class=""><a class="nav-link"
							href="<%=request.getContextPath()%>/updateaccount">Update Account</a></li>
						<li class="nav-item" style="margin-left: 0px;"><a class="nav-link"
							href="<%=request.getContextPath()%>/logout" style="color:red">Logout</a></li>
					</ul>

				</div>
			</nav>
			</c:if>
	</header>

