<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">                
        <div class="container-fluid text-center"><h4>Form Plan</h4></div>
        <div class="panel-body">
            <a href="${context}/PlanSearchServlet" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/PlanAddServlet" method="post" class="form-horizontal">
            <input type="hidden" id="id" name="id" value="${plan_id}"/>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="plan_name" class="col-sm-2 control-label">Plan Name</label>
                        <div class="col-sm-8">
                            <textarea class="form-control" rows="7" id="plan_name" name="plan_name" value="${plan_name}">${plan_name}</textarea>
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
