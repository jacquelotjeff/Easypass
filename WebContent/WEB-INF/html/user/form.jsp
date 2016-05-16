<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<form action="login" method="POST">
    <div class="form-group">
        <label for="username">Nom d'utilisateur : </label>
        <input type="text" name="username" class="form-control" value='<c:out value="${user.getUsername()}"/>'>
        <label for="lastname">Nom : </label>
        <input type="text" name="lastname" class="form-control" value="">
        <label for="firstname">Prenom : </label>
        <input type="text" name="firstname" class="form-control" value="">
        <label for="email">Email : </label>
        <input type="text" name="email" class="form-control" value="">
        <label for="password">Mot de passe : </label>
        <input type="password" name="password" class="form-control" value="">
    </div>
    <br />
    <button type="submit" class="btn btn-default">Enregistrer</button>
</form>