<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container" style="padding-right: 100px;">    
    <div class="panel panel-ddpms">        
        <div class="container-fluid text-center"><h4>Manage Strategic</h4></div>
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
            <form id="searchProj" method="get" action="${context}/StrategicSearchServlet" class="form-horizontal">          
                <input type="hidden" id="menu" name="menu" value="searching"/>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-10" >
                            <div class="form-group">
                                <label for="stra_name" class="col-sm-2 control-label">Strategic Name</label>
                                <div class="col-sm-8">
                                    <input class="form-control" type="text" name="stra_name" id="stra_name" value="${stra_name}" >
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
                                <a href="${context}/StrategictSearchServlet?menu=strategic" class="btn btn-warning">
                                    <i class="glyphicon glyphicon-erase"></i> Reset
                                </a>
                                <a href="${context}/StrategicAddServlet?menu=strategic-form" class="btn btn-default btn-primary">
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
            <form id="budpList" action="${context}/StrategicSearchServlet"   method="post" class="form-horizontal">
                <input type="hidden" id="menu" name="menu" value="manage"/>
                <div style="overflow-y: scroll;max-height: 400px;">                    
                    <table id="search_table" class="table table-responsive">                        
                        <tr>
                        <th>#</th>
                        <th>Id</th> 
                        <th>Strategic Name</th> 
                        <th>Modified By</th>
                        <th>Modified Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${strategicList}">
                        <tr>
                            <td  nowrap>        
                                <a href="${context}/StrategicEditServlet?id=${s.straId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                <a href="${context}/StrategicDeleteServlet?id=${s.straId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                            </td>
                            <td>${s.straId}</td>
                            <td>${s.straName}</td>
                            <td>${s.modifiedBy}</td> 
                            <td>${s.modifiedDate}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${strategicList.isEmpty()}">
                        <tr>                    
                            <td colspan="15"><div class="alert"><span style="padding: 40%">ไม่พบข้อมูลที่ค้นหา</span></div> </td>
                        </tr>
                    </c:if>
                    <c:if test="${strategicList == null}">
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
<jsp:include page="../include/inc_footer.jsp"/>
