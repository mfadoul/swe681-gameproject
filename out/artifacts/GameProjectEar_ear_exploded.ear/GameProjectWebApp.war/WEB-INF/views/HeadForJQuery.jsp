<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Matthew Fadoul SWE 681, 03/30/2014 -->

<!-- Header Information needed for JQuery UI -->

<link href="/GameProjectWebApp/jquery-ui/css/south-street/jquery-ui-1.10.2.custom.css"
	rel="stylesheet" />
<script src="/GameProjectWebApp/jquery-ui/js/jquery-1.9.1.js"></script>
<script src="/GameProjectWebApp/jquery-ui/js/jquery-ui-1.10.2.custom.js"></script>
<script>
    $(function() {
//         $( document ).tooltip();
        $( "input" ).tooltip();
        $( "textarea" ).tooltip();
        $( "select" ).tooltip();
        $( "a" ).tooltip();
    });
	$(function() {
		$("#tabs").tabs();
	});
	$(function() {
		$("#menu").menu();
	});
    $(function() {
	    $( "a[type=login_link]" ).button({
	        icons: { primary: "ui-icon-key"}
	    });
	    $( "a[type=logout_link]" ).button({
	        icons: { primary: "ui-icon-key"}
	    });
	    $( "a[type=detailed_info_link]" ).button({
	        icons: { primary: "ui-icon-info"}
	    });
	    $( "a[type=user_info_link]" ).button({
	        icons: { primary: "ui-icon-contact"}
	    });
	    $( "a[type=edit_link]" ).button({
	        icons: { primary: "ui-icon-pencil"}
	    });
	    $( "a[type=add_link]" ).button({
	        icons: { primary: "ui-icon-plus"}
	    });
	    $( "a[type=external_link]" ).button({
	        icons: { secondary: "ui-icon-extlink"}
	    });
	    $( "a[type=cancel_link]" ).button({
	        icons: { primary: "ui-icon-cancel"}
	    });
	    $( "input#search_button" ).button({
	        icons: { primary: "ui-icon-search"}
	    });
	    $( "input[type=submit]" ).button({
	    });
	    $( "input[type=reset]" ).button({
	    });
	    $( "a[type=mailto_link]" ).button({
	        icons: { primary: "ui-icon-mail-closed"}
	    });
	});
</script>
<style>
body {
	font: 62.5% "Trebuchet MS", sans-serif;
	margin: 50px;
}

.demoHeaders {
	margin-top: 2em;
}

#dialog-link {
	padding: .4em 1em .4em 20px;
	text-decoration: none;
	position: relative;
}

#dialog-link span.ui-icon {
	margin: 0 5px 0 0;
	position: absolute;
	left: .2em;
	top: 50%;
	margin-top: -8px;
}

#icons {
	margin: 0;
	padding: 0;
}

#icons li {
	margin: 2px;
	position: relative;
	padding: 4px 0;
	cursor: pointer;
	float: left;
	list-style: none;
}

#icons span.ui-icon {
	float: left;
	margin: 0 4px;
}

.fakewindowcontain .ui-widget-overlay {
	position: absolute;
}

.ui-menu {
	width: 115px;
}
</style>