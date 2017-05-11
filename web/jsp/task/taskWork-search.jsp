<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">        
        <div class="panel-heading">Manage Task Working</div>
        <div class="panel-body">
            <!-- Alert Message -->
            <c:if test="${!empty MessageUI}">
                <div class="alert alert-${MessageUI.cssClass} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>${MessageUI.title}!</strong> ${MessageUI.message}
                </div>
                <c:remove var="MessageUI" scope="session" />
            </c:if>           

            <!-- Criteria -->
            <form action="${context}/TaskWorkListServlet?menu=task_work" method="get" class="form-horizontal" >                                
                <input type="hidden" name="menu" value="task_work"/>
                <div class="form-group">
                    <label for="taskaId" class="col-sm-3 control-label">Task Assign</label>
                    <div class="col-sm-3">
                        <select class="form-control" id="taskaId" name="taskaId">
                            <option value="" selected>--เลือก--</option>
                            <c:forEach items="${projectList}" var="project">
                                <optgroup label="${project.projName}">
                                    <c:forEach items="${project.taskAssignList}" var="task">
                                        <c:choose>
                                            <c:when test="${criteria.taskaId == task.taskaId}">
                                                <option value="${task.taskaId}" selected>${task.taskName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${task.taskaId}">${task.taskName}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </optgroup>
                            </c:forEach>
                        </select>
                    </div>
                    <label for="taskwManday" class="col-sm-3 control-label">Manday (Hour)</label>
                    <div class="col-sm-3">                    
                        <c:set var="mandays" value="${mandays}"/>
                        <select class="form-control" id="taskwManday" name="taskwManday">
                            <option value="" selected>--เลือก--</option>
                            <c:forEach items="${mandays}" var="man">
                                <c:choose>
                                    <c:when test="${criteria.taskwManday == man}">
                                        <option value="${man}" selected>${man}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${man}">${man}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>      
                <div class="form-group">
                    <label for="taskwMonth" class="col-sm-3 control-label">Work In Month</label>
                    <div class="col-sm-3">
                        <c:set var="months" value="${months}"/>
                        <select class="form-control" id="taskwManday" name="taskwMonth">
                            <option value="" selected>--เลือก--</option>
                            <c:forEach items="${months}" var="month" varStatus="loop">
                                <c:set var="monthIndex" value="${loop.index+1}"/>
                                <c:choose>                                    
                                    <c:when test="${criteria.taskwDate == monthIndex}">                                        
                                        <option value="${monthIndex}" selected>${month}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${monthIndex}">${month}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>   
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-success">Search</button>
                        <a href="${context}/TaskWorkListServlet?menu=task_work" class="btn btn-warning">Reset</a>
                    </div>
                </div>
            </form>
            <!-- Criteria -->

            <a href="${context}/TaskWorkFormServlet" class="btn btn-default btn-primary">
                <i class="glyphicon glyphicon-plus"></i> Add Task
            </a>
            <table id="search_table" class="table table-bordered table-striped">                        
                <tr>
                    <th style="width: 8%">#</th>                                      
                    <th colspan="4">รายละเอียด</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="work" items="${taskWorkingList}">
                        <tr>
                            <td  nowrap rowspan="2">        
                                <a href="${context}/TaskWorkFormServlet?taskwId=${work.taskwId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                <a href="${context}/TaskWorkDeleteServlet?taskwId=${work.taskwId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                            </td>
                            <td colspan="5">${work.taskwDetail}</td>                
                        </tr>
                        <tr>
                            <td>Task: ${work.taskName}</td>
                            <td>Project: ${work.projName}</td>                                                        
                            <td style="text-align: center;">Manday (Hr): ${work.taskwManday}</td>                            
                            <td nowrap>Work Date: ${work.taskwDate}</td>                            
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>    

    </div>        
</div>   

<script type="text/javascript">

    $(document).ready(function () {




    });
</script>

<jsp:include page="../include/inc_footer.jsp"/>
