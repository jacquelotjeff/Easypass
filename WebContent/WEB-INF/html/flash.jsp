<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- Display flash messages if stored in user sessionScope, then remove from session -->
<c:if test="${not empty sessionScope.alertClass and not empty sessionScope.alertMessage}">
    <div class="alert ${sessionScope.alertClass}">
      <strong>Message : </strong><c:out value="${sessionScope.alertMessage}"></c:out>
      <c:if test="${not empty sessionScope.alertMessages}">
        <ul>
            <c:forEach var="message" items="${sessionScope.alertMessages}">
                <li>${message}</li>
            </c:forEach>
        </ul>
      </c:if>
    </div>
    <c:remove var="alertClass" scope="session" />
    <c:remove var="alertMessage" scope="session" />
    <c:remove var="alertMessages" scope="session" />
</c:if>