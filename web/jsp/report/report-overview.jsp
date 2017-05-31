<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<c:set var="columnLength" value="${(rangeYear.budpYearEnd-rangeYear.budpYearBegin+1)*3}"/>
<jsp:include page="../include/inc_header.jsp"/>
<style type="text/css">
    #table-overview thead tr th{vertical-align: middle;text-align: center;font-size: 12px;}
    #table-overview tbody tr td{vertical-align: middle;text-align: center;font-size: 12px;}
</style>
<div class="container-fluid">
    <div class="panel panel-ddpms">        
        <div class="panel-heading">รายงานข้อมูลสรุปในด้านงบประมาณ <button id="btnPrintPDF" class="btn btn-warning">Print</button></div>
        <div class="panel-body" style="overflow-x: auto;overflow-y: auto;height: 500px;" id="print-container">
            <table id="table-overview" class="table table-bordered table-striped" border="1">
                <thead>
                    <tr>
                        <th rowspan="3">ที่</th>
                        <th rowspan="3">รายการ</th>
                        <th rowspan="3">Owner</th>
                        <th colspan="3" rowspan="2">งบประมาณ</th>
                        <th colspan="${columnLength}">งบประมาณรายปี (บาท)</th>
                    </tr>
                    <tr>
                        <c:forEach var="year" begin="${rangeYear.budpYearBegin}" end="${rangeYear.budpYearEnd}">
                            <th colspan="3">${year}</th>
                            </c:forEach>
                    </tr>
                    <tr>
                        <th>งบที่เสนอรออนุมัติ</th>
                        <th>งบที่ได้รับ</th>
                        <th>งบที่ใช้จริง</th>
                            <c:forEach var="year" begin="${rangeYear.budpYearBegin}" end="${rangeYear.budpYearEnd}">
                            <th>งบที่เสนอรออนุมัติ</th>
                            <th>งบที่ได้รับ</th>
                            <th>งบที่ใช้จริง</th>
                            </c:forEach>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="planId" value="-1"/>
                    <c:forEach var="budget" items="${budgetProjectList}" varStatus="loop">
                        <c:set var="budgetYear" value="${budget.budgetProject}"/>                        
                        <c:if test="${planId != budget.planId}">
                            <tr>
                                <td colspan="${6+columnLength}" 
                                    style="text-align: left;font-size: 14px;font-weight: bold;"><i class="glyphicon glyphicon-flag"></i> ${budget.planName}</td>
                            </tr>
                            <c:set var="planId" value="${budget.planId}"/>
                        </c:if>
                        <c:if test="${planId == budget.planId}">
                            <tr>
                                <td>${loop.index+1}</td>
                                <td style="text-align: left;">${budget.project}</td>
                                <td>${budget.owner}</td>
                                <td style="background-color: #E0E0E0;" title="งบที่เสนอรออนุมัติ รวมทุกปีงบ" >
                                    <fmt:formatNumber value = "${budgetYear.sum_request_all}" type = "number" maxFractionDigits = "2"/>
                                </td>
                                <td style="background-color: #CCFF90;" title="งบที่ได้รับ รวมทุกปีงบ" >
                                    <fmt:formatNumber value = "${budgetYear.sum_approve_all}" type = "number" maxFractionDigits = "2"/>
                                </td>
                                <td style="background-color: #B2EBF2;" title="งบที่ใช้จริง รวมทุกปีงบ" >
                                    <fmt:formatNumber value = "${budgetYear.sum_actualuse_all}" type = "number" maxFractionDigits = "2"/>
                                </td>
                                <c:forEach var="year" begin="${rangeYear.budpYearBegin}" end="${rangeYear.budpYearEnd}">                                
                                    <c:set var="sum_request" value="sum_request_${year}"/>
                                    <c:set var="sum_approve" value="sum_approve_${year}"/>
                                    <c:set var="sum_actualuse" value="sum_actualuse_${year}"/>
                                    <td title="งบที่เสนอรออนุมัติ ปี ${year}" style="background-color: #E0E0E0;"><fmt:formatNumber value = "${budgetYear[sum_request]}" type = "number" maxFractionDigits = "2"/></td>
                                    <td title="งบที่ได้รับ ปี ${year}" style="background-color: #CCFF90;"><fmt:formatNumber value = "${budgetYear[sum_approve]}" type = "number" maxFractionDigits = "2"/></td>
                                    <td title="งบที่ใช้จริง ปี ${year}" style="background-color: #B2EBF2;"><fmt:formatNumber value = "${budgetYear[sum_actualuse]}" type = "number" maxFractionDigits = "2"/></td>
                                </c:forEach>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </div>    
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $('#btnPrintPDF').on('click',function (){
            printElement('print-container');
        });        
    });
    function printElement(elem) {                
        var mywindow = window.open('', 'PRINT', 'height=400,width=1200px;');
        mywindow.document.write('<!DOCTYPE html><html><head><title>' + document.title + '</title>');        
        mywindow.document.write('</head><body >');
        mywindow.document.write('<h1>' + document.title + '</h1>');
        mywindow.document.write(document.getElementById(elem).innerHTML);
        mywindow.document.write('</body></html>');
        mywindow.document.close(); // necessary for IE >= 10
        mywindow.focus(); // necessary for IE >= 10*/
        mywindow.print();
        mywindow.close();
        return true;
    }
</script>
<jsp:include page="../include/inc_footer.jsp"/>
