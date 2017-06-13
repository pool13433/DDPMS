<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">                
        <div class="container-fluid text-center"><h4>Form Plan</h4></div>
        <div class="panel-body">
            <a href="${context}/EmployeeListServlet?menu=employee" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/EmployeeSaveServlet" method="post" class="form-horizontal">
            <input type="hidden" name="empId" value="${employee.empId}"/>
            <c:if test="${empty employee.empId}">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">Username</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="username" value="${employee.username}" required/>
                    </div>
                    <label for="password" class="col-sm-2 col-sm-offset-1 control-label">Password</label>
                    <div class="col-sm-3">
                        <input type="password" class="form-control"  name="password" value="${employee.password}" required/>
                    </div>
                </div>  
            </c:if>
            <div class="form-group">
                <label for="code" class="col-sm-2 control-label">Code</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control"  name="empCode" value="${employee.empCode}" required/>
                </div>
            </div>  
            <div class="form-group">
                <label for="firstname" class="col-sm-2 control-label">FirstName</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control"  name="fname" value="${employee.empFname}" required/>
                </div>
                <label for="lastname" class="col-sm-2 control-label">LastName</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control"  name="lname" value="${employee.empLname}" required/>
                </div>
            </div>  
            <div class="form-group">
                <label for="mobile" class="col-sm-2 control-label">Mobile</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control"  name="mobile" value="${employee.empMobile}" required/>
                </div>
                <label for="email" class="col-sm-2 col-sm-offset-1 control-label">Email</label>
                <div class="col-sm-3">
                    <input type="email" class="form-control"  name="email" value="${employee.empEmail}"  required/>
                </div>
            </div>
            <div class="form-group">
                <label for="gender" class="col-sm-2 control-label">Gender</label>
                <div class="col-sm-3">
                    <select class="form-control" name="gender" required>
                        <c:forEach var="gend" items="${genderList}">
                            <c:choose>
                                <c:when test="${employee.gender == gend.confName}">
                                    <option value="${gend.confName}" selected>${gend.confValue}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${gend.confName}">${gend.confValue}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
                <label for="department" class="col-sm-2 col-sm-offset-1 control-label">Department</label>
                <div class="col-sm-3">
                    <select class="form-control" name="department" required>
                        <c:forEach var="dep" items="${departmentList}">
                            <c:choose>
                                <c:when test="${employee.depId == dep.depId}">
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
                <label for="status" class="col-sm-2 control-label">Status</label>
                <div class="col-sm-3">
                    <select class="form-control" name="status" required>
                        <c:forEach var="status" items="${employeeStatusList}">                            
                            <c:choose>
                                <c:when test="${employee.status == status.confName}">
                                    <option value="${status.confName}" selected>${status.confValue}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${status.confName}">${status.confValue}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-success">Save</button>
                    <button type="reset" class="btn btn-warning">Reset</button>
                </div>
            </div>
        </form>
    </div>
</div>
<jsp:include page="../include/inc_footer.jsp"/>
