<%@ page contentType='text/html' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@ taglib prefix="v" uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<v:head title='Wereldwijnen&nbsp;${wijn}' />
<v:header />
<c:if test="${not empty fout}">
	<div class="alert alert-danger" role="alert">
		<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
		<span class="sr-only"><strong>Error:</strong></span> <strong>OPGELET!</strong>
		${fout}.
	</div>
</c:if>
<c:if test="${not empty wijn}">
	<h2>Wijn toevoegen aan mandje</h2>
	<c:url value="/soort.htm" var="soorturl">
		<c:param name="id" value="${wijn.soort.id}" />
	</c:url>
	<a href="${soorturl}" class="btn btn-default" role="button">Terug
		naar overzicht ${wijn.soort.naam} uit ${wijn.soort.land.naam}</a>
	<div class="container">
		<p style="width: 300px">
		<dl class="row">
			<dt class="col-sm-2">Land</dt>
			<dd class="col-sm-9">${wijn.soort.land.naam}</dd>

			<dt class="col-sm-2">Soort</dt>
			<dd class="col-sm-9">${wijn.soort.naam}</dd>

			<dt class="col-sm-2">Jaar</dt>
			<dd class="col-sm-9">${wijn.jaar}</dd>

			<dt class="col-sm-2">Beoordeling</dt>
			<dd class="col-sm-9">${wijn.sterretjes}</dd>

			<dt class="col-sm-2">Prijs</dt>
			<dd class="col-sm-9">
				<fmt:formatNumber value='${wijn.prijs}' />
			</dd>
		</dl>
		</p>
		<p>
		<form action="" method="post">
			<input name="id" type="hidden" value="${wijn.id}">
			<div class="input-group" style="width: 300px">
				<input name="aantal" type="number" class="form-control"
					placeholder="Aantal flessen..." min="1"
					value="${reedsToegevoegdAantal}"> <span
					class="input-group-btn"> <input type="submit"
					class="btn btn-default" value="Toevoegen" id="toevoegen">
				</span>
			</div>
			<c:if test="${not empty fouten.aantal}">
				<br>
				<div class="alert alert-danger" role="alert" style="width: 300px">${fouten.aantal}</div>
			</c:if>
		</form>
		</p>
	</div>
</c:if>

<v:footer />