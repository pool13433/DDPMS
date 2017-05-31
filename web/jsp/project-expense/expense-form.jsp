<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">        
        <div class="panel-heading">ฟอร์มกรอกข้อมูล รายจ่าย (Expense Details)</div>
        <div class="panel-body">
            <a href="${context}/ProjectExpenseSearchServlet?menu=project_expense" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form action="${context}/ProjectExpenseSaveServlet" method="post" class="form-horizontal"> 
            <input type="hidden" name="expId" value="${expense.expId}">
            <div class="form-group">
                <label for="projId" class="col-sm-2 control-label">Project</label>
                <div class="col-sm-10">
                    <select class="form-control" name="projId" required>
                        <option value="" selected>--select project-- ${proj_id}</option>  
                        <c:forEach items="${projectList}" var="p">
                            <c:choose>
                                <c:when test="${expense.projId == p.projId}">
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
            <div class="form-group">
                <label for="expDate" class="col-sm-2 control-label">Date</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control datepicker"  name="expDate" value="${expense.expDate}" placeholder="Date..." required>
                </div>
                <label for="expPoPr" class=" col-sm-offset-1 col-sm-2 control-label">PO/PR Number</label>
                <div class="col-sm-3">
                    <input class="form-control" type="text" name="expPoPr" value="${expense.expPr}" placeholder="PO/PR..." required>
                </div>                        
            </div>
            <div class="form-group">
                <label for="expVoch" class="col-sm-2 control-label">Voucher Number/Type</label>
                <div class="col-sm-3">
                    <input class="form-control" type="text" name="expVoch"  value="${expense.expVoch}" placeholder="Voucher/Type..." required>
                </div>
                <label for="expReceipt" class="col-sm-2 control-label">Receipt/Reserve Date</label>
                <div class="col-sm-3">
                    <input class="form-control datepicker" type="text" name="expReceipt" value="${expense.receiptDate}" placeholder="Receipt/Reserve..." required>
                </div>                        
            </div> 
            <div class="form-group">
                <label for="expDesc" class="col-sm-2 control-label">Description</label>
                <div class="col-sm-8">
                    <textarea class="form-control" rows="7"  name="expDesc" placeholder="Description..." required>${expense.expDesc}</textarea>
                </div>                                               
            </div> 
            <div class="form-group">                        
                <label for="expAmount" class="col-sm-2 control-label">Amount</label>
                <div class="col-sm-3">
                    <input class="form-control" type="number" name="expAmount" value="${expense.expAmount}" placeholder="Amount..." required>
                </div>                         
            </div>
            <div class="form-group">                        
                <label for="expVender" class="col-sm-2 control-label">Vendor</label>
                <div class="col-sm-8">
                    <input class="form-control" type="text" name="expVender" value="${expense.vender}" placeholder="Vendor..." required>
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
