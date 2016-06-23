<%@ tag import="fr.easypass.servlets.BaseServlet" %>

<div class="col-sm-3 col-md-2 sidebar">
  <ul class="nav nav-sidebar">
    <li class="active"><a href="${BaseServlet.rootPath}/admin">Overview <span class="sr-only">(current)</span></a></li>
    <li><a href="${BaseServlet.rootPath}/admin/utilisateurs">Utilisateurs</a></li>
    <li><a href="${BaseServlet.rootPath}/admin/groupes">Groupes</a></li>
    <li><a href="${BaseServlet.rootPath}/admin/categories">Catégories</a></li>
    <li><a href="#">Mot de passes</a></li>
  </ul>
</div>