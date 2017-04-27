<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container" style="padding-right: 100px;">    
    <div class="panel panel-ddpms">        
        <div class="panel-heading">Manage Project Working</div>
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
            <form id="searchProjw" method="get" action="${context}/ProjectWorkingSearchServlet" class="form-horizontal">          
                <input type="hidden" id="menu" name="menu" value="searching"/>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-10" >
                            <div class="form-group">
                                <label for="proj_id" class="col-sm-1 control-label">Project</label>
                                <div class="col-sm-3">
                                    <select class="form-control" id="proj_id" name="proj_id" placeholder="proj_id" >
                                        <option value="" selected>--เลือก--</option>                                        
                                        <c:forEach items="${projectList}" var="p">
                                            <c:choose>
                                                <c:when test="${proj_id == p.projId}">
                                                    <option value="${p.projId}" selected>${p.projName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${p.projId}">${p.projName}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        
                                    </select>
                                </div>
                                <label for="budget_year" class="col-sm-2 control-label">Budget Year</label>
                                <div class="col-sm-3">
                                    <input class="form-control" type="text" name="budget_year" id="budget_year" value="${budget_year}" placeholder="Year..." >
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
                            <a href="${context}/ProjectWorkingSearchServlet?menu=project-plan" class="btn btn-warning">
                                <i class="glyphicon glyphicon-erase"></i> Reset
                            </a>
                            <a href="${context}/ProjectWorkingAddServlet?menu=project-form" class="btn btn-default btn-primary">
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
            <form id="projwList" action="${context}/ProjectWorkingSearchServlet"   method="post" class="form-horizontal">
                <input type="hidden" id="menu" name="menu" value="manage"/>
                <div style="overflow-y: scroll;max-height: 400px;">                    
                    <table id="search_table" class="table table-bordered table-striped">                        
                        <tr>
                        <th>#</th>
                        <th>Project name</th> 
                        <th>Budget Year</th>
                        <th>Budget Request</th>
                        <th>Budget Response</th>
                        <th>Budget Usage</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${projectWorkingList}">
                        <tr>
                            <td  nowrap>        
                                <a href="${context}/ProjectWorkingEditServlet?id=${p.projwId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                <a href="${context}/ProjectWorkingDeleteServlet?id=${p.projwId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                            </td>
                            <td>${p.projId}</td>
                            <td>${p.budgetYear}</td>
                            <td>${p.budgetRequest}</td> 
                            <td>${p.budgetResponse}</td>
                            <td>${p.budgetUsage}</td>                            
                        </tr>
                    </c:forEach>
                    <c:if test="${projectWorkingList.isEmpty()}">
                        <tr>                    
                            <td colspan="15"><div class="alert"><span style="padding: 40%">ไม่พบข้อมูลที่ค้นหา</span></div> </td>
                        </tr>
                    </c:if>
                    <c:if test="${projectWorkingList == null}">
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
