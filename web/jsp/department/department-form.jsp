<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">                
        <div class="container-fluid text-center"><h4>Form Department</h4></div>
        <div class="panel-body">
            <a href="${context}/DepartmentListServlet?menu=department" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/DepartmentSaveServlet" method="post" class="form-horizontal" >
            <input type="hidden" name="depId" value="${department.depId}"/>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="depCode" class="col-sm-2 control-label">Code</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="depCode" value="${department.depCode}"/>
                        </div>
                        <label for="depName" class="col-sm-2 control-label">Name</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="depName" value="${department.depName}"/>
                        </div>
                    </div>
                </div>
            </div>            
            <div class="form-group">
                <div class="col-sm-offset-1 col-sm-10">
                    <button type="submit" class="btn btn-success">Save</button>
                    <button type="reset" class="btn btn-warning">Reset</button>
                </div>
            </div>
        </form>
    </div>
</div>
<jsp:include page="../include/inc_footer.jsp"/>
