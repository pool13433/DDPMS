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
                                <th>#</th>
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
