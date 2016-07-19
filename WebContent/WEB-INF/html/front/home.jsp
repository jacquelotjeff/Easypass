<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<t:genericuserpage>
    <jsp:attribute name="title">
      Easypass - Accueil
    </jsp:attribute>
    <jsp:body>
      <div class="col-md-6">
        <h1>Easypass</h1>
        <p>
            Easypass vous simplifie la vie. Il vous permet de stocker rapidement l’ensemble de vos mots de passes, 
            de les partager avec d’autres utilisateurs,
            de créer des groupes de travail en ajoutant des mots de passe liés à vos différents groupes.
            Essayez le, c’est gratuit !
        </p>
      </div>
    <div class="col-md-6">
      <!-- Example row of columns -->
      <h2>Inscrivez-vous</h2>
      <p>
        Inscrivez-vous et commencez dès à présents à créer vos différents groupes de travail
        en y attribuant les mots de passes tout en invitant les différents membres de votre équipe.
      </p>
      <p><a class="btn btn-primary" href="inscription" role="button">Inscription</a></p>
    </div>
    </jsp:body>
</t:genericuserpage>