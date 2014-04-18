<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Matthew Fadoul SWE 681, 03/30/2014 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Rating System - Create User</title>
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
				<c:if test="${successfullyCreatedUser eq false}">
				<form:form modelAttribute="user" action="create" method="post">
					<fieldset title="Create User">
						<legend><b>Create a New User</b></legend>
						<table border="0" cellspacing="3" cellpadding="0" align="center">
							<tr>
								<td align="right"><form:label for="email" path="email" cssErrorClass="error">* User Name (email) :</form:label></td>
								<td align="left"><form:input path="email" size="40" maxlength="40" title="Please provide a valid e-mail as your user name" /> <form:errors path="email" /></td>	
							</tr>
							<tr>
								<td align="right"><form:label for="password" path="password" cssErrorClass="error">* Password :</form:label></td>
								<td align="left"><form:password path="password" size="20" maxlength="32" title="This will help protect your account"/> <form:errors path="password" /></td>	
							</tr>
							<tr>
								<td align="right"><form:label for="salutation" path="salutation" cssErrorClass="error">Saluation :</form:label></td>
								<td align="left"><form:input path="salutation" size="5" maxlength="5" title="Examples: Mr., Miss, Mrs., Dr." /> <form:errors path="salutation" /></td>	
							</tr>
							<tr>
								<td align="right"><form:label for="firstname" path="firstname" cssErrorClass="error">* First Name :</form:label></td>
								<td align="left"><form:input path="firstname" size="20" maxlength="32" title="Your given name" /> <form:errors path="firstname" /></td>	
							</tr>
							<tr>
								<td align="right"><form:label for="lastname" path="lastname" cssErrorClass="error">* Last Name :</form:label></td>
								<td align="left"><form:input path="lastname" size="20" maxlength="32" title="Your surname" /> <form:errors path="lastname" /></td>	
							</tr>
							<tr>
								<td align="right"><form:label for="aboutme" path="aboutme" cssErrorClass="error">About me :</form:label></td>
								<td align="left"><form:textarea path="aboutme" maxlength="1024" rows="4" cols="40" title="Tell us something about yourself" /> <form:errors path="aboutme" /></td>	
							</tr>	
												
<%-- 							
								<tr>
								<td align="right"><form:label for="rating" path="rating" cssErrorClass="error">Your Rating:</form:label></td>
								<td align="left">
					                <form:select path="rating" items="${review.ratingOptions}" title="Please rate from 1 (poor) to 5 (excellent)"/> <form:errors path="rating" />
								</td>
							</tr>
 --%>
 						</table>
						<hr />							
						<div align="center">
							<input type="submit" title="Create a user account" value="Create an account"/> |
							<input type="reset"  title="Clear the form" value="Clear Form"/>
						</div>
					</fieldset>
				</form:form>
				</c:if>
				<c:if test="${successfullyCreatedUser eq true}">
					<h1>Congratulations, <c:out value="${user.salutation}" /> <c:out value="${user.firstname}" /> <c:out value="${user.lastname}" />!</h1>
					<p>Welcome to our online community, where you can share with others who love to read as much as you!  
					Your account is: <emp> <c:out value="${user.email}" /></emp>.  Please sign in.</p>
					<sec:authorize access="!isAuthenticated()">
							<a type="login_link" href="<c:url value="/login.jsp" />" title="Sign in with your new account">Sign in</a>
					</sec:authorize>
				</c:if>
				<!-- END OF CONTENT HERE -->
				</td>
			</tr>
		</table>
		<jsp:include page="Bottom.jsp" flush="true" />
	</div>
</body>
</html>



