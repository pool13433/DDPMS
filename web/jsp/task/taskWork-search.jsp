<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container" style="padding-right: 100px;">    
    <div class="panel panel-ddpms">        
        <div class="panel-heading">Manage Task Assignment</div>
        <div class="panel-body">
            <!-- Alert Message -->
            <c:if test="${!empty MessageUI}">
                <div class="alert alert-${MessageUI.cssClass} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>${MessageUI.title}!</strong> ${MessageUI.message}
                </div>
                <c:remove var="MessageUI" scope="session" />
            </c:if>            

            <a href="${context}/TaskWorkFormServlet" class="btn btn-default btn-primary">
                <i class="glyphicon glyphicon-plus"></i> Add Task
            </a>
            <table id="search_table" class="table table-bordered table-striped">                        
                <tr>
                    <th style="width: 8%">#</th>
                    <th>Project</th>             
                    <th>Task</th>                                 
                    <th>Detail</th>                                 
                    <th>Manday (Hr)</th>
                    <th>Task Name</th>
                    <th>Work Date</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="work" items="${taskWorkingList}">
                        <tr>
                            <td  nowrap>        
                                <a href="${context}/TaskWorkFormServlet?taskwId=${work.taskwId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                <a href="${context}/TaskWorkDeleteServlet?taskwId=${work.taskwId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                            </td>
                            <td>${work.projName}</td>
                            <td>${work.taskName}</td>
                            <td>${work.taskwDetail}</td>
                            <td>${work.taskwManday}</td>
                            <td>${work.taskaId}</td>
                            <td>${work.taskwDate}</td>                            
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
