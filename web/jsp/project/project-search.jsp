<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">        
        <div class="container-fluid text-center"><h4>Manage Project</h4></div>
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
                                <label for="prot_id" class="col-sm-2 control-label">Project Type</label>
                                <div class="col-sm-8">
                                    <select class="form-control" id="prot_id" name="prot_id" >
                                        <option value="" selected>    --กรุณาเลือก--  </option>
                                        <c:forEach items="${projectTypeList}" var="p">                            
                                            <c:choose>
                                                <c:when test="${prot_id == p.protId}">
                                                    <option value="${p.protId}" selected>${p.protName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${p.protId}">${p.protName}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>    
                                    </select>
                                </div> 
                            </div>
                        </div>                        
                    </div>
                    <div class="row">
                        <div class="col-sm-10" >
                            <div class="form-group">                                 
                                <label for="plan_id" class="col-sm-2 control-label">Plan</label>
                                <div class="col-sm-8">
                                    <select class="form-control" id="plan_id" name="plan_id" >
                                        <option value="" selected>    --กรุณาเลือก--  </option>
                                        <c:forEach items="${planList}" var="p">                            
                                            <c:choose>
                                                <c:when test="${plan_id == p.planId}">
                                                    <option value="${p.planId}" selected>${p.planName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${p.planId}">${p.planName}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>    
                                    </select>
                                </div>
                            </div>
                        </div>                        
                    </div>
                    <div class="row">
                        <div class="col-sm-10" >
                            <div class="form-group">                                 
                                <label for="budp_id" class="col-sm-2 control-label">Budget Plan</label>
                                <div class="col-sm-8">
                                    <select class="form-control" id="budp_id" name="budp_id" >
                                        <option value="" selected>    --กรุณาเลือก--  </option>
                                        <c:forEach items="${budgetPlanList}" var="bp">                            
                                            <c:choose>
                                                <c:when test="${budp_id == bp.budpId}">
                                                    <option value="${bp.budpId}" yearS="${bp.budpYearBegin }" yearT="${bp.budpYearEnd}" selected>${bp.budpName}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${bp.budpId}" yearS="${bp.budpYearBegin }" yearT="${bp.budpYearEnd}">${bp.budpName}</option>                                            
                                                </c:otherwise>
                                            </c:choose>                                          
                                        </c:forEach>    
                                    </select>                            
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
                    <table id="search_table" class="table table-responsive">                                                
                        <th>#</th>
                        <th>Project name</th> 
                        <th>Project Details</th>
                        <th>Project Type</th>
                        <th>Plan</th>
                        <th>Budget</th>                        
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${projectList}">
                        <tr>
                            <td  nowrap>        
                                <a href="${context}/jsp/project/project-approve.jsp" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-bold"></i></a>                                
                                <c:if test="${p.projStatus=='WAITING'}" >
                                    <a href="${context}/ProjectEditServlet?id=${p.projId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                    <a href="${context}/ProjectDeleteServlet?id=${p.projId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>                                
                                </c:if>    
                                
                            </td>
                            <td>${p.projName}</td>
                            <td>${p.projDetails}</td> 
                            <td>${p.protId}</td>
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
