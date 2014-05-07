<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Matthew Fadoul SWE 681, 04/26/2014 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dominion Game Website - Active Players</title>
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
				<h2>Players who are online</h2>
				<c:if test="${activeUsers ne null}">
					<div id="accordion">
						<c:forEach items="${activeUsers}" var="activeUser">
						<c:url value="/GameProject/user/info/${activeUser.id}" var="userInfoLink" />
				        <h3><c:out value="${activeUser.salutation} ${activeUser.firstname} ${activeUser.lastname}" /></h3>
						<div>
							<p><a href="<c:out value="${userInfoLink}" />">User Info Page</a></p>	
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



