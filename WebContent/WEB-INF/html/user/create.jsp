<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
    <jsp:attribute name="title">
      Easypass - Création d'un utilisateur
    </jsp:attribute>
    <jsp:body>
        Création d'un utilisateur
        <jsp:directive.include file="form.jsp" />
    </jsp:body>
</t:genericpage>