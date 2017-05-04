<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-ddpms">
                <div class="panel-heading">                    
                    Change Password
                </div>
                <div class="panel-body">

                    <!-- Alert Message -->
                    <c:if test="${!empty MessageUI}">
                        <div class="alert alert-${MessageUI.cssClass} alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <strong>${MessageUI.title}!</strong> ${MessageUI.message}
                        </div>
                        <c:remove var="MessageUI" scope="session" />
                    </c:if>            
                    <!-- Alert Message -->

                    <form class="form-horizontal" name="formChangePassword" action="${context}/ChangePasswordServlet" method="post">
                        <div class="form-group">
                            <label for="passwordOld" class="col-sm-2 control-label">Old Password</label>
                            <div class="col-sm-4">                                
                                <input type="password" class="form-control" id="passwordOld" name="passwordOld" placeholder="Old Password" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="passwordNew" class="col-sm-2 control-label">New Password</label>
                            <div class="col-sm-4">
                                <input type="password" class="form-control" id="passwordNew" name="passwordNew" placeholder="New Password" required>
                            </div>
                            <label for="passwordNewConfirm" class="col-sm-2 control-label">Confirm New Password</label>
                            <div class="col-sm-4">
                                <input type="password" class="form-control" id="passwordNew" name="passwordNewConfirm" placeholder="Confirm New Password" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-success">Change Password</button>
                                <a href="${context}/jsp/authen/password.jsp" class="btn btn-warning">Reset</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>        
    </div>
</div>
<jsp:include page="../include/inc_footer.jsp"/>

