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
                            <input class="form-control" type="text" name="proj_name" id="proj_name" value="${proj_name}" placeholder="project name..." >
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="account" class="col-sm-2 control-label">Account</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="account" id="account" value="${account}" placeholder="Account..." >
                        </div>
                        <label for="dept_id" class="col-sm-2 control-label">Department</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="dept_id" id="dept_id" value="${dept_id}" placeholder="Department..." >
                        </div>                        
                    </div>
                </div>                        
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="prot_id" class="col-sm-2 control-label">Project Type</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="prot_id" id="prot_id" value="${prot_id}" placeholder="Project Type Dropdown..." >
                        </div>                        
                    </div>
                </div>                        
            </div>  
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">                        
                        <label for="proj_details" class="col-sm-2 control-label">Project Details</label>
                        <div class="col-sm-8">                            
                            <textarea class="form-control" rows="3" id="proj_details" value="${proj_details}" placeholder="Details..." ></textarea>
                        </div>
                    </div>
                </div>                        
            </div>          
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="budp_id" class="col-sm-2 control-label">Budget</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" name="budp_id" id="budp_id" value="${budp_id}" placeholder="ID.." >
                        </div>                                                
                    </div>
                </div>                        
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="plan_id" class="col-sm-2 control-label">Plan</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="plan_id" name="plan_id" >
                                <c:forEach items="${planList}" var="p">                            
                                    <c:choose>
                                        <c:when test="${plan_id == p.planId}">
                                            <option value="${p.planId}" selected>${p.planName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${p.planId}">${p.planName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>    
                            </select>
                        </div> 
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-10" ><label class="col-sm-3 control-label"><h6>งบประมาณรายปี</h6></label></div>
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="budget_request1" class="col-sm-2 control-label">งบประมาณปีที่1</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="budget_request1" id="budget_request1" value="${budget_request1}" placeholder="Budg year 1..." >
                        </div>                        
                        <label for="budget_month1" class="col-sm-3 control-label">Budget In Month</label>
                        <div class="col-sm-3">
                            <c:set var="months" value="${months}"/>
                            <select class="form-control" id="budget_month1" name="budget_month1">
                                <option value="" selected>--เลือก--</option>
                                <c:forEach items="${months}" var="month" varStatus="loop">
                                    <c:set var="monthIndex" value="${loop.index+1}"/>
                                    <option value="${monthIndex}">${month}</option>
                                </c:forEach>
                            </select>
                        </div>                  
                    </div>
                </div>                        
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="budget_request2" class="col-sm-2 control-label">งบประมาณปีที่2</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="budget_request2" id="budget_request2" value="${budget_request2}" placeholder="Budg year 2..." >
                        </div>                        
                        <label for="budget_month2" class="col-sm-3 control-label">Budget In Month</label>
                        <div class="col-sm-3">
                            <c:set var="months" value="${months}"/>
                            <select class="form-control" id="budget_month2" name="budget_month2">
                                <option value="" selected>--เลือก--</option>
                                <c:forEach items="${months}" var="month" varStatus="loop">
                                    <c:set var="monthIndex" value="${loop.index+1}"/>
                                    <option value="${monthIndex}">${month}</option>
                                </c:forEach>
                            </select>
                        </div>                  
                    </div>
                </div>                        
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="budget_request3" class="col-sm-2 control-label">งบประมาณปีที่3</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="budget_request3" id="budget_request3" value="${budget_request3}" placeholder="Budg year 3..." >
                        </div>                        
                        <label for="budget_month3" class="col-sm-3 control-label">Budget In Month</label>
                        <div class="col-sm-3">
                            <c:set var="months" value="${months}"/>
                            <select class="form-control" id="budget_month3" name="budget_month3">
                                <option value="" selected>--เลือก--</option>
                                <c:forEach items="${months}" var="month" varStatus="loop">
                                    <c:set var="monthIndex" value="${loop.index+1}"/>
                                    <option value="${monthIndex}">${month}</option>
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
