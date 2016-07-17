<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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
            <a class="navbar-brand" href="${pageContext.request.contextPath}">Easypass</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <c:choose>
                <c:when test='${sessionScope.user  != null}'>
                    <form id="form-logout" class="navbar-form navbar-right" method="post"
                        action="${pageContext.request.contextPath}/user/logout">
                        <a href="${pageContext.request.contextPath}/utilisateur">${sessionScope.user.getUsername()}</a>
                        <button id="btn-logout" type="submit" class="btn btn-success">Déconnexion</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form id="form-sign-in" class="navbar-form navbar-right" method="post"
                        action="${pageContext.request.contextPath}/user/login">
                        <div class="form-group">
                            <input id="email" type="text" placeholder="E-mail"
                                id="email" name="email"
                                class="form-control">
                        </div>
                        <div class="form-group">
                            <input id="password" type="password" id="password"
                                name="password" placeholder="Mot de passe"
                                class="form-control">
                        </div>
                        <button id="btn-sign-in" type="submit" class="btn btn-success">Connexion</button>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
        <!--/.navbar-collapse -->
    </div>
</nav>