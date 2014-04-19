<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- Matthew Fadoul SWE 681, 03/30/2014 -->

<div id="menu_div" style="width:120px;float:left;">
<ul id="menu">
  <li><a href="/GameProjectWebApp/index.jsp" title="The home page for the playing games"><span class="ui-icon ui-icon-home"></span>Home</a></li>
  <li></li>
  
  <sec:authorize access="isAuthenticated()">
      <li><a href="/GameProjectWebApp/GameProject/user/myProfile" title="My personal profile"><span class="ui-icon ui-icon-info"></span>My Profile</a></li>
      <li><a href="/GameProjectWebApp/GameProject/game/newgame" title="Open a new table and wait for another player"><span class="ui-icon ui-icon-actionVm"></span>New Game</a></li>
      <li><a href="/GameProjectWebApp/GameProject/game/openGames" title="A list of the players waiting to action"><span class="ui-icon ui-icon-link"></span>Open Games</a></li>
      <li><a href="/GameProjectWebApp/GameProject/game/gamesInProgress" title="A list of the games in progress"><span class="ui-icon ui-icon-note"></span>Games in Progress</a></li>
      
<!--       
      <li><a href="/GameProjectWebApp/GameProject/user/myBooks" title="A list of the books that I own"><span class="ui-icon ui-icon-note"></span>My Books</a></li>
      <li><a href="/GameProjectWebApp/GameProject/user/myReviews" title="A list of the books I have reviewed"><span class="ui-icon ui-icon-tag"></span>My Reviews</a></li>
      <li></li>
 -->
   </sec:authorize>
<!--   
  <li><a href="/GameProjectWebApp/BookRatingSystem/book/topRated" title="View the highest-rated books"><span class="ui-icon ui-icon-star"></span>Top Rated Books</a></li>
  <li><a href="/GameProjectWebApp/BookRatingSystem/book/mostRated"title="View the books with the most ratings"><span class="ui-icon ui-icon-comment"></span>Most Rated</a></li>
  <li><a href="/GameProjectWebApp/BookRatingSystem/book/list" title="View all of the books"><span class="ui-icon ui-icon-note"></span>All Books</a></li>
  <sec:authorize access="isAuthenticated()">
      <li></li>
      <li><a href="/GameProjectWebApp/BookRatingSystem/book/add" title="Add a book to the BRS database"><span class="ui-icon ui-icon-plus" ></span>Add a Book</a></li>
      <li></li>
      <li><a href="<c:url value="/j_spring_security_logout" />" title="Sign out to end my session on BRS"><span class="ui-icon ui-icon-power"></span>Sign out</a></li>
  </sec:authorize>
 -->
   <sec:authorize access="!isAuthenticated()">
      <li><a href="<c:url value="/login.jsp" />" title="Sign in to my existing account"><span class="ui-icon ui-icon-key"></span>Sign in</a></li>
      <li><a href="<c:url value="/GameProject/user/create"/>"  title="Sign up to create a new user account"><span class="ui-icon ui-icon-plus"></span>Sign up</a></li>
  </sec:authorize>
</ul>
</div>
<!-- 

Account
 My Profile
 My Books
 My Reviews
 Logout
Books
 Add a Book
Search
 General Search
 Top Rated
 Most Rated

 -->