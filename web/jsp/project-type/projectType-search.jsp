<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">                
        <div class="container-fluid text-center"><h4>Manage Project Type</h4></div>
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

            <form method="get" action="${context}/ProjectTypeListServlet" class="form-horizontal">          
                <input type="hidden" id="menu" name="menu" value="project-type"/>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-10" >
                            <div class="form-group">
                                <label for="protCode" class="col-sm-2 control-label">Code</label>
                                <div class="col-sm-4">
                                    <input class="form-control" type="text" name="protCode" value="${criteria.protCode}" >
                                </div>
                                <label for="protName" class="col-sm-2 control-label">Name</label>
                                <div class="col-sm-4">
                                    <input class="form-control" type="text" name="protName" value="${criteria.protName}" >
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="protType" class="col-sm-2 control-label">Type</label>
                                <div class="col-sm-4">                                    
                                    <select class="form-control" name="protType" required>
                                        <option value="" selected>--เลือก--</option>
                                        <c:forEach items="${projectGroupList}" var="projectGroup">
                                            <c:choose>
                                                <c:when test="${criteria.protType == projectGroup.key}">
                                                    <option value="${projectGroup.key}" selected>${projectGroup.value}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${projectGroup.key}">${projectGroup.value}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
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
                                <a href="${context}/ProjectTypeListServlet?menu=projectType" class="btn btn-warning">
                                    <i class="glyphicon glyphicon-erase"></i> Reset
                                </a>
                                <a href="${context}/ProjectTypeFormServlet?menu=projectType-form" class="btn btn-default btn-primary">
                                    <i class="glyphicon glyphicon-plus"></i> Add
                                </a>

                            </div>
                        </div>
                    </div>  
                </div>
            </form>

            <table id="search_table" class="table table-responsive">                        
                <tr>
                    <th>#</th>
                    <th>Code</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Modified By</th>
                    <th>Modified Date</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="projectType" items="${projectTypeList}">
                        <tr>
                            <td  nowrap>        
                                <a href="${context}/ProjectTypeFormServlet?menu=project-form&protId=${projectType.protId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                <a href="${context}/ProjectTypeDeleteServlet?protId=${projectType.protId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                            </td>
                            <td>${projectType.protCode}</td>
                            <td>${projectType.protName}</td> 
                            <td>${projectType.protType}</td>
                            <td>${projectType.modifiedBy}</td> 
                            <td>${projectType.modifiedDate}</td>
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
