<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ tag import="fr.easypass.servlets.BaseServlet" %>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed"
                data-toggle="collapse" data-target="#navbar"
                aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${BaseServlet.rootPath}">Easypass</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <c:choose>
                <c:when test='${sessionScope.user  != null}'>
                    <form class="navbar-form navbar-right" method="post"
                        action="${BaseServlet.rootPath}/user/logout">
                        <a href="${BaseServlet.rootPath}/utilisateur">${sessionScope.user.getUsername()}</a>
                        <button type="submit" class="btn btn-success">Déconnexion</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form class="navbar-form navbar-right" method="post"
                        action="${BaseServlet.rootPath}/user/login">
                        <div class="form-group">
                            <input type="text" placeholder="E-mail"
                                id="email" name="email"
                                class="form-control">
                        </div>
                        <div class="form-group">
                            <input type="password" id="password"
                                name="password" placeholder="Mot de passe"
                                class="form-control">
                        </div>
                        <button type="submit" class="btn btn-success">Connexion</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
        <!--/.navbar-collapse -->
    </div>
</nav>