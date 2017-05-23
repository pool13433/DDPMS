<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">        
        <div class="panel-heading">โครงการปรับเปลี่ยนแผน</div>
        <div class="panel-body">               
            <a href="${context}/ProjectShiftSearchServlet?menu=project_shift" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>        
        <form action="${context}/ProjectShiftAddServlet" method="post" class="form-horizontal">
            <input type="hidden" id="id" name="id" value="${projs_id}"/>
            <div class="form-group">
                <label for="proj_id" class="col-sm-2 control-label">Project</label>
                <div class="col-sm-9">
                    <select class="form-control" id="proj_id" name="proj_id" placeholder="proj_id" >
                        <option value="" selected>--เลือก--</option>                                        
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
            </div>
            <div class="form-group">
                <label for="reason" class="col-sm-2 control-label">Reason</label>
                <div class="col-sm-9">
                    <textarea rows="5" class="form-control" name="reason">${reason}</textarea>
                </div>               
            </div>    
            <div class="form-group">
                <label for="projsPlanDate" class="col-sm-2 control-label">Project Shift Date</label>
                <div class="col-sm-3">
                    <input class="form-control datepicker" type="text" id="projsPlanDate" name="projsPlanDate"  value="${projsPlanDate}" readonly>
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

<script type="text/javascript">

    $(document).ready(function () {
        var id = $("#id").val();
        if(id !== "" && id !== null ){
            $("#proj_id").attr('disabled','disabled');
        }else{
            $("#proj_id").attr('enabled','enabled');
        }



    });
</script>

<jsp:include page="../include/inc_footer.jsp"/>
