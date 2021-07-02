<%@ include file="header.jsp"%>

<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
  <ol class="carousel-indicators">
    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
  </ol>
  <div class="carousel-inner">
    <div class="carousel-item active">
    <a href="<%=request.getContextPath()%>/catalogue">All Books
      <img class="d-block w-100" src="https://www.clemson.edu/registrar/images/cooper-library.jpg" alt="First slide">
    </a>
    </div>
    <div class="carousel-item">
    <a href="<%=request.getContextPath()%>/myaccount">My Books
      <img class="d-block w-100" src="http://freedesignfile.com/upload/2017/01/Young-female-student-holding-books-HD-picture.jpg" alt="Second slide">
    </a>
    </div>
    <div class="carousel-item">
    <a href="<%=request.getContextPath()%>/updateaccount">Update Account
      <img class="d-block w-100" src="https://kcjmngo.com/wp-content/uploads/2017/02/bank_details_update.jpg" alt="Third slide">
    </a>
    </div>
  </div>
  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>

<%@ include file="footer.jsp"%>