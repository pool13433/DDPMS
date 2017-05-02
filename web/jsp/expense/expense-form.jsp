<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">        
        <div class="panel-heading">ฟอร์มกรอกข้อมูล รายจ่าย (Expense Details)</div>
        <div class="panel-body">
            <a href="${context}/ProjectExpenseSearchServlet" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/ProjectExpenseAddServlet" method="post" class="form-horizontal" style="padding-right: 100px;" >
            <input type="hidden" id="proj_id" name="proj_id" value="${proj_id}"/>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="proj_id" class="col-sm-2 control-label">Project</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="proj_id" name="proj_id" placeholder="proj_id" disabled>
                                <option value="" selected>--select project-- ${proj_id}</option>  
                                <c:forEach items="${projectList}" var="p">
                                    <c:choose>
                                        <c:when test="${proj_id == p.projId}">
                                            <option value="${p.projId}" selected><h4>${p.projName}</h4></option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${p.projId}">${p.projName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>

                            </select>
                        </div>                        
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="exp_date" class="col-sm-2 control-label">Date</label>
                        <div class="col-sm-3">
                            <input type="text" class="form-control datepicker"  name="exp_date" id="exp_date" value="${exp_date}" placeholder="Date..." >
                        </div>
                        <label for="exp_pr" class="col-sm-2 control-label">PO/PR Number</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="exp_pr" id="exp_pr" value="${exp_pr}" placeholder="PO/PR..." >
                        </div>                        
                    </div>
                </div>                        
            </div>  
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="exp_voch" class="col-sm-2 control-label">Voucher Number/Type</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="exp_voch" id="exp_voch" value="${exp_voch}" placeholder="Voucher/Type..." >
                        </div>
                        <label for="receipt" class="col-sm-2 control-label">Receipt/Reserve Date</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="receipt" id="receipt" value="${receipt}" placeholder="Receipt/Reserve..." >
                        </div>                        
                    </div>
                </div>                        
            </div>   
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="exp_desc" class="col-sm-2 control-label">Description</label>
                        <div class="col-sm-8">
                            <textarea class="form-control" rows="7"  name="exp_desc" id="exp_desc" value="${exp_desc}" placeholder="Description..." ></textarea>
                        </div>                                               
                    </div>
                </div>                        
            </div> 
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">                        
                        <label for="exp_amount" class="col-sm-2 control-label">Amount</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="text" name="exp_amount" id="exp_amount" value="${exp_amount}" placeholder="Amount..." >
                        </div>                         
                    </div>
                </div>                        
            </div> 
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">                        
                        <label for="vender" class="col-sm-2 control-label">Vendor</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" name="vender" id="vender" value="${vender}" placeholder="Vendor..." >
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
