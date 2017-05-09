<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">        
        <div class="container-fluid text-center"><h4>Manage Project Type</h4></div>
        <div class="panel-body">
            <a href="${context}/ProjectTypeListServlet?menu=project-type" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/ProjectTypeSaveServlet" method="post" class="form-horizontal" >
            <input type="hidden" name="protId" value="${projectType.protId}"/>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="protCode" class="col-sm-2 control-label">Code</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="protCode" value="${projectType.protCode}"/>
                        </div>
                        <label for="protName" class="col-sm-2 control-label">Name</label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="protName" value="${projectType.protName}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="protType" class="col-sm-2 control-label">Type</label>
                        <div class="col-sm-4">                            
                            <select class="form-control" id="protType" name="protType" required>
                                <option value="" selected>--เลือก--</option>
                                <c:forEach items="${projectGroupList}" var="group">
                                    <c:choose>
                                        <c:when test="${projectType.protType == group.confName}">
                                            <option value="${group.confName}" selected>${group.confValue}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${group.confName}">${group.confValue}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
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
