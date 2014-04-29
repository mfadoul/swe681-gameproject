<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!-- Matthew Fadoul SWE 681, 03/30/2014 -->

<div id="menu_div" style="width:120px;float:left;">
<ul id="menu">
  <li><a href="/GameProjectWebApp/index.jsp" title="The home page for the playing games"><span class="ui-icon ui-icon-home"></span>Home</a></li>
  <li></li>
  
  <sec:authorize access="isAuthenticated()">
      <li><a href="/GameProjectWebApp/GameProject/user/myProfile" title="My personal profile"><span class="ui-icon ui-icon-info"></span>My Profile</a></li>
      <c:if test="${player eq null}">
            <li><a href="/GameProjectWebApp/GameProject/game/play" title="Open a new table and wait for another player"><span class="ui-icon ui-icon-play"></span>New Game</a></li>
      </c:if>
      
      <c:if test="${player ne null}">
          <li><a href="/GameProjectWebApp/GameProject/game/play" title="Go to current game"><span class="ui-icon ui-icon-play"></span>Current Game</a></li>
          <li><a href="/GameProjectWebApp/GameProject/game/quit" title="Quit current game"><span class="ui-icon ui-icon-quit"></span>Quit Game</a></li>      
      </c:if>
      <li></li>
      
      <li><a href="/GameProjectWebApp/GameProject/game/openGames" title="A list of the players waiting to play"><span class="ui-icon ui-icon-link"></span>Open Games</a></li>
      <li><a href="/GameProjectWebApp/GameProject/user/activeUsers" title="A list of users who are online"><span class="ui-icon ui-icon-note"></span>Players Online</a></li>

   </sec:authorize>
   <sec:authorize access="!isAuthenticated()">
      <li><a href="<c:url value="/login.jsp" />" title="Sign in to my existing account"><span class="ui-icon ui-icon-key"></span>Sign in</a></li>
      <li><a href="<c:url value="/GameProject/user/create"/>"  title="Sign up to create a new user account"><span class="ui-icon ui-icon-plus"></span>Sign up</a></li>
  </sec:authorize>
</ul>
</div>
