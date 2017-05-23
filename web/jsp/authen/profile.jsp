<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <div class="panel panel-ddpms">
                <div class="panel-heading">                    
                    Change My Profile
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

                    <form class="form-horizontal" name="formChangeProfile" action="${context}/ChangeProfileServlet" method="post">
                        <div class="form-group">
                            <label for="empCode" class="col-sm-2 control-label">Code</label>
                            <div class="col-sm-4">                                
                                <input type="text" class="form-control" name="empCode" value="${profile.empCode}" placeholder="Employee Code" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="empFname" class="col-sm-2 control-label">FirstName</label>
                            <div class="col-sm-4">                                
                                <input type="text" class="form-control" value="${profile.empFname}"  name="empFname" placeholder="Employee FirstName" required>
                            </div>
                            <label for="empLname" class="col-sm-2 control-label">LastName</label>
                            <div class="col-sm-4">                                
                                <input type="text" class="form-control" value="${profile.empLname}" name="empLname" placeholder="Employee LastName" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="empEmail" class="col-sm-2 control-label">Email</label>
                            <div class="col-sm-4">                                
                                <input type="text" class="form-control" value="${profile.empEmail}" name="empEmail" placeholder="Employee Email" required>
                            </div>
                            <label for="empMobile" class="col-sm-2 control-label">Mobile</label>
                            <div class="col-sm-4">                                
                                <input type="text" class="form-control" value="${profile.empMobile}"  name="empMobile" placeholder="Employee Mobile" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="empGender" class="col-sm-2 control-label">Gender</label>
                            <div class="col-sm-2">                                                                
                                <select class="form-control" name="empGender" required>
                                    <option value="" selected>--เลือก--</option>
                                    <c:forEach items="${genderList}" var="gender">
                                        <c:choose>
                                            <c:when test="${profile.gender == gender.key}">
                                                <option value="${gender.key}" selected>${gender.value}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${gender.key}">${gender.value}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                            <label for="empTitle" class="col-sm-2 col-sm-offset-2 control-label">Title</label>
                            <div class="col-sm-4">                                
                                <input type="text" class="form-control" value="${profile.title}" name="empTitle" placeholder="Employee Title" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="empDepartment" class="col-sm-2 control-label">Department</label>
                            <div class="col-sm-4">                                
                                <select class="form-control" id="taskaId" name="depId" required>
                                    <option value="" selected>--เลือก--</option>
                                    <c:forEach items="${departmentList}" var="dep">
                                        <c:choose>
                                            <c:when test="${profile.depId == dep.depId}">
                                                <option value="${dep.depId}" selected>${dep.depName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${dep.depId}">${dep.depName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-success">Change Profile</button>
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

