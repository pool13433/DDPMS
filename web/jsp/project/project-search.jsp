<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container" style="padding-right: 100px;">    
    <div class="panel panel-ddpms">        
        <div class="panel-heading">Manage Project</div>
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
            <form id="searchProj" method="get" action="${context}/ProjectSearchServlet" class="form-horizontal">          
                <input type="hidden" id="menu" name="menu" value="searching"/>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-10" >
                            <div class="form-group">
                                <label for="proj_name" class="col-sm-2 control-label">Project Name</label>
                                <div class="col-sm-8">
                                    <input class="form-control" type="text" name="proj_name" id="proj_name" value="${proj_name}" placeholder="budget plan name..." >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-10" >
                            <div class="form-group">
                                <label for="proj_status" class="col-sm-2 control-label">Status</label>
                                <div class="col-sm-3">
                                    <input class="form-control" type="text" name="proj_status" id="proj_status" value="${proj_status}" placeholder="Begin..." >
                                </div>
                                <label for="proj_details" class="col-sm-2 control-label">Details</label>
                                <div class="col-sm-4">
                                    <input class="form-control" type="text" name="proj_details" id="proj_details" value="${proj_details}" placeholder="End..." >
                                </div>
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
                            <a href="${context}/ProjectSearchServlet?menu=project-plan" class="btn btn-warning">
                                <i class="glyphicon glyphicon-erase"></i> Reset
                            </a>
                            <a href="${context}/ProjectAddServlet?menu=project-form" class="btn btn-default btn-primary">
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
            <form id="budpList" action="${context}/ProjectSearchServlet"   method="post" class="form-horizontal">
                <input type="hidden" id="menu" name="menu" value="manage"/>
                <div style="overflow-y: scroll;max-height: 400px;">                    
                    <table id="search_table" class="table table-bordered table-striped">                        
                        <tr>
                        <th>#</th>
                        <th>Project name</th> 
                        <th>Project Details</th>
                        <th>Project Status</th>
                        <th>Plan</th>
                        <th>Budget</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${projectList}">
                        <tr>
                            <td  nowrap>        
                                <a href="${context}/ProjectEditServlet?id=${p.projId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                <a href="${context}/ProjectDeleteServlet?id=${p.projId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                            </td>
                            <td>${p.projName}</td>
                            <td>${p.projDetails}</td> 
                            <td>${p.projStatus}</td>
                            <td>${p.planId}</td>
                            <td>${p.budpId}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${projectList.isEmpty()}">
                        <tr>                    
                            <td colspan="15"><div class="alert"><span style="padding: 40%">ไม่พบข้อมูลที่ค้นหา</span></div> </td>
                        </tr>
                    </c:if>
                    <c:if test="${projectList == null}">
                        <tr>                    
                            <td colspan="17"><div class="alert"><span style="padding: 40%">กรุณาระบุเงื่อนไขในการค้นหา</span></div> </td>
                        </tr>
                    </c:if>
                </tbody>
                    </table>
                </div>                
                <div class="row">
                    <div class="col-md-12">
                        <c:import url="../include/inc_pagination.jsp"/>
                    </div>
                </div>     
            </form>

        </div>        
    </div>        
</div>   

<script type="text/javascript">

    $(document).ready(function () {
        
        
        
        
    });
</script>

<jsp:include page="../include/inc_footer.jsp"/>
