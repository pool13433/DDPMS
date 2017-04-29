<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">        
        <div class="panel-heading">ฟอร์มกรอกข้อมูล ยุธศาสตร์ (Strategic)</div>
        <div class="panel-body">
            <a href="${context}/StrategicSearchServlet" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/StrategicAddServlet" method="post" class="form-horizontal" style="padding-right: 100px;" >
            <input type="hidden" id="id" name="id" value="${stra_id}"/>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="stra_name" class="col-sm-2 control-label">Strategic Name</label>
                        <div class="col-sm-8">
                            <textarea class="form-control" rows="7" id="stra_name" name="stra_name" value="${stra_name}">${stra_name}</textarea>
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
