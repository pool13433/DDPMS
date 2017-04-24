<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="include/inc_header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-lg-offset-4 col-lg-4">
            <div class="panel panel-ddpms">
                <div class="panel-heading">                    
                    ลงชื่อเข้าใชเงานระบบ
                </div>
                <div class="panel-body">
                    <c:if test="${!empty status}">
                        <div class="alert alert-warning alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <strong>Warning!</strong> ${status}
                        </div>
                    </c:if>
                    <form class="form-horizontal" action="${context}/LoginServlet" method="post">
                        <div class="form-group">
                            <label for="Username" class="col-sm-4 control-label">Username</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="username" name="username" placeholder="Username">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-4 control-label">Password</label>
                            <div class="col-sm-8">
                                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-10">
                                <button type="submit" class="btn btn-success">Login</button>
                                <button type="reset" class="btn btn-default">Reset</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>        
    </div>
</div>
<jsp:include page="include/inc_footer.jsp"/>

