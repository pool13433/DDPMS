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
                        <a href="#" class="btn btn-default btn-primary">
                            <i class="glyphicon glyphicon-plus"></i> Assign Task
                        </a>&nbsp ${project.projName} [เงินงบประมาณ : ${project.budpId} , แผนพัฒนา : ${project.planId}]
                    </div>
                    <div class="panel-body">
                        <table id="search_table" class="table table-bordered table-striped">                        
                            <tr>
                                <th>Task Name</th>                                 
                                <th>Employee</th>
                                <th>Assign Date</th>
                            </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="assign" items="${project.taskAssignList}">
                                    <tr>
                                        <td>${assign.taskName}</td>
                                        <td>${assign.taskUsername}</td>
                                        <td>${assign.modifiedDate}</td>
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
