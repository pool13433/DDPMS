<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">        
        <div class="container-fluid text-center"><h4>Project Verify</h4></div>
        <div class="panel-body">

            <form id="budpList" action="${context}/ProjectSearchServlet"   method="post" class="form-horizontal">                
                <div class="form-group">
                    <label for="protId" class="col-sm-2 control-label">Project Type</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" value="${project.protId}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="projName" class="col-sm-2 control-label">Project Name</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" value="${project.projName}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">Budget Plan</label>
                    <div class="col-sm-4">
                         <input type="text" class="form-control" value="${project.budpId}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">Plan</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" value="${project.planId}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPassword3" class="col-sm-2 control-label">Budget</label>
                    <div class="col-sm-4">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <c:forEach var="work" items="${projectWorkingList}">
                                        <th>งบประมาณปี ${work.budgetYear}</th>
                                        </c:forEach>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <c:forEach var="work" items="${projectWorkingList}">
                                        <td>${work.budgetRequestTotal}</td>
                                    </c:forEach>
                                </tr>
                            </tbody>
                        </table>

                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-success">Approve</button>
                        <button type="submit" class="btn btn-danger">Reject</button>
                        <button type="submit" class="btn btn-warning">Cancel</button>
                    </div>
                </div>
            </form>

        </div>        
    </div>        
</div>   

<jsp:include page="../include/inc_footer.jsp"/>
