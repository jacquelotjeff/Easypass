<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericpage>
    <jsp:attribute name="title">
      Easypass - Inscription
    </jsp:attribute>
    <jsp:body>
        <div class="container">
            <div class="panel-heading">
               <div class="panel-title text-center">
                    <h1 class="title">Inscription</h1>
                </div>
            </div> 
            <jsp:include page="form-signup.jsp"/>
        </div>
    </jsp:body>
</t:genericpage>