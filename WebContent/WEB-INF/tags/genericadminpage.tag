<%@ tag description="Overall Admin Page template" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ attribute name="title" fragment="true" %>
<t:genericpage>
    <jsp:attribute name="title">
        <jsp:invoke fragment="title"/>
    </jsp:attribute>
    <jsp:body>
     <div class="container-fluid">
      <div class="row">
        <%@ include file="../html/adminSidebar.jsp" %>
        <div class="col-sm-9 col-md-10">
            <jsp:doBody/>
        </div>
      </div>
    </div>
    </jsp:body>
</t:genericpage>