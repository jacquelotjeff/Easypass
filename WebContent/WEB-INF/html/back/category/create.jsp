<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericadminpage>
    <jsp:attribute name="title">
      Easypass - Ajouter une catégorie
    </jsp:attribute>
    <jsp:body>
        <div class="jumbotron">
            <div class="container">
                <h1>Ajouter une catégorie</h1>
                <jsp:include page="form.jsp"/>
            </div>
        </div>
    </jsp:body>
</t:genericadminpage>