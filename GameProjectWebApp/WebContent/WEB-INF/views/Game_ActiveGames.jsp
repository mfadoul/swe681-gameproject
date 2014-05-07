<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Matthew Fadoul SWE 681, 04/27/2014 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dominion Game Website - Active Games</title>
<jsp:include page="HeadForJQuery.jsp" flush="true" />
<script>
	$(function() {
		$("#accordion").accordion({
			collapsible : true,
			heightStyle: "content"
		});
	});

	$(function() {
		$("#radio").buttonset();
	});
</script>
</head>
<body>
	<div id="container">
		<jsp:include page="Top.jsp" flush="true" />

		<table border="1" width="100%" align="center"
			style="background-color: #55BB55;">
			<tr valign="top">
				<td width="120px"><jsp:include page="Menu.jsp" flush="true" />
				</td>
				<td style="background-color: #DDFFDD;">
				<!-- ADD CONTENT HERE -->
				<h2>Active Games</h2>
				<c:if test="${gameStates ne null}">
					<h3>(Found <c:out value="${fn:length(gameStates)}" /> active games)</h3>
					<div id="accordion">
						<c:forEach items="${gameStates}" var="gameState">
						<c:url value="/GameProject/game/watch/${gameState.id}" var="gameWatchLink" />
				        <h3><c:out value="${gameState.id}" /></h3>
						<div>
							<p>Play a game with a waiting player!</p>
							<p><a href="<c:out value="${gameWatchLink}" />">Watch game #<c:out value="${gameState.id}" /></a></p>
						</div>
						</c:forEach>
					</div>
				</c:if> 
				
				<!-- END OF CONTENT HERE -->
				</td>
			</tr>
		</table>
		<jsp:include page="Bottom.jsp" flush="true" />
	</div>
</body>
</html>



