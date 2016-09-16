<%@ tag description='header onderdeel van pagina' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="v" uri='http://vdab.be/tags'%>
<body>
	<section class='jumbotron'>
		<div class="container">
			<nav class="navbar navbar-default navbar-fixed-top">
			<div class="container text-center">
				<h1>Wereldwijnen</h1>
				<ul class="list-inline">
					<c:forEach items="${landen}" var="land">
						<c:url value="/land.htm" var="landurl">
							<c:param name="id" value="${land.id}" />
						</c:url>
						<li><a href="${landurl}"><img alt="${land.naam}" src="<c:url value="/images/${land.id}.png" />"></a></li>
					</c:forEach>
					<c:if test="${not empty mandje}">
						<li><a href="<c:url value="/mandje.htm" />"><img
								alt="mandje" src="<c:url value="/images/mandje.png" />"></a></li>
					</c:if>
				</ul>
				</div>
			</nav>