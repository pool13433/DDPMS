<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">        
        <div class="panel-heading">ฟอร์มกรอกข้อมูล เริ่มต้นโครงการ</div>
        <div class="panel-body">
            <a href="${context}/ProjectWorkingSearchServlet" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/ProjectWorkingAddServlet" method="post" class="form-horizontal" >
            <input type="hidden" id="id" name="id" value="${projw_id}"/>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="proj_id" class="col-sm-2 control-label">Project</label>
                        <div class="col-sm-3">
                            <select class="form-control" id="proj_id" name="proj_id" placeholder="proj_id" >
                                <option value="" selected>--select project--</option>                                        
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
                        <label for="budget_year" class="col-sm-2 control-label">Budget Year</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="budget_year" id="budget_year" value="${budget_year}" placeholder="Year..." >
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="budget_request" class="col-sm-2 control-label">Budget Request</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="budget_request" id="budget_request" value="${budget_request}" placeholder="Budget request..." >
                        </div>
                        <label for="budget_response" class="col-sm-2 control-label">Budget Response</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="budget_response" id="budget_response" value="${budget_response}" placeholder="Budget response..." >
                        </div>
                    </div>
                </div>                        
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="budget_usage" class="col-sm-2 control-label">Budget Usage</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="budget_usage" id="budget_usage" value="${budget_usage}" placeholder="Budget usage..." >
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
