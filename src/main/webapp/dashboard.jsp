<%@ include file="header.jsp"%>

<%
String viewheight = "50vh";
String captionHeader = "h1 class=\"font-weight-bold\" style=\"text-shadow:0px 2px 6px black;\"";
String captionBody = "h5 class=\"mb-3 font-weight-bold\" style=\"text-shadow:0px 2px 6px black;\"";
String buttonStyle = "btn btn-primary mb-2";
String maskOpacity = "0.3";
%>


<h1 class="display-3 text-center p-5">Welcome to the Library, <c:out value="${ patron.firstName }" />!</h1>

<div id="carousel" class="carousel slide" data-ride="carousel">
	<!-- INDICATORS -->
	<ol class="carousel-indicators">
		<li data-target="#carousel" data-slide-to="0" class="active"></li>
		<li data-target="#carousel" data-slide-to="1"></li>
		<li data-target="#carousel" data-slide-to="2"></li>
	</ol>

	<!-- SLIDES -->
	<div class="carousel-inner">
		<div class="carousel-item active" style="height: <%=viewheight%>;">

			<div class="bg-image">
				<img
					class="d-block w-100" style="margin-top: 0px"
					src="https://static01.nyt.com/images/2018/06/03/books/review/03GLASSIE-SUB/03GLASSIE-SUB-videoSixteenByNineJumbo1600.jpg"
					alt="Library">
				<div class="mask" style="background-color: rgba(0, 0, 0, <%=maskOpacity%>)"></div>
			</div>

			<div class="carousel-caption d-none d-md-block">
				<<%=captionHeader%>>All Books</<%=captionHeader%>>
				<<%=captionBody%>>Our library has an extensive
				catalogue of various titles from well-known authors.</<%=captionBody%>>
				
				<a href="<%=request.getContextPath()%>/catalogue"><button type="button" class="<%=buttonStyle%>">View and
					checkout books</button></a>
			</div>

		</div>
		<div class="carousel-item carousel-inner"
			style="height: <%=viewheight%>;">
			<div class="bg-image">
				 <img
					class="d-block w-100" style="margin-top: 0px" class="d-block w-100"
					src="https://media.gq.com/photos/5a03579f3c1d3945a9ce29fc/16:9/pass/hub--books.png"
					alt="Books">
				
				<div class="mask" style="background-color: rgba(0, 0, 0,<%=maskOpacity%>)"></div>
			</div>


			<div class="carousel-caption d-none d-md-block">
				<<%=captionHeader%>>My Books</<%=captionHeader%>>
				<<%=captionBody%>>We help you keep track of the
				books you've checked out here, past and present.</<%=captionBody%>>
				<a href="<%=request.getContextPath()%>/myaccount"><button type="button" class="<%=buttonStyle%>">View your
					books</button></a>
			</div>
		</div>
		<div class="carousel-item" style="height: <%=viewheight%>;">
			<div class="bg-image">
				 <img
					class="d-block w-100" style="margin-top: 0px" class="d-block w-100"
					src="https://www.stryvemarketing.com/wp-content/uploads/2016/10/banner-typing-1600x900-1.png"
					alt="Typing">
				
				<div class="mask" style="background-color: rgba(0, 0, 0, <%=maskOpacity%>)"></div>
			</div>
			<div class="carousel-caption d-none d-md-block">
				<<%=captionHeader%>>Update Account</<%=captionHeader%>>
				
				<<%=captionBody%>>Need to change something? No problem!</<%=captionBody%>>
				
				<a href="<%=request.getContextPath()%>/updateaccount">
				<button type="button" class="<%=buttonStyle%>">Update account info</button></a>
				
				
			</div>
		</div>
	</div>

	<!-- CONTROLS -->
	<a class="carousel-control-prev" href="#carousel" role="button"
		data-slide="prev"> <span class="carousel-control-prev-icon"
		aria-hidden="true"></span> <span class="sr-only">Previous</span>
	</a> <a class="carousel-control-next" href="#carousel" role="button"
		data-slide="next"> <span class="carousel-control-next-icon"
		aria-hidden="true"></span> <span class="sr-only">Next</span>
	</a>
</div>



<%@ include file="footer.jsp"%>