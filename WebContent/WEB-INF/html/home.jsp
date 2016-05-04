<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
    <jsp:attribute name="title">
      Easypass - Accueil
    </jsp:attribute>
    <jsp:attribute name="header">
      <jsp:directive.include file="header.jsp" />
    </jsp:attribute>
    <jsp:attribute name="footer">
      <jsp:directive.include file="footer.jsp" />
    </jsp:attribute>
    <jsp:body>
        Bienvenue ${currentUser.getFirstname()}
        <p>Page d'accueil</p>
    </jsp:body>
</t:genericpage>