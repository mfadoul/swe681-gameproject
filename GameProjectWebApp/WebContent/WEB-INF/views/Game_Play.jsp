<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Matthew Fadoul SWE 632, 04/13/2013 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dominion Game Website - Play a new game</title>
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
				<c:if test="${player ne null}">
					<h3>In a game.</h3>
					<!-- ADD CONTENT HERE -->
					<form:form modelAttribute="actVm" action="play" method="post">
						<form:label for="command" path="command" cssErrorClass="error">Command</form:label>
						<form:input path="command" size="40" maxlength="100" title="Enter a command" /> <form:errors path="command" />
					</form:form>
					Player State: Stats
					<table>
						<tr>
							<td>Buys:</td>
							<td><c:out value="${player.buyCount }" /></td>
						</tr>
						<tr>
							<td>Actions:</td>
							<td><c:out value="${player.actionCount }" /></td>
						</tr>
						<tr>
							<td>Treasure:</td>
							<td><c:out value="${player.coinCount }" /></td>
						</tr>
					</table>
					Player State: Hand
					<table>
						<c:forEach items="${player.actionsToPlay}" var="entry">
							<tr>
								<td>${entry.key } (${entry.value })</td>
							</tr>
						</c:forEach>
						
					</table>
					Game State: Available Cards
					<table>
						<c:forEach items="${gameState.availableCards}" var="entry">
							<tr>
								<td>${entry.key } (${entry.value })</td>
							</tr>
						</c:forEach>
						
					</table>
					<h2>Players:</h2>
					<c:forEach items="${player.gameState.players}" var="gamePlayer">
						<c:if test="${player.id eq gamePlayer.id}">
							<h3>* Me!</h3>
						</c:if>	        
						<c:if test="${player.id ne gamePlayer.id}">
					        <h3>* <c:out value="${gamePlayer.user.salutation}" /> <c:out value="${gamePlayer.user.firstname}" /> <c:out value="${gamePlayer.user.lastname}" /></h3>
					    </c:if>
					</c:forEach>
					
					<h3></h3>				
     				<!-- END OF CONTENT HERE -->				
				</c:if>
				<c:if test="${player eq null}">
					<h3>Failed to create a game...</h3>
				</c:if>
				</td>
			</tr>
		</table>
		<jsp:include page="Bottom.jsp" flush="true" />
	</div>
</body>
</html>



