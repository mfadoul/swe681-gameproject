<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Matthew Fadoul SWE 632, 04/13/2013 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Rating System - User Info</title>
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
		        	<p> 
		        		<c:if test="${user.id ne loggedInUser.id}">
			        	    <a type=detailed_info_link href="/GameProjectWebApp/GameProject/review/list/${user.id}">
			        	        <c:out value="${user.salutation}" /> <c:out value="${user.lastname}" />'s Reviews</a>
		        	    </c:if>
		        		<c:if test="${user.id eq loggedInUser.id}">
			        	    <a type=detailed_info_link href="/GameProjectWebApp/GameProject/review/list/${user.id}">
			        	        My Reviews</a>
		        	    </c:if>
		        	</p>
					
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



