<li href="#" class="list-group-item text-left">
    <img class="img-thumbnail" src="http://bootdey.com/img/Content/User_for_snippets.png">
    <label class="name">${user.getUsername()}<br></label> 
    <label class="pull-right"> 
        <c:url value="admin-utilisateur" var="adminUserURL">
            <c:param name="userId" value="${user.getId()}" />
            <c:param name="groupId" value="${group.getId()}" />
            <c:param name="admin" value="true" />
        </c:url>
        <form action="${adminUserURL}" method="POST">
            <button type="submit" class="btn btn-success btn-sm" title="Passer en admin">
                <i class="fa fa-user-secret" aria-hidden="true"></i>
            </button>
        </form>
        <c:url value="supprimer-utilisateur" var="deleteURL">
            <c:param name="userId" value="${user.getId()}" />
            <c:param name="groupId" value="${group.getId()}" />
        </c:url>
        <form action="${deleteURL}" method="POST">
            <button type="submit" class="btn btn-danger btn-sm">
                <i class="fa fa-minus" aria-hidden="true"></i>
            </button>
        </form>
    </label>
    <div class="break"></div>
</li>