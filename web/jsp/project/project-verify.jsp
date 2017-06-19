<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">        
        <div class="container-fluid text-center"><h4>Project Verify</h4></div>
        <div class="panel-body">
            <a href="${context}/ProjectSearchServlet" class="btn btn-default btn-primary"><i class="glyphicon glyphicon-arrow-left"></i></a>
        </div>
        <div class="panel-body">
            <form id="formVerify" action="${context}/ProjectVerifyServlet"   method="post" class="form-horizontal">                
                <input type="hidden" name="verifyCase">
                <input type="hidden" name="projId" value="${project.projId}">
                <div class="form-group">
                    <label for="protId" class="col-sm-2 control-label">Project Type</label>
                    <div class="col-sm-4">                       
                        <label class="control-label"><u>${project.protId}</u></label>
                    </div>
                    <label for="budpId" class="col-sm-2 control-label">Budget Plan</label>
                    <div class="col-sm-4">
                        <label class="control-label"><u>${project.budpId}</u></label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="projName" class="col-sm-2 control-label">Project Name</label>
                    <div class="col-sm-4">
                        <label class="control-label"><u>${project.projName}</u></label>
                    </div>
                    <label for="projDetail" class="col-sm-2 control-label">Project Detail</label>
                    <div class="col-sm-4">
                        <label class="control-label"><u>${project.projDetail}</u></label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="planId" class="col-sm-2 control-label">Plan</label>
                    <div class="col-sm-9">
                        <label class="control-label"><u>${project.planId}</u></label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="modifiedBy" class="col-sm-2 control-label">Request By</label>
                    <div class="col-sm-4">
                        <label class="control-label"><u>${project.modifiedBy}</u></label>
                    </div>
                    <label for="modifiedDate" class="col-sm-2 control-label">Request Date</label>
                    <div class="col-sm-4">
                        <label class="control-label"><u>${project.modifiedDate}</u></label>
                    </div>
                </div>
                <c:if test="${EMPLOYEE.status == 'APPROVER'}">
                    <div class="form-group">
                        <label for="budget" class="col-sm-2 control-label">Budget</label>
                        <div class="col-sm-10">
                            <div class="alert alert-warning alert-dismissible" role="alert">
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <strong>สำหรับการตรวจสอบการของบประมาณ!</strong> 
                                หากท่านต้องการเปลี่ยนแปลงจำนวนงบของแต่ละเดือนให้ท่านกรอกจำนวนตัวเลขในช่องของแต่ละเดือน
                                เพื่อเปลี่ยนแปลงจำนวนลบที่ขอก่อนการกดปุ่ม Approve
                            </div>
                            <table class="table table-bordered table-striped" id="tblBudgetMonth">
                                <thead>
                                    <tr>
                                        <th style="text-align: center;">ปี </th>
                                        <th colspan="6" style="text-align: center;">Month</th>                              
                                    </tr>
                                </thead>
                                <tbody>                            
                                    <c:forEach var="work" items="${projectWorkingList}">       
                                        <c:set var="budgetTotal" value="${
                                               work.budgetRequestM1+work.budgetRequestM2+work.budgetRequestM3+work.budgetRequestM4+
                                                   work.budgetRequestM5+work.budgetRequestM6+work.budgetRequestM7+work.budgetRequestM8+
                                                   work.budgetRequestM9+work.budgetRequestM10+work.budgetRequestM11+work.budgetRequestM12                                               
                                               }"></c:set>
                                            <tr>
                                                <td rowspan="5" style="vertical-align: middle;text-align: center;">${work.budgetYear} 
                                                <input type="hidden" class="budgetYear" name="budgetYear" value="${work.budgetYear}"/>
                                            </td>
                                            <td>Jan</td>
                                            <td>Feb</td>
                                            <td>Mar</td>
                                            <td>Apr</td>
                                            <td>May</td>
                                            <td>June</td>
                                        </tr>
                                        <tr>
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_1" value="${work.budgetRequestM1}"/></td>
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_2" value="${work.budgetRequestM2}"/></td>
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_3" value="${work.budgetRequestM3}"/></td>
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_4" value="${work.budgetRequestM4}"/></td>
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_5" value="${work.budgetRequestM5}"/></td>
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_6" value="${work.budgetRequestM6}"/></td>
                                        </tr>
                                        <tr>                                                                               
                                            <td>Jul</td>
                                            <td>Aug</td>
                                            <td>Sep</td>
                                            <td>Oct</td>
                                            <td>Nov</td>
                                            <td>Dec</td>
                                        </tr>
                                        <tr>                                       
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_7" value="${work.budgetRequestM7}"/></td>
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_8" value="${work.budgetRequestM8}"/></td>
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_9" value="${work.budgetRequestM9}"/></td>
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_10" value="${work.budgetRequestM10}"/></td>
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_11" value="${work.budgetRequestM11}"/></td>
                                            <td><input type="number" class="form-control input-sm" name="${work.budgetYear}_12" value="${work.budgetRequestM12}"/></td>
                                        </tr>
                                        <tr>                                       
                                            <td colspan="3" class="form-inline" style="background-color: #FFECB3;">
                                                <label class="control-label">Budget Request Total: </label>
                                                <input type="text" class="form-control input-sm" value="${budgetTotal}" readonly/>                                            
                                            </td>
                                            <td colspan="3" class="form-inline" style="background-color: #DCE775;">
                                                <label class="control-label">Budget Approve Total:</label>
                                                <input type="text" class="form-control input-sm" name="${work.budgetYear}_budgetTotal" readonly/>
                                            </td>
                                        </tr>
                                    </c:forEach> 
                                </tbody>
                            </table>

                        </div>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="reason" class="col-sm-2 control-label">Reason <span style="color: red;">*</span></label>
                    <div class="col-sm-9">
                        <textarea class="form-control" name="reason"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <c:if test="${EMPLOYEE.status == 'APPROVER'}">
                            <button type="button" class="btn btn-success" id="btn-approve">Approve</button>
                            <button type="button" class="btn btn-danger" id="btn-reject">Reject</button>
                        </c:if>
                        <c:if test="${EMPLOYEE.status != 'APPROVER'}">
                            <button type="button" class="btn btn-warning" id="btn-cancel">Cancel</button>
                        </c:if>
                    </div>
                </div>

                <c:if test="${!projectHistoryList.isEmpty() && projectHistoryList != null}">                
                    <div class="row">
                        <div class="col-sm-12" >
                            <div class="form-group">  
                                <div class="col-sm-1">&nbsp;</div>
                                <div class="col-sm-10">
                                    <div class="panel">
                                        <div class="panel-heading">
                                            <h6>Project Movement</h6>
                                        </div>
                                        <div class="panel-body">
                                            <div style="overflow-y: scroll;max-height: 400px;">                                
                                                <table id="search_table" class="table table-responsive" > 
                                                    <thead style="background-color: wheat">
                                                        <tr>
                                                            <th>Modified Date</th>
                                                            <th>Project name</th> 
                                                            <th>Project Status</th>
                                                            <th>Remarks</th>
                                                            <th>Modified By</th>

                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="p" items="${projectHistoryList}">
                                                            <tr>
                                                                <td>${p.modifiedDate}</td> 
                                                                <td>${p.projId}</td>
                                                                <td>${p.status}</td> 
                                                                <td>${p.remarks}</td>
                                                                <td>${p.modifiedBy}</td>
                                                            </tr>
                                                        </c:forEach>                            
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>                    
                    </div>
                </c:if>
            </form>

        </div>        
    </div>        
