<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage>
    <jsp:attribute name="title">
      Easypass - Login
    </jsp:attribute>
    <jsp:attribute name="header">
      <jsp:directive.include file="header.jsp" />
    </jsp:attribute>
    <jsp:attribute name="footer">
      <jsp:directive.include file="footer.jsp" />
    </jsp:attribute>
    <jsp:body>
        ${errorMessage}
        Veuillez vous connecter
        <form action="login" method="POST">
            <div class="form-group">
                <label for=user>Login : </label>
                <input type="text" name="username" class="form-control" placeholder="Login">
            </div>
            <br />
            <div class="form-group">
                <label for=password>Password : </label>
                <input type="password" name="password" class="form-control" placeholder="assword">
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
        </form>
    </jsp:body>
</t:genericpage>