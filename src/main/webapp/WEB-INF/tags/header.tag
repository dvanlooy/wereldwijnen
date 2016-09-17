<%@ tag description='header onderdeel van pagina' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="v" uri='http://vdab.be/tags'%>
<body>
	<section class='jumbotron'>
		<div class="container">
			<nav class="navbar navbar-default">
				<div class="container text-center">
					<h1>Wereldwijnen</h1>

					<ul class="list-inline">
						<c:forEach items="${landen}" var="land">
							<c:url value="/land.htm" var="landurl">
								<c:param name="id" value="${land.id}" />
							</c:url>
							<li><a href="${landurl}"><img alt="${land.naam}"
									src="<c:url value="/images/${land.id}.png" />"></a></li>
						</c:forEach>
					</ul>
					<c:if test="${not empty mandje}">
						<h2>
							<a href="<c:url value="/mandje.htm" />"><span
								class="glyphicon glyphicon-shopping-cart"></span> </a>
						</h2>
					</c:if>
				</div>
			</nav>
