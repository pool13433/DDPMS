<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">        
        <div class="panel-heading">ฟอร์มกรอกข้อมูล โครงการ (Project)</div>
        <div class="panel-body">
            <a href="${context}/ProjectSearchServlet" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/ProjectAddServlet" method="post" class="form-horizontal" style="padding-right: 100px;" >
            <input type="hidden" id="id" name="id" value="${proj_id}"/>
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
                        <label for="proj_details" class="col-sm-2 control-label">Project Details</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="proj_details" id="proj_details" value="${proj_details}" placeholder="Details..." >
                        </div>
                        <label for="proj_status" class="col-sm-2 control-label">Project Status</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="proj_status" id="proj_status" value="${proj_status}" placeholder="Status..." >
                        </div>
                    </div>
                </div>                        
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="plan_id" class="col-sm-2 control-label">Plan Id</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="plan_id" id="plan_id" value="${plan_id}" placeholder="ID..." >
                        </div>
                        <label for="budp_id" class="col-sm-2 control-label">Budget Id</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="budp_id" id="budp_id" value="${budp_id}" placeholder="ID.." >
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
