<%@ page contentType='text/html' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@ taglib prefix="v" uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<v:head title='Wereldwijnen&nbsp;${land.naam}&nbsp;${soort.naam}' />
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
	<c:forEach items="${soorten}" var="eensoort">
		<c:url value="/soort.htm" var="soorturl">
			<c:param name="id" value="${eensoort.id}" />
		</c:url>
		<a href="${soorturl}"
			class="btn btn-default ${eensoort.id == soort.id ? 'active' : ''}"
			role="button" style="margin-bottom: 5px">${eensoort.naam}</a>
	</c:forEach>
	<h3>${soort.naam}&nbsp;wijnen</h3>
	<div class="list-group" style="width:200px">
		<c:forEach items="${wijnen}" var="wijn">
			<c:url value="/wijn.htm" var="wijnurl">
				<c:param name="id" value="${wijn.id}" />
			</c:url>
			<a href="${wijnurl}" class="list-group-item">${wijn.jaar}&nbsp;<span class="badge">${wijn.sterretjes}</span></a>
		</c:forEach>
	</div>
</c:if>
<v:footer />