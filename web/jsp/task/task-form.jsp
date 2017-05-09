<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">                
        <div class="container-fluid text-center"><h4>Form Task</h4></div>
        <div class="panel-body">
            <a href="${context}/TaskListServlet?menu=task" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/TaskSaveServlet" method="post" class="form-horizontal" >
            <input type="hidden" name="taskId" value="${task.taskId}"/>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="taskName" class="col-sm-2 control-label">Name</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="taskName" value="${task.taskName}"/>
                        </div>
                    </div>
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
<jsp:include page="../include/inc_footer.jsp"/>
