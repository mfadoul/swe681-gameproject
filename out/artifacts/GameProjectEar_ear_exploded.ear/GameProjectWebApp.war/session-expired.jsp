<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Game Project</title>
<jsp:include page="WEB-INF/views/HeadForJQuery.jsp" flush="true" />
</head>
<body>
	<div id="container">
		<jsp:include page="WEB-INF/views/Top.jsp" flush="true" />

		<table border="1" width="100%" align="center"
			style="background-color: #55BB55;">
			<tr valign="top">
				<td width="120px">
					<jsp:include page="WEB-INF/views/Menu.jsp" flush="true" />
				</td>
				<td style="background-color: #DDFFDD;">
				    <h2>Welcome to the Dominion Game Website.</h2>
					<p>Your session has expired.</p>
					<h2></h2>
					<p>Use the menu on the left to explore the site.</p>
  					<sec:authorize access="!isAuthenticated()">
					<p>
					    New to our site? Sign up in minutes! 
				    	<a type="add_link" title="Sign up to create a new user account" href="GameProject/user/create">Sign up</a>
					</p>
					</sec:authorize>
					<div align="center">
						<img class="logo" height="350" width="350"
							src="/GameProjectWebApp/images/dominion_game.jpg" alt="Dominion Logo" />
					</div>
				</td>
			</tr>
		</table>
		<jsp:include page="WEB-INF/views/Bottom.jsp" flush="true" />

	</div>

</body>
</html>