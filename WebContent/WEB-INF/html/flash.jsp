<jsp:directive.page language="java" pageEncoding="UTF-8" />
<% 
if(request.getAttribute("success") != null){
    %>
	<div class="alert alert-success">
	  <strong>Success!</strong> <% out.write(request.getAttribute("success").toString()); %>
	</div>
     <%
    
} else if (request.getAttribute("errorMessage") != null){
    %>
    <div class="alert alert-danger">
        <strong>Error...</strong> <% out.write(request.getAttribute("errorMessage").toString()); %>
    </div>
    <%
}
%>