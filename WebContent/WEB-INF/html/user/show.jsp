<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
    <jsp:attribute name="title">
      Easypass - Consulter le profil de {$user->getUsername()}
    </jsp:attribute>
    <jsp:body> 
        Affichage d'un profil utilisateur 
    </jsp:body>
</t:genericpage>