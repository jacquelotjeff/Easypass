<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="title" fragment="true" %>
<!doctype html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
    <jsp:invoke fragment="header"/>
    <div id="body">
      <jsp:doBody/>
    </div>
    <jsp:invoke fragment="footer"/>
  </body>
</html>