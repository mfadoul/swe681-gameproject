<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Matthew Fadoul SWE 632, 04/13/2013 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dominion Game Website - User Info</title>
<jsp:include page="HeadForJQuery.jsp" flush="true" />
<script>
	$(function() {
		$("#accordion1").accordion({
			collapsible : true,
			heightStyle: "content"
		});
	});

	$(function() {
		$("#accordion2").accordion({
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
				<c:if test="${user ne null}">
					<h2>
						User Account (E-mail): <a href="<c:out value="mailto:${user.email}" />"><c:out
								value="${user.email}" /></a>
					</h2>
					<h3>Full Name:</h3>
						<c:out value="${user.salutation}" />
						<c:out value="${user.firstname}" />
						<c:out value="${user.lastname}" />
					<h3>Role:</h3>
						<c:out value="${user.userRole.description}" />
					
					<h3>About Me</h3>
					<p>
						<c:out value="${user.aboutme}" />
					</p>
						<h3> Statistics</h3>
						<h4>Wins: <c:out value="${gamesWonCount}" /></h4>
		        		<c:if test="${gamesWonList ne null}">
						<div id="accordion1">
			        		<c:forEach items="${gamesWonList}" var="gameStateWon">
								<c:url value="/GameProject/game/report/${gameStateLost.id}" var="gameWonInfoLink" />
						        <h5>Game #<c:out value="${gameStateWon.id}" /></h5>
								<div>
									<p><a href="<c:out value="${gameWonInfoLink}" />">Results for Game #<c:out value="${gameStateWon.id}" />
									</a></p>
								</div>
							</c:forEach>
						</div>
		        	    </c:if>
						<h4>Losses: <c:out value="${gamesLostCount}" /></h4>
		        		<c:if test="${gamesLostList ne null}">
						<div id="accordion2">
			        		<c:forEach items="${gamesLostList}" var="gameStateLost">
								<c:url value="/GameProject/game/report/${gameStateLost.id}" var="gameLostInfoLink" />
						        <h5>Game #<c:out value="${gameStateLost.id}" /></h5>
								<div>
									<p>
										<a href="<c:out value="${gameLostInfoLink}" />">Results for Game #<c:out value="${gameStateLost.id}" />
										</a>
									</p>
								</div>
							</c:forEach>
						</div>
		        	    </c:if>
					</c:if>
    				<c:if test="${user eq null}">
					<h1>Unfortunately, I could not find that user.</h1>
					</c:if>				
				</td>
			</tr>
		</table>
		<jsp:include page="Bottom.jsp" flush="true" />
	</div>
</body>
</html>



