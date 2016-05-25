<%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ attribute name="title" fragment="true" %>

<!doctype html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
  </head>
  <body>
    <%@ include file="../html/header.jsp" %>
        <div class="jumbotron"></div>
        <%@ include file="../html/flash.jsp" %>
        <jsp:doBody/>
    <%@ include file="../html/footer.jsp" %>
  </body>
</html>