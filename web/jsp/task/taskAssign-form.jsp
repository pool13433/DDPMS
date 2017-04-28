<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container" style="padding-right: 100px;">    
    <div class="panel panel-ddpms">        
        <div class="panel-heading">Form Task Assignment</div>
        <div class="panel-body">               
            <a href="${context}/TaskAssignListServlet?menu=task_assign" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>        
        <form action="${context}/TaskAssignSaveServlet" method="post" class="form-horizontal" style="padding-right: 100px;" >
            <input type="hidden" id="taskaId" name="taskaId" value="${taskAssign.taskaId}"/>
            <div class="form-group">
                <label for="proj_name" class="col-sm-2 control-label">Project Name</label>
                <div class="col-sm-8">                            
                    <input  type="hidden" name="projId"  value="${project.projId}">
                    <input class="form-control" type="text" name="projName"  value="${project.projName}" readonly>
                </div>
            </div>
            <div class="form-group">
                <label for="proj_details" class="col-sm-2 control-label">Task Name</label>
                <div class="col-sm-3">
                    <select class="form-control" id="task_id" name="taskId" required>
                        <option value="" selected>--เลือก--</option>
                        <c:forEach items="${taskList}" var="task">
                            <c:choose>
                                <c:when test="${taskAssign.taskId == task.taskId}">
                                    <option value="${task.taskId}" selected>${task.taskName}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${task.taskId}">${task.taskName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
                <label for="proj_status" class="col-sm-2 control-label">Staff</label>
                <div class="col-sm-3">
                    <select class="form-control" id="empId" name="empId" required>
                        <option value="" selected>--เลือก--</option>
                        <c:forEach items="${employeeList}" var="employee">
                            <c:choose>
                                <c:when test="${taskAssign.taskUserId == employee.empId}">
                                    <option value="${employee.empId}" selected>${employee.username}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${employee.empId}">${employee.username}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>      
                <div class="form-group">
                <label for="proj_details" class="col-sm-2 control-label">Assign Date</label>
                <div class="col-sm-3">
                    <input class="form-control datepicker" type="text" name="taskaAssignDate"  value="${taskAssign.taskaAssignDate}" readonly>
                </div>
                <label for="proj_status" class="col-sm-2 control-label">Target Date</label>
                <div class="col-sm-3">
                    <input class="form-control datepicker" type="text" name="taskaTargetDate"  value="${taskAssign.taskaTargetDate}" readonly>
                </div>
            </div>   
            <div class="form-group">
                <div class="col-sm-offset-1 col-sm-10">
                    <button type="submit" class="btn btn-success">Save</button>
                    <button type="reset" class="btn btn-warning">Reset</button>
                </div>
            </div>
        </form>

    </div>        
</div>   

<script type="text/javascript">

    $(document).ready(function () {




    });
</script>

<jsp:include page="../include/inc_footer.jsp"/>