</div>   
<script type="text/javascript">
    var verifyCaseLabel = {'APPROVE': 'อนุมัติ', 'REJECT': 'ไม่อนุมัติ', 'CANCEL': 'ยกเลิก'};
    $(function () {
        $('#btn-approve').on('click', function () {
            $('input[name="verifyCase"]').val('APPROVE');
            $('#formVerify').submit();
        });
        $('#btn-reject').on('click', function () {
            $('input[name="verifyCase"]').val('REJECT');
            $('#formVerify').submit();
        });
        $('#btn-cancel').on('click', function () {
            $('input[name="verifyCase"]').val('CANCEL');
            $('#formVerify').submit();
        });
        $('#formVerify').submit(function (e) {
            var verifyCase = $('input[name="verifyCase"]').val();
            var verifyReason = $('textarea[name="reason"]').val();
            if (verifyReason == '') {
                alert('กรุณาระบุเหตุการทำรายการ');
                return false;
            } else {
                var isConfirm = confirm('ยืนยันการ ' + verifyCaseLabel[verifyCase] + ' ใช่หรือไม่ ใช่[ตกลง] || ไม่ใช่[ยกเลิก]');
                if (isConfirm) {
                    return true;
                } else {
                    return false;
                }
            }
            e.preventDefault();
        });
        $('#tblBudgetMonth').find('input').focusout(function () {
            calculatorBudgetTotal();
        });
        calculatorBudgetTotal();        
    });

    function calculatorBudgetTotal() {
        var $budgetYears = $('.budgetYear');
        $.each($budgetYears, function (ind, $year) {
            var year = $($year).val();
            console.log('xxxxx ::==' + year);
            var budgetTotla = 0;
            for (var i = 1; i < 13; i++) {
                var name = year + '_' + i;
                //console.log('name ::=='+name);
                var budgetM = $('input[name="' + name + '"]').val();
                //console.log('budgetM ::=='+budgetM);
                budgetTotla += parseInt(((budgetM != '' && budgetM != undefined) ? budgetM : 0));
            }
            console.log('budgetTotla ::==' + budgetTotla);
            $('input[name="' + year + '_budgetTotal"]').val(parseInt(budgetTotla));
        });
    }

    function validateInputNumber() {
        var validCollection = 0;
        var numbers = $('#tblBudgetMonth').find('input[type="number"]');
        $(numbers, function (index, numb) {
            var value = $(numb).val();
            if (!IsNumeric(value)){
                $(numb).focus();
                validCollection ++;
            }
        });
        function IsNumeric(input) {
            var RE = /^-{0,1}\d*\.{0,1}\d+$/;
            return (RE.test(input));
        }
        return validCollection;
    }

</script>
<jsp:include page="../include/inc_footer.jsp"/>

