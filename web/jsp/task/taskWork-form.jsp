<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container" style="padding-right: 100px;">    
    <div class="panel panel-ddpms">        
        <div class="panel-heading">Form Task Assignment</div>
        <div class="panel-body">               
            <a href="${context}//TaskWorkListServlet?menu=task_work" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>        
        <form action="${context}/TaskWorkSaveServlet" method="post" class="form-horizontal" style="padding-right: 100px;" >
            <input type="hidden" id="taskaId" name="taskwId" value="${taskWork.taskwId}"/>
            <div class="form-group">
                <label for="taskwDetail" class="col-sm-2 control-label">Detail</label>
                <div class="col-sm-10">                            
                    <input  type="hidden" name="taskwId"  value="${project.projId}">
                    <textarea class="form-control" name="taskwDetail">${taskWork.taskwDetail}</textarea>
                </div>
            </div>
            <div class="form-group">
                <label for="taskaId" class="col-sm-2 control-label">Task Name</label>
                <div class="col-sm-4">
                    <select class="form-control" id="taskaId" name="taskaId" required>
                        <option value="" selected>--เลือก--</option>
                        <c:forEach items="${projectList}" var="project">
                            <optgroup label="${project.projName}">
                                <c:forEach items="${project.taskAssignList}" var="task">
                                    <c:choose>
                                        <c:when test="${taskWork.taskaId == task.taskaId}">
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
                <label for="taskwManday" class="col-sm-2 control-label">Manday (Hour)</label>
                <div class="col-sm-2">                    
                    <c:set var="mandays" value="1,2,3,4,5,6,7,8"/>
                    <select class="form-control" id="taskwManday" name="taskwManday" required>
                        <option value="" selected>--เลือก--</option>
                        <c:forEach items="${mandays}" var="man">
                            <c:choose>
                                <c:when test="${taskWork.taskwManday == man}">
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
                <label for="taskwDate" class="col-sm-2 control-label">Work Date</label>
                <div class="col-sm-3">
                    <input class="form-control datepicker" type="text" name="taskwDate"  value="${taskWork.taskwDate}" readonly>
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
