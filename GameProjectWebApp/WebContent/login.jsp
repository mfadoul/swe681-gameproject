<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Game Project Website</title>
<jsp:include page="WEB-INF/views/HeadForJQuery.jsp" flush="true" />
</head>
<body onload='document.f.j_username.focus();'>
	<div id="container">
		<jsp:include page="WEB-INF/views/Top.jsp" flush="true" />

		<table border="1" width="100%" align="center"
			style="background-color: #55BB55;">
			<tr valign="top">
				<td width="120px">
					<jsp:include page="WEB-INF/views/Menu.jsp" flush="true" />
				</td>
				<td style="background-color: #DDFFDD;">
				    <h2>Welcome to the Dominion Website.</h2>
				    <h3>By logging in, you will be able to play with others online.</h3>
				    <form name='f' action='/GameProjectWebApp/j_spring_security_check' method='POST'>
					    <p>Username: <input type='text' name='j_username' value='' size="40" title="Your email is your username"></p>
					    <p>Password:<input type='password' name='j_password'/></p>
					    <p><input type='checkbox' name='_spring_security_remember_me'/>Remember me on this computer.</p>
					    <p><input name="submit" type="submit" value="Sign in"/></p>
					</form>

					<hr/>
			        <h3>Aren't yet a member of our online community?  Open a new account for free!
			        <a type="add_link" href="/GameProjectWebApp/GameProject/user/create">Create a New Account</a>
			        </h3>
					
				</td>
			</tr>
		</table>
		<jsp:include page="WEB-INF/views/Bottom.jsp" flush="true" />

	</div>

</body>
</html>