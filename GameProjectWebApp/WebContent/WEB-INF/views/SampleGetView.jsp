<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Matthew Fadoul SWE 632, 04/13/2013 -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sample Get</title>
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
				<form:form modelAttribute="sampleData" action="samplePost" method="post">
					<fieldset title="Create Sample Data">
						<legend><b>Sample Data Form</b></legend>
						<table border="0" cellspacing="3" cellpadding="0" align="center">
							<tr>
								<td align="right"><form:label for="foo" path="foo" cssErrorClass="error">* Foo :</form:label></td>
								<td align="left"><form:input path="foo" size="40" maxlength="40" title="Please provide a valid string for foo" /> <form:errors path="foo" /></td>	
							</tr>
							<tr>
								<td align="right"><form:label for="bar" path="bar" cssErrorClass="error">* Bar :</form:label></td>
								<td align="left"><form:input path="bar" size="40" maxlength="40" title="Please provide a valid string for bar" /> <form:errors path="bar" /></td>	
							</tr>
												
 						</table>
						<hr />							
						<div align="center">
							<input type="submit" title="Submit Sample Data" value="Submit Sample Data"/> |
							<input type="reset"  title="Clear the form" value="Clear Form"/>
						</div>
					</fieldset>
				</form:form>
				</c:if>
				
				<!-- END OF CONTENT HERE -->
				</td>
			</tr>
		</table>
		<jsp:include page="Bottom.jsp" flush="true" />
	</div>
</body>
</html>



