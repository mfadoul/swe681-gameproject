<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Matthew Fadoul SWE 681, 03/30/2014 -->

<div id="footer">
	<table border="2" width="100%" align="center"
		style="background-color: #55BB55;">
		<c:if test="${infoMessage ne null}">
		<tr>
			<td>
				<div class="ui-widget">
					<div class="ui-state-highlight ui-corner-all"
						style="padding: 0 .7em;">
						<p>
							<span class="ui-icon ui-icon-info"
								style="float: left; margin-right: .3em;"></span> <strong>Information:</strong>
							<c:out value="${infoMessage}" />
						</p>
					</div>
				</div>
			</td>
		</tr>
		</c:if>
		<c:if test="${errorMessage ne null}">	
		<tr>
			<td>
				<div class="ui-widget">
					<div class="ui-state-error ui-corner-all" style="padding: 0 .7em;">
						<p>
							<span class="ui-icon ui-icon-alert"
								style="float: left; margin-right: .3em;"></span> <strong>Alert:</strong>
							<c:out value="${errorMessage}" />
						</p>
					</div>
				</div>
			</td>
		</tr>
		</c:if>
		<tr>
			<td>
			    <a type="mailto_link" href="mailto:info@gameproject.com" title="We welcome any feedback">Website questions/feedback</a>
			    | <span> Copyright 2014, Dominion Game Project, LLC </span>
			</td>
		</tr>
	</table>
</div>