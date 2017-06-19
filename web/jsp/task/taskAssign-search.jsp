<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">        
        <div class="container-fluid text-center"><h4>Manage Task Assignment</h4></div>
        <div class="panel-body">
            <!-- Alert Message -->
            <c:if test="${!empty MessageUI}">
                <div class="alert alert-${MessageUI.cssClass} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>${MessageUI.title}!</strong> ${MessageUI.message}
                </div>
                <c:remove var="MessageUI" scope="session" />
            </c:if>            


            <form class="form-horizontal" action="${context}/TaskAssignListServlet" method="get">
                <div class="form-group">
                    <label for="BudgetPlan" class="col-sm-2 control-label">BudgetPlan</label>
                    <div class="col-sm-4">
                        <select class="form-control" id="budpId" name="budpId" required>
                            <option value="" selected>--เลือก--</option>
                            <c:forEach items="${budgetPlanList}" var="budgetPlan">
                                <c:choose>
                                    <c:when test="${criteria.budpId == budgetPlan.budpId}">
                                        <option value="${budgetPlan.budpId}" selected>${budgetPlan.budpName}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${budgetPlan.budpId}">${budgetPlan.budpName}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                    <label for="plan" class="col-sm-1 control-label">Plan</label>
                    <div class="col-sm-5">
                        <select class="form-control" id="planId" name="planId">
                            <option value="" selected>--เลือก--</option>
                            <c:forEach items="${planList}" var="plan">
                                <c:choose>
                                    <c:when test="${criteria.planId == plan.planId}">
                                        <option value="${plan.planId}" selected>${plan.planName}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${plan.planId}">${plan.planName}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-info">Search</button>
                        <a href="${context}/TaskAssignListServlet?menu=task_assign" class="btn btn-warning">Reset</a>
                    </div>
                </div>
            </form>

            <c:if test="${empty projectList}">
                <div class="alert alert-info alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>ไม่พบข้อมูล!</strong> ไม่พบข้อมูลหลังจากทำการค้นหา
                </div>
            </c:if>

            <c:forEach items="${projectList}" var="project">                
                <div class="panel panel-ddpms">
                    <div class="panel-heading">
                        <a href="${context}/TaskAssignFormServlet?projId=${project.projId}" class="btn btn-default btn-primary">
                            <i class="glyphicon glyphicon-plus"></i> Assign Task
                        </a>&nbsp ${project.projName} [วันที่สร้างโครงการ : ${project.modifiedDate}]
                    </div>
                    <div class="panel-body">
                        <table id="search_table" class="table table-bordered table-striped">                        
                            <tr>
                                <th style="width: 8%">#</th>
                                <th>Task Name</th>                                 
                                <th>Employee</th>
                                <th>Assign Date</th>
                                <th>Target Date</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="assign" items="${project.taskAssignList}">
                                    <tr>
                                        <td  nowrap>        
                                            <a href="${context}/TaskAssignFormServlet?taskaId=${assign.taskaId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                            <a href="${context}/TaskAssignDeleteServlet?taskaId=${assign.taskaId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                                        </td>
                                        <td>${assign.taskName}</td>
                                        <td>${assign.taskUsername}</td>
                                        <td>${assign.taskaAssignDate}</td>
                                        <td>${assign.taskaTargetDate}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>       
            </c:forEach>


        </div>        
    </div>        
</div>   

<script type="text/javascript">

    $(document).ready(function () {




    });
</script>

<jsp:include page="../include/inc_footer.jsp"/>
