<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">        
        <div class="panel-heading">โครงการที่มีการเปลี่ยนแผน</div>
        <div class="panel-body">
            <!-- Alert Message -->
            <c:if test="${!empty MessageUI}">
                <div class="alert alert-${MessageUI.cssClass} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>${MessageUI.title}!</strong> ${MessageUI.message}
                </div>
                <c:remove var="MessageUI" scope="session" />
            </c:if>  
            
            <form class="form-horizontal" action="${context}/ProjectShiftSearchServlet" method="get">
                <input type="hidden" id="menu" name="menu" value="searching"/>
                <div class="form-group">
                    <label for="proj_id" class="col-sm-1 control-label">Project</label>
                    <div class="col-sm-8">
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
                    <div class="col-sm-3">
                        <button type="submit" class="btn btn-info">Search</button>
                        <a href="${context}/ProjectShiftSearchServlet?menu=project_shift" class="btn btn-warning">Reset</a>
                         <a href="${context}/ProjectShiftAddServlet?menu=project_shift" class="btn btn-default btn-primary">
                            <i class="glyphicon glyphicon-plus"></i> Add
                        </a>
                    </div>
                </div>
                     
            </form>
            <c:if test="${!projectShiftList.isEmpty()}">
                <c:forEach items="${projectSearchList}" var="project">                
                    <div class="panel panel-ddpms">
                        <div class="panel-heading" style="background-color: #7aba7b">
                            &nbsp; ${project.projName}                       
                        </div>
                        <div class="panel-body">
                            <table id="search_table" class="table table-bordered table-striped">                        
                                <tr style="background-color: tomato">
                                    <th style="width: 8%">#</th>
                                    <th style="width: 8%">ครั้งที่</th>
                                    <th style="width: 72%">สาเหตุ</th>
                                    <th style="width: 12%">วันที่เลื่อน</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="ps" items="${projectShiftList}" varStatus="i"> 
                                        <tr>
                                            <td  nowrap>        
                                                <a href="${context}/ProjectShiftEditServlet?id=${ps.projsId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                                <a href="${context}/ProjectShiftDeleteServlet?id=${ps.projsId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                                            </td>
                                            <td>${i.count}</td>
                                            <td>${ps.projsReason}</td>
                                            <td>${ps.projsPlanDate}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>       
                </c:forEach>
            </c:if>                
            
            <c:if test="${projectShiftList.isEmpty()}">
                <div class="panel panel-ddpms">
                    <div class="panel-body">
                        <table id="search_table" class="table table-bordered table-striped">   
                            <tr>                    
                                <td colspan="4"><div class="alert"><span style="padding: 35%">ไม่พบข้อมูลที่ค้นหา</span></div> </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </c:if>
            <c:if test="${projectSearchList == null}">
                <div class="panel panel-ddpms">
                    <div class="panel-body">
                        <table id="search_table" class="table table-bordered table-striped">   
                            <tr>                    
                                <td colspan="4"><div class="alert"><span style="padding: 35%">กรุณาระบุเงื่อนไขในการค้นหา</span></div> </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </c:if>
        </div>        
    </div>        
</div>   

<script type="text/javascript">

    $(document).ready(function () {




    });
</script>

<jsp:include page="../include/inc_footer.jsp"/>
