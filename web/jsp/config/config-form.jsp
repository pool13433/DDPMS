<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">        
        <div class="panel-heading">ฟอร์มกรอกข้อมูล แผน (Plan)</div>
        <div class="panel-body">
            <a href="${context}/ConfigListServlet?menu=config" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/ConfigSaveServlet" method="post" class="form-horizontal" >
            <input type="hidden" name="confId" value="${config.confId}"/>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="plan_name" class="col-sm-2 control-label">Code</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="confCode" value="${config.confCode}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="confName" class="col-sm-2 control-label">Name</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="confName" value="${config.confName}"/>
                        </div>
                        <label for="confValue" class="col-sm-2 control-label">Value</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="confValue" value="${config.confValue}"/>
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
