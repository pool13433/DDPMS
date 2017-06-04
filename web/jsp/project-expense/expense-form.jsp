<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="../include/inc_header.jsp"/>
<div class="container">
    <div class="panel panel-ddpms">        
        <div class="container-fluid text-center"><h4>Manage Expense Details</h4></div>
        <div class="panel-body">
            <a href="${context}/ProjectExpenseSearchServlet?menu=project_expense" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <form name="expense" action="${context}/ProjectExpenseSaveServlet" method="post" class="form-horizontal"> 
            <input type="hidden" name="expId" value="${expense.expId}">
            <div class="form-group">
                <label for="projId" class="col-sm-3 control-label">Project</label>
                <div class="col-sm-8">
                    <select class="form-control" name="projId" required>
                        <option value="" selected>--select project-- ${proj_id}</option>  
                        <c:forEach items="${projectList}" var="p">
                            <c:choose>
                                <c:when test="${expense.projId == p.projId}">
                                    <option value="${p.projId}" selected 
                                            data-all="${p.budgetAll}" 
                                            data-actualUse="${p.budgetActualUse}" 
                                            data-balance="${p.budgetBalance}"><h4>${p.projName}</h4></option>
                                    </c:when>
                                    <c:otherwise>
                                    <option value="${p.projId}"
                                            data-all="${p.budgetAll}" 
                                            data-actualUse="${p.budgetActualUse}" 
                                            data-balance="${p.budgetBalance}">${p.projName}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>                        
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-3">
                    <div class="alert alert-success alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <p><strong>งบอนุมัติทั้งหมด</strong> <span id="labelAll" style="font-size: 25px;">0</span>&nbsp;&nbsp;บาท</p>
                    </div>
                </div>           
                <div class="col-sm-3">
                    <div class="alert alert-info alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <p><strong>งบที่ใช้ไปแล้ว</strong> <span id="labelActualUse" style="font-size: 25px;">0</span>&nbsp;&nbsp;บาท</p>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <p><strong>งบคงเหลือ</strong> <span id="labelBalance" style="font-size: 25px;">0</span>&nbsp;&nbsp;บาท</p>
                    </div>
                </div>
            </div>
            <div class="form-group">        
                <label for="expVoch" class="col-sm-3 control-label">Voucher Number/Type</label>
                <div class="col-sm-3">
                    <input class="form-control" type="text" name="expVoch"  value="${expense.expVoch}" placeholder="Voucher/Type..." required>
                </div>
                <label for="expPoPr" class="col-sm-2 control-label">PO/PR Number</label>
                <div class="col-sm-3">
                    <input class="form-control" type="text" name="expPoPr" value="${expense.expPr}" placeholder="PO/PR..." required>
                </div>                        
            </div>
            <div class="form-group">
                <label for="expDate" class="col-sm-3 control-label">Use Date</label>
                <div class="col-sm-2">
                    <input type="text" class="form-control datepicker"  name="expDate" value="${expense.expDate}" placeholder="Date..." required>
                </div>               
                <label for="expReceipt" class="col-sm-offset-1 col-sm-2 control-label">Receipt/Reserve Date</label>
                <div class="col-sm-3">
                    <input class="form-control datepicker" type="text" name="expReceipt" value="${expense.receiptDate}" placeholder="Receipt/Reserve..." required>
                </div>                        
            </div> 
            <div class="form-group">
                <label for="expDesc" class="col-sm-3 control-label">Description</label>
                <div class="col-sm-8">
                    <textarea class="form-control" rows="7"  name="expDesc" placeholder="Description..." required>${expense.expDesc}</textarea>
                </div>                                               
            </div> 
            <div class="form-group">                        
                <label for="expAmount" class="col-sm-3 control-label">Amount</label>
                <div class="col-sm-3">
                    <input class="form-control" type="number" name="expAmount" value="${expense.expAmount}" placeholder="Amount..." required>
                </div>                         
            </div>
            <div class="form-group">                        
                <label for="expVender" class="col-sm-3 control-label">Vendor</label>
                <div class="col-sm-8">
                    <input class="form-control" type="text" name="expVender" value="${expense.vender}" placeholder="Vendor..." required>
                </div>                        
            </div>

            <div class="form-group">
                <div class="col-sm-offset-3 col-sm-9">
                    <button type="submit" class="btn btn-success">Save</button>
                    <button type="reset" class="btn btn-warning">Reset</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    var BUDGET = {all: 0, actualUse: 0, balance: 0}
    $(function () {
        var $eleDropdown = $('select[name="projId"]');
        $eleDropdown.on('change', function () {
            var $elementOption = $(this).find(":selected");
            var budgetAll = $elementOption.attr('data-all');
            var budgetActualUse = $elementOption.attr('data-actualUse');
            var budgetBalance = $elementOption.attr('data-balance');
            BUDGET = {all: budgetAll, actualUse: budgetActualUse, balance: budgetBalance};
            console.log('xxxx ::==', BUDGET);
            //return false;
            $('#labelAll').html(numberWithCommas(budgetAll));
            $('#labelActualUse').html(numberWithCommas(budgetActualUse));
            $('#labelBalance').html(numberWithCommas(budgetBalance));
        }).trigger('change');
        $('form[name="expense"]').submit(function (e) {
            var expId = $('input[name="expId"]').val();
            var budgetBalance = parseInt(BUDGET.balance);
            var budgetAutualUse = parseInt(BUDGET.actualUse);
            var budgetInput = parseInt($('input[name="expAmount"]').val());
            if (budgetInput <= 0) {
                alert('ไม่อนุญาตให้กรอกค่าจำนวนเงินมากกว่า "0"');
                return false;
            }
            var isOverLimit = false;
            if (expId == '') {// Create
                isOverLimit = (parseInt(budgetInput) > budgetBalance);
            } else { // Update
                isOverLimit = (parseInt(budgetInput) > (budgetBalance + budgetAutualUse));
            }
            if (isOverLimit) {
                alert('ไม่อนุญาตให้กรอกค่าจำนวนเงินเกิน งบประมาณคงเหลือ ทั้งหมดได้');
                return false;
            }

            var valids = validateDateRange();
            if (valids.length > 0) {
                var message = '';
                $.each(valids, function (index,vali) {
                    message += (vali.message+' \n ');
                });
                alert(message);
                return false;
            }
            return true;
            e.preventDefault();
        });
    });
    function numberWithCommas(x) {
        return (x == undefined ? '' : x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
    }
    function validateDateRange() {
        var result = [];
        var expDate = moment($('input[name="expDate"]').val(), "DD/MM/YYYY");
        var expReceiptDate = moment($('input[name="expReceipt"]').val(), "DD/MM/YYYY");
        var currentDate = moment();
        if (expDate.isSameOrAfter(currentDate)) {
            result.push({name: 'expDate', message: 'กรุณากรอก "Use Date" ไม่เกินวันที่ปัจจุบัน'});
        }
        if (expReceiptDate.isSameOrAfter(currentDate)) {
            result.push({name: 'expReceipt', message: 'กรุณากรอก "Receipt/Reserve Date" ไม่เกินวันที่ปัจจุบัน'});
        }
        return result;
    }
</script>
<jsp:include page="../include/inc_footer.jsp"/>

