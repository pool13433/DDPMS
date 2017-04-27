<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">        
        <div class="panel-heading">ฟอร์มกรอกข้อมูล Budget Plan</div>
        <div class="panel-body">
            <a href="${context}/BudgetplanSearchServlet" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/BudgetplanAddServlet" method="post" class="form-horizontal" style="padding-right: 100px;" >
            <input type="hidden" id="id" name="id" value="${budp_id}"/>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="budp_name" class="col-sm-2 control-label">Budget Plan Name</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" name="budp_name" id="budp_name" value="${budp_name}" placeholder="budget plan name..." >
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="budp_begin" class="col-sm-2 control-label">Budget Plan Begin</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="budp_begin" id="budp_begin" value="${budp_begin}" placeholder="Begin..." >
                        </div>
                        <label for="budp_end" class="col-sm-2 control-label">Budget Plan End</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="budp_end" id="budp_end" value="${budp_end}" placeholder="End..." >
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
