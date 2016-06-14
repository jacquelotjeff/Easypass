<li href="#" class="list-group-item text-left">
    <img class="img-thumbnail" src="http://bootdey.com/img/Content/User_for_snippets.png">
    <label class="name">${user.getUsername()} (Admin)<br></label> 
    <label class="pull-right"> 
        <c:url value="admin-utilisateur" var="adminUserURL">
            <c:param name="userId" value="${user.getId()}" />
            <c:param name="groupId" value="${group.getId()}" />
            <c:param name="admin" value="false" />
        </c:url>
        <form action="${adminUserURL}" method="POST">
            <button type="submit" class="btn btn-success btn-sm" title="Rétrograder en simple membre.">
                <i class="fa fa-chain-broken" aria-hidden="true"></i>
            </button>
        </form>
        <!-- Remove user from group --> 
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