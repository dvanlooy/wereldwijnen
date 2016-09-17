<%@ page contentType='text/html' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@ taglib prefix="v" uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<v:head title='Wereldwijnen&nbsp;${land.naam}' />
<v:header />
<c:if test="${not empty fout}">
	<div class="alert alert-danger" role="alert">
		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
		<span class="sr-only"><strong>Error:</strong></span> <strong>OPGELET!</strong>
		${fout}.
	</div>
</c:if>
<c:if test="${not empty soorten && not empty land}">
<h2>Soorten uit ${land.naam}</h2>
	<c:forEach items="${soorten}" var="soort">
		<c:url value="/soort.htm" var="soorturl">
			<c:param name="id" value="${soort.id}" />
		</c:url>
		<a href="${soorturl}" class="btn btn-default" role="button" style="margin-bottom:5px">${soort.naam}</a>
	</c:forEach>

</c:if>
<v:footer />