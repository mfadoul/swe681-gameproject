<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Matthew Fadoul SWE 632, 04/13/2013 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dominion Game Website - Game Report</title>
<jsp:include page="HeadForJQuery.jsp" flush="true" />
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
				<h2>Game Report</h2>
				<c:if test="${gameState ne null}">
					<h3>Time</h3>
					<p>Game Played from <c:out value="${gameState.beginDate}" /> 
					to <c:out value="${gameState.endDate}" /></p>
					<h3>Players</h3>
					<c:forEach items="${gameState.players}" var="player">
						<c:url value="/GameProject/user/info/${player.user.id}" var="userInfoLink" />
						<p><a href="<c:out value="${userInfoLink}" />">User: 
							<c:out value="${player.user.salutation}" /> <c:out value="${player.user.firstname}" /> <c:out value="${player.user.lastname}" />
							</a>
							<c:if test="${gameState.winnerId eq player.id}">
							(Winner!)
							</c:if>			
						</p>
					</c:forEach>
			
					<c:if test="${cardEvents ne null}">
						<table border="1" width="100%" align="center"
							style="background-color: #55BB55;">
							<tr valign="top">
								<th>Time</th>
								<th>Card Type</th>
								<th>Location</th>
								<th>Player ID</th>
							</tr>					
							<c:forEach items="${cardEvents}" var="cardEvent">
								<tr>
									<td><c:out value="${cardEvent.eventDate}" /></td>
									<td><c:out value="${cardEvent.cardType}" /></td>
									<td><c:out value="${cardEvent.location}" /></td>
									<td><c:out value="${cardEvent.playerId}" /></td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
				</c:if>

				<!-- END OF CONTENT HERE -->
				</td>
			</tr>
		</table>
		<jsp:include page="Bottom.jsp" flush="true" />
	</div>
</body>
</html>



