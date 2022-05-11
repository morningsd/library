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
					<h4>Caption</h4>
					<p>Paragraph</p>
				</div>
			</div>
			<div class="carousel-item">
				<img class="d-block w-100" src="/img/slide-photo-3.jpeg" alt="Third slide">
				<div class="carousel-caption d-none d-md-block">
					<h4>Caption</h4>
					<p>Paragraph</p>
				</div>
			</div>
			<div class="carousel-item">
				<img class="d-block w-100" src="/img/slide-photo-4.jpeg" alt="Fourth slide">
				<div class="carousel-caption d-none d-md-block">
					<h4>Caption</h4>
					<p>Paragraph</p>
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

<script src="/js/jquery-3.6.0.min.js" />
<script src="/js/bootstrap.min.js" />
</body>
</html>