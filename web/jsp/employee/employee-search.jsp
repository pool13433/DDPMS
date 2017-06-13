<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">        
        <div class="container-fluid text-center"><h4>Manage Employee</h4></div>
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
            <form id="searchProj" method="get" action="${context}/EmployeeListServlet" class="form-horizontal">          
                <input type="hidden" id="menu" name="menu" value="employee"/>
                <div class="container">
                    <div class="form-group">
                        <label for="code" class="col-sm-2 control-label">Code</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control"  name="empCode" value="${criteria.empCode}"/>
                        </div>
                    </div>  
                    <div class="form-group">
                        <label for="firstname" class="col-sm-2 control-label">FirstName</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control"  name="fname" value="${criteria.empFname}"/>
                        </div>
                        <label for="lastname" class="col-sm-2 col-sm-offset-1 control-label">LastName</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control"  name="lname" value="${criteria.empLname}"/>
                        </div>
                    </div>  
                    <div class="form-group">
                        <label for="mobile" class="col-sm-2 control-label">Mobile</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control"  name="mobile" value="${criteria.empMobile}"/>
                        </div>
                        <label for="email" class="col-sm-2 col-sm-offset-1 control-label">Email</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control"  name="email" value="${criteria.empEmail}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="gender" class="col-sm-2 control-label">Gender</label>
                        <div class="col-sm-3">
                            <select class="form-control" name="gender">
                                <option value="" selected>--เลือก--</option>
                                <c:forEach var="gend" items="${genderList}">
                                    <c:choose>
                                        <c:when test="${criteria.gender == gend.confName}">
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
                            <select class="form-control" name="department">
                                 <option value="" selected>--เลือก--</option>
                                <c:forEach var="dep" items="${departmentList}">
                                    <c:choose>
                                        <c:when test="${criteria.depId == dep.depId}">
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
                            <select class="form-control" name="status">
                                 <option value="" selected>--เลือก--</option>
                                <c:forEach var="status" items="${employeeStatusList}">                            
                                    <c:choose>
                                        <c:when test="${criteria.status == status.confName}">
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
                        <div class="col-sm-offset-6">
                            <button type="submit" class="btn btn-success">
                                <i class="glyphicon glyphicon-search"></i> Search
                            </button>
                            <a href="${context}/EmployeeListServlet?menu=employee" class="btn btn-warning">
                                <i class="glyphicon glyphicon-erase"></i> Reset
                            </a>
                            <a href="${context}/EmployeeFormServlet" class="btn btn-default btn-primary">
                                <i class="glyphicon glyphicon-plus"></i> Add
                            </a>

                        </div>
                    </div>
                </div>
            </form>
            <div class="row">
                <div class="col-md-12">
                    <c:import url="../include/inc_pagination.jsp"/>
                </div>
            </div>     
            <div id="msgBox" class="alert alert-warning" hidden="">
                <strong>Warning! </strong><text id="msg" name="msg" value=""></text>
            </div>
            <form id="budpList" action="${context}/PlanSearchServlet"   method="post" class="form-horizontal">
                <input type="hidden" id="menu" name="menu" value="manage"/>
                <table id="search_table" class="table table-responsive">                        
                    <tr>
                        <th>#</th>                        
                        <th>Code</th> 
                        <th>Name</th> 
                        <th>Email</th> 
                        <th>Mobile</th> 
                        <th>Gender</th> 
                        <th>Department</th> 
                        <th>Status</th> 
                        <th>Modified By</th>
                        <th>Modified Date</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="emp" items="${employeeList}">
                            <tr>
                                <td  nowrap>        
                                    <a href="${context}/EmployeeFormServlet?empId=${emp.empId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                    <a href="${context}/EmployeeDeleteServlet?empId=${emp.empId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                                </td>
                                <td>${emp.empCode}</td>
                                <td>${emp.empFname} ${emp.empLname}</td>
                                <td>${emp.empEmail}</td>
                                <td>${emp.empMobile}</td>
                                <td>${emp.gender}</td>
                                <td>${emp.depName}</td>
                                <td>${emp.status}</td>
                                <td>${emp.modifiedBy}</td> 
                                <td>${emp.modifiedDate}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>          
                <div class="row">
                    <div class="col-md-12">
                        <c:import url="../include/inc_pagination.jsp"/>
                    </div>
                </div>     
            </form>

        </div>        
    </div>        
</div>   
<jsp:include page="../include/inc_footer.jsp"/>
