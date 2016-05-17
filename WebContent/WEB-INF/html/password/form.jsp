<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form action="login" method="POST">
    <div class="form-group">
        <label for="title">Titre pour le mot  de passe : </label>
        <input type="text" name="username" class="form-control" value='<c:out value="${password.getPassword()}"/>'>
        <label for="site">Site du mot de passe : </label>
        <input type="text" name="site" class="form-control" value="">
        <label for="password">Mot de passe : </label>
        <input type="text" name="password" class="form-control" value="">
    </div>
    <br />
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>