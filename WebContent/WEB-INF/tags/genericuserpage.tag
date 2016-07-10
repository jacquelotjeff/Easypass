<%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ attribute name="title" fragment="true" %>
<%@ attribute name="scripts" fragment="true" %>

<!doctype html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>
        <jsp:invoke fragment="title"/>
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel='stylesheet' href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-select.min.css">
    <link rel='stylesheet' href="${pageContext.request.contextPath}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  </head>
  <body>
    <%@ include file="/WEB-INF/html/header.jsp" %>
    <%@ include file="/WEB-INF/html/flash.jsp" %>
    <div class="container">
        <c:if test="${sessionScope.user != null}">
            <jsp:include page="/WEB-INF/html/front/userNavbar.jsp" />                    
        </c:if>
        <div class="col-sm-12">
            <jsp:doBody/>
        </div>
    </div>
    <%@ include file="/WEB-INF/html/footer.jsp" %>
    <script src="${pageContext.request.contextPath}/js/jquery-2.2.4.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap-select.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/i18n/defaults-fr_FR.js"></script>
    <jsp:invoke fragment="scripts"/>
  </body>
</html>