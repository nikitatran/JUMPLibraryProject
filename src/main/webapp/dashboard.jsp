<%@ include file="header.jsp"%>
<h1 class="display-3 text-center">Welcome to the Library!</h1>
<div id="carouselExampleIndicators" class="carousel slide"
	data-ride="carousel">
	<ol class="carousel-indicators">
		<li data-target="#carouselExampleIndicators" data-slide-to="0"
			class="active"></li>
		<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
		<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
	</ol>
	<div class="carousel-inner">
		<div class="carousel-item active" style="height: 450px;">
			<a href="<%=request.getContextPath()%>/catalogue"><img
				class="d-block w-100"
				src="https://www.clemson.edu/registrar/images/cooper-library.jpg"
				alt="First slide"> </a>
			<div class="carousel-caption d-none d-md-block">
				<h5>All Books</h5>
			</div>
		</div>
		<div class="carousel-item carousel-inner" style="height: 450px;">
			<a href="<%=request.getContextPath()%>/myaccount"> <img
				style="margin-top: -335px" class="d-block w-100"
				src="http://freedesignfile.com/upload/2017/01/Young-female-student-holding-books-HD-picture.jpg"
				alt="Second slide">
			</a>
			<div class="carousel-caption d-none d-md-block">
				<h5>My Books</h5>
			</div>
		</div>
		<div class="carousel-item" style="height: 450px;">
			<a href="<%=request.getContextPath()%>/updateaccount"> <img
				style="margin-top: -200px" class="d-block w-100"
				src="https://kcjmngo.com/wp-content/uploads/2017/02/bank_details_update.jpg"
				alt="Third slide">
			</a>

			<div class="carousel-caption d-none d-md-block">
				<h5>Update Account</h5>
			</div>
		</div>
	</div>
	<a class="carousel-control-prev" href="#carouselExampleIndicators"
		role="button" data-slide="prev"> <span
		class="carousel-control-prev-icon" aria-hidden="true"></span> <span
		class="sr-only">Previous</span>
	</a> <a class="carousel-control-next" href="#carouselExampleIndicators"
		role="button" data-slide="next"> <span
		class="carousel-control-next-icon" aria-hidden="true"></span> <span
		class="sr-only">Next</span>
	</a>
</div>



<%@ include file="footer.jsp"%>