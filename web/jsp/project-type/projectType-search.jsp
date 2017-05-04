<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container" style="padding-right: 100px;">    
    <div class="panel panel-ddpms">        
        <div class="panel-heading ">Manage Project Type</div>
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

            <a href="${context}/ProjectTypeFormServlet?menu=projectType-form" class="btn btn-default btn-primary">
                <i class="glyphicon glyphicon-plus"></i> Add
            </a>

            <table id="search_table" class="table table-responsive">                        
                <tr>
                    <th>#</th>
                    <th>Code</th>
                    <th>Name</th>
                    <th>Value</th>
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
