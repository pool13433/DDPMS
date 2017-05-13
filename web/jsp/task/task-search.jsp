<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">                
        <div class="container-fluid text-center"><h4>Manage Task</h4></div>
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

            <form method="get" action="${context}/TaskListServlet" class="form-horizontal">          
                <input type="hidden" id="menu" name="menu" value="task"/>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-10" >
                            <div class="form-group">
                                <label for="taskName" class="col-sm-2 control-label">Task Name</label>
                                <div class="col-sm-8">
                                    <input class="form-control" type="text" name="taskName" value="${taskName}" >
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="form-group">
                            <div class="col-sm-offset-6">
                                <button type="submit" class="btn btn-success">
                                    <i class="glyphicon glyphicon-search"></i> Search
                                </button>
                                <a href="${context}/TaskListServlet?menu=task" class="btn btn-warning">
                                    <i class="glyphicon glyphicon-erase"></i> Reset
                                </a>
                                <a href="${context}/TaskFormServlet?menu=task-form" class="btn btn-default btn-primary">
                                    <i class="glyphicon glyphicon-plus"></i> Add
                                </a>

                            </div>
                        </div>
                    </div>  
                </div>
            </form>
            <div class="row">
                <div class="col-md-12">
                    <c:import url="../include/inc_pagination.jsp"/>
                </div>
            </div> 
            <table id="search_table" class="table table-responsive">                        
                <tr>
                    <th>#</th>
                    <th>Name</th>                   
                    <th>Modified By</th>
                    <th>Modified Date</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="task" items="${taskList}">
                        <tr>
                            <td  nowrap>        
                                <a href="${context}/TaskFormServlet?menu=task-form&taskId=${task.taskId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                <a href="${context}/TaskDeleteServlet?taskId=${task.taskId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                            </td>
                            <td>${task.taskName}</td>                             
                            <td>${task.modifiedBy}</td> 
                            <td>${task.modifiedDate}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="row">
                <div class="col-md-12">
                    <c:import url="../include/inc_pagination.jsp"/>
                </div>
            </div> 

        </div>        
    </div>        
</div>   

<script type="text/javascript">

    $(document).ready(function () {




    });
</script>

<jsp:include page="../include/inc_footer.jsp"/>
