<%@ page contentType='text/html' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@ taglib prefix="v" uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<v:head title='Wereldwijnen&nbsp;${wijn}' />
<v:header />

<h2>Mandje</h2>
<c:if test="${empty bestelbonlijnen}">
<div class="alert alert-warning" role="alert">Geen mandje beschikbaar</div>
</c:if>
<c:if test="${not empty bestelbonlijnen}">

	<p>
	<table class="table table-striped">
		<tr class="info">
			<th class=" text-center">Wijn</th>
			<th class="col-md-2 text-center">Prijs</th>
			<th class="col-md-2 text-center">Aantal</th>
			<th class="col-md-2 text-center">Te betalen</th>
		</tr>
		<form action="" method="post">
		<c:forEach items='${bestelbonlijnen}' var='bestelbonlijn'>
		<c:url value="/wijn.htm" var="wijnurl">
				<c:param name="id" value="${bestelbonlijn.wijn.id}" />
			</c:url>
			<tr>
				<td><button name="remove" type="submit" class="btn btn-default btn-sm" value="${bestelbonlijn.wijn.id}">
          <span class="glyphicon glyphicon-trash"></span> verwijder
        </button>&nbsp;<a href="${wijnurl}">${bestelbonlijn.wijn.soort.land.naam}&nbsp;${bestelbonlijn.wijn.soort.naam}&nbsp;${bestelbonlijn.wijn.jaar}</a></td>
				<td class="col-md-2 text-center"><fmt:formatNumber
						value='${bestelbonlijn.wijn.prijs}' /></td>
				<td class="col-md-2 text-center">${bestelbonlijn.aantal}</td>
				<td class="col-md-2 text-center"><fmt:formatNumber
						value='${bestelbonlijn.totaleWaarde}' /></td>
			</tr>
		</c:forEach>
		</form>
		<tr>
			<th></th>
			<th class="col-md-2"></th>
			<th class="col-md-2"></th>
			<th class="col-md-2 text-center">Totaal: <fmt:formatNumber
					value='${totaal}' /></th>

		</tr>
	</table>
	<form action="" method="post">
		<div class="input-group" style="margin-bottom: 5px; width: 400px">
			<span class="input-group-addon" id="basic-addon1"
				style="width: 120px">Naam</span> <input name="naam" type="text"
				size="50" class="form-control" placeholder="Naam"
				aria-describedby="basic-addon1" required><span>${fouten.naam}</span>
		</div>

		<div class="input-group" style="margin-bottom: 5px; width: 400px">
			<span class="input-group-addon" id="basic-addon1"
				style="width: 120px">Straat</span> <input name="straat" type="text"
				size="50" class="form-control" placeholder="Straat"
				aria-describedby="basic-addon1" required><span>${fouten.straat}</span>
		</div>

		<div class="input-group" style="margin-bottom: 5px; width: 400px">
			<span class="input-group-addon" id="basic-addon1"
				style="width: 120px">Huisnummer</span> <input name="huisnummer"
				type="text" size="50" class="form-control" placeholder="Straat"
				aria-describedby="basic-addon1" required><span>${fouten.huisnummer}</span>
		</div>

		<div class="input-group" style="margin-bottom: 5px; width: 400px">
			<span class="input-group-addon" id="basic-addon1"
				style="width: 120px">Postcode</span> <input name="postcode"
				type="text" size="50" class="form-control" placeholder="Postcode"
				aria-describedby="basic-addon1" required><span>${fouten.postcode}</span>
		</div>

		<div class="input-group" style="margin-bottom: 5px; width: 400px">
			<span class="input-group-addon" id="basic-addon1"
				style="width: 120px">Gemeente</span> <input name="gemeente"
				type="text" size="50" class="form-control" placeholder="Gemeente"
				aria-describedby="basic-addon1" required><span>${fouten.gemeente}</span>
		</div>

		<c:forEach items="${bestelwijzen}" var="bestelwijze">
			<div class="input-group" style="margin-bottom: 5px; width: 400px">
				<span class="input-group-addon"> <input type="radio"
					name="bestelwijze" value="${bestelwijze}">
				</span> <span class="form-control">
					${fn:toUpperCase(fn:substring(bestelwijze, 0, 1))}${fn:toLowerCase(fn:substring(bestelwijze, 1, -1))}</span>
			</div>

		</c:forEach>
		<span>${fouten.bestelwijze}</span> <input type="submit"
			value="Als bestelbon bevestigen" class="btn btn-default">

	</form>



	</p>











</c:if>


<v:footer />