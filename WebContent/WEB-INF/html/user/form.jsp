${not empty param.user.id ? "Modification" : 'Création' } d'un utilisateur
<form action="login" method="POST">
    <div class="form-group">
        <label for=user>Login : </label>
        <input type="text" name="username" class="form-control" placeholder="Login">
    </div>
    <br />
    <button type="submit" class="btn btn-default">Submit</button>
</form>