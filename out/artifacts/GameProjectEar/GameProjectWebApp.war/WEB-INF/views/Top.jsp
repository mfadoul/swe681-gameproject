<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- Matthew Fadoul SWE 681, 03/30/2014 -->

<div id="header">
<table border="1" width="100%" align="center"
	style="background-color: #55BB55;">
	<tr>
		<td align="center" width="120px">
			<div>
				<a class="logo_link" href="/GameProjectWebApp/index.jsp" title="The Dominion Game Home Page"> <img
					class="logo" height="100" width="100"
					src="/GameProjectWebApp/images/dominion_game.jpg" alt="Dominion Logo" />
				</a>
			</div>

		</td>
		<td>
			<h1 class="title">Dominion Game Website</h1>
			<!--  
			<form action="/GameProjectWebApp/GameProject/book/search" 
			    name="search_form" id="searchForm" method="post">
			    <div>
			    	<input type="text" id="searchString" name="searchString" size="40" title="Enter your search string here" />
			    	<select id="searchType" name="searchType" title="Select what you would like to search by">
						<option value="title" selected="selected">Title</option>
						<option value="author">Author</option>
						<option value="description">Description</option>
					</select>
			    	<input id="search_button" type="submit" value="Search" title="Click to execute your search" />
                </div>
			</form>
			-->
		</td>
		<td align="center" width="100px">
			<sec:authorize access="isAuthenticated()">
					<h3>Signed in as:</h3>
						<p>
						<span style="color: navy; font-style: oblique">
					    <c:if test="${loggedInUser ne null}">
								<c:out value="${loggedInUser.firstname}" />
								<c:out value="${loggedInUser.lastname}" /> (<c:out
									value="${loggedInUser.email}" />)
					    </c:if>
						<c:if test="${loggedInUser eq null}">
								<sec:authentication property="principal.username" />
						</c:if>
						</span>
						</p>
						<p> 
						<a type="logout_link" href="<c:url value="/j_spring_security_logout" />" title="Sign out of your account">
						  Sign out</a>
						  <!--  <img src="/GameProjectWebApp/images/logout_icon.gif" alt="logout"/> 
						</a> -->
						</p>
			</sec:authorize>
			<sec:authorize access="!isAuthenticated()">
				<h3>You are not signed in.</h3>
					<a type="login_link" href="<c:url value="/login.jsp" />" title="Sign in to my existing account">Sign in</a>
				<p>
				</p>
			</sec:authorize>
			
		</td>
	</tr>
</table>
</div>