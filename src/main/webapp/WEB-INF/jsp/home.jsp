<%@ include file="/WEB-INF/jspf/page.jspf" %>
<html>
<c:set var="title" value="Home"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>
<body>
<div class="container">
	<%@ include file="/WEB-INF/jspf/header.jspf" %>

	<div id="carousel" class="carousel slide" data-ride="carousel">
		<div class="carousel-inner">
			<div class="carousel-item active">
				<img class="d-block w-100" src="/img/slide-photo-1.jpg" alt="First slide">
				<div class="carousel-caption d-none d-md-block">
					<h4><fmt:message key="home_jsp.carousel.caption1" /></h4>
					<p><fmt:message key="home_jsp.carousel.description1" /></p>
				</div>
			</div>
			<div class="carousel-item">
				<img class="d-block w-100" src="/img/slide-photo-3.jpeg" alt="Third slide">
				<div class="carousel-caption d-none d-md-block">
					<h4><fmt:message key="home_jsp.carousel.caption2" /></h4>
					<p><fmt:message key="home_jsp.carousel.description2" /></p>
				</div>
			</div>
			<div class="carousel-item">
				<img class="d-block w-100" src="/img/slide-photo-4.jpeg" alt="Fourth slide">
				<div class="carousel-caption d-none d-md-block">
					<h4><fmt:message key="home_jsp.carousel.caption3" /></h4>
					<p><fmt:message key="home_jsp.carousel.description3" /></p>
				</div>
			</div>
		</div>
		<a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
			<span class="carousel-control-prev-icon" aria-hidden="true"></span>
			<span class="sr-only">Previous</span>
		</a>
		<a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
			<span class="carousel-control-next-icon" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>

	<%@ include file="/WEB-INF/jspf/footer.jspf" %>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js" integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s" crossorigin="anonymous"></script>
</body>
</html>