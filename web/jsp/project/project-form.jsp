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
        <form id="addProject" action="${context}/ProjectAddServlet" method="post" class="form-horizontal" >
            <input type="hidden" name="verifyCase">
            <input type="hidden" id="id" name="id" value="${proj_id}"/>
            <input type="hidden" id="proj_status" name="proj_status" value="${proj_status}"/>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="proj_name" class="col-sm-2 control-label">Project Name</label>
                        <div class="col-sm-8">
                            <input class="form-control" type="text" name="proj_name" id="proj_name" value="${proj_name}" placeholder="project name..." required>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="account" class="col-sm-2 control-label">Account</label>
                        <div class="col-sm-3">
                            <input class="form-control" type="number" name="account" id="account" value="${account}" placeholder="Account..." required>
                        </div>                                                
                    </div>
                </div>                        
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="prot_id" class="col-sm-2 control-label">Project Type</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="prot_id" name="prot_id" >
                                <c:forEach items="${projectTypeList}" var="p">                            
                                    <c:choose>
                                        <c:when test="${prot_id == p.protId}">
                                            <option value="${p.protId}" selected>${p.protName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${p.protId}">${p.protName}</option>
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
                        <label for="details" class="col-sm-2 control-label">Project Details</label>
                        <div class="col-sm-8">                            
                            <textarea class="form-control" rows="3" id="details" name="details" value="${details}">${details}</textarea>
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
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="strategic" class="col-sm-2 control-label">Strategic</label>                        
                        <div class="col-sm-8">
                            <select class="form-control" id="strategic" name="strategic" multiple>
                                <c:forEach items="${strategicList}" var="s">                            
                                    <c:choose>
                                        <c:when test="${stra_id == s.straId}">
                                            <option value="${s.straId}" selected>${s.straName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${s.straId}">${s.straName}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>    
                            </select>
                        </div> 
                    </div>
                </div>
                <input type="hidden" id="stra_id" name="stra_id" value="${stra_id}">
            </div>            
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="budgetPlan" class="col-sm-2 control-label">Budget Plan</label>                        
                        <div class="col-sm-8">
                            <select class="form-control" id="budgetPlan" name="budgetPlan" >
                                <option value="" selected>    --กรุณาเลือก--  </option>
                                <c:forEach items="${budgetPlanList}" var="bp">                            
                                    <c:choose> 
                                        <c:when test="${budp_id == bp.budpId}">
                                            <option value="${bp.budpId}" yearS="${bp.budpYearBegin }" yearT="${bp.budpYearEnd}" selected>${bp.budpName}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${bp.budpId}" yearS="${bp.budpYearBegin }" yearT="${bp.budpYearEnd}">${bp.budpName}</option>                                            
                                        </c:otherwise>
                                    </c:choose>                                          
                                </c:forEach>    
                            </select>                            
                        </div>  
                        <input type="hidden" id="budp_id" name="budp_id" value="${budp_id}">
                    </div>
                </div>                        
            </div>
            <div class="row" id="formBudget">
               
                <c:set var="sizePjWorking" value="0" />
                <c:if test="${projectWorkingList != null}">
                    <div class="row col-sm-offset-1">
                        <div class="col-sm-10" ><label class="col-sm-3 control-label"><h6>งบประมาณรายปี</h6></label></div>
                        <div class="col-sm-11" >          
                            <input type="hidden" id="loop" name="loop" />
                            <table class="table"> 
                                <tbody>
                                    <c:forEach items="${projectWorkingList}" var="i" varStatus="count">
                                        <c:set var="sizePjWorking" value="${count.count}" />
                                        <tr style="align-content: center">
                                        <td rowspan="2" style="vertical-align: middle">งบประมาณปี${i.budgetYear}<input type="hidden" id="yearStart+${i}" name="yearStart_edit" value="${i.budgetYear}"/></td>
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m1" value="${i.budgetRequestM1}" placeholder="JAN" data-id="${i.budgetYear}_m1"></td>
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m2" value="${i.budgetRequestM2}" placeholder="FEB" data-id="${i.budgetYear}_m2"></td>
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m3" value="${i.budgetRequestM3}" placeholder="MAR" data-id="${i.budgetYear}_m3"></td>
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m4" value="${i.budgetRequestM4}" placeholder="APR" data-id="${i.budgetYear}_m4"></td>
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m5" value="${i.budgetRequestM5}" placeholder="MAY" data-id="${i.budgetYear}_m5"></td>
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m6" value="${i.budgetRequestM6}" placeholder="JUN" data-id="${i.budgetYear}_m6"></td>
                                        </tr>
                                        <tr id="${i.budgetYear}">
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m7" value="${i.budgetRequestM7}" placeholder="JUL" data-id="${i.budgetYear}_m7"></td>
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m8" value="${i.budgetRequestM8}" placeholder="AUG" data-id="${i.budgetYear}_m8"></td>
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m9" value="${i.budgetRequestM9}" placeholder="SEP" data-id="${i.budgetYear}_m9"></td>
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m10" value="${i.budgetRequestM10}" placeholder="OCT" data-id="${i.budgetYear}_m10"></td>
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m11" value="${i.budgetRequestM11}" placeholder="NOV" data-id="${i.budgetYear}_m11"></td>
                                        <td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m12" value="${i.budgetRequestM12}" placeholder="DEC" data-id="${i.budgetYear}_m12"></td>                                      
                                        </tr>
                                    </c:forEach>                                  
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
            <table style="align-content: center"></table>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="totalBudget" class="col-sm-3 control-label">งบประมาณรวมทั้งหมด </label>
                        <div class="col-sm-3">
                            <input class="form-control" type="number" name="totalBudget" id="totalBudget" value="${totalBudget}" disabled>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-10" >
                    <div class="form-group">
                        <label for="remarks" class="col-sm-2 control-label">Remarks <span style="color: red;">*</span></label>
                        <div class="col-sm-8">
                            <textarea class="form-control" name="remarks" id="remarks"></textarea>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-1 col-sm-10">
                    <button type="submit" class="btn btn-success">Save</button>
                    <button type="reset" class="btn btn-warning">Reset</button>
                    <c:if test="${isCancel==true}">
                        <button type="button" class="btn btn-danger" id="btn-cancel">Cancel Project</button>
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
<script type="text/javascript">
    var verifyCaseLabel = {'APPROVE': 'อนุมัติ', 'REJECT': 'ไม่อนุมัติ', 'CANCEL': 'ยกเลิก'};
    $(document).ready(function () {
        $("#strategic").multiselect();
        var strategic = $('#stra_id').val(), i=0 , size = strategic.length;
        var straArr = JSON.stringify(strategic);       
         if(strategic !== ""){
            for( i ; i<size ; i++){
                $("#strategic").find("option[value='"+straArr[i]+"']").attr("selected",1); 
                $("#strategic").multiselect("refresh");            
            }
        }       
        
        $('#btn-cancel').on('click', function () {
            var verifyReason = $('textarea[name="remarks"]').val();
            var projId = $('#id').val();
            if (verifyReason == '') {
                alert('กรุณาระบุเหตุการยกเลิกโครงการ');
                return false;
            }else{
                var isConfirm = confirm('ยืนยันการ ยกเลิก ใช่หรือไม่ ใช่[ตกลง] || ไม่ใช่[ยกเลิก]');
                if (isConfirm) {
                    $('body').append($('<form/>')
                    .attr({'action': '${context}/ProjectVerifyServlet', 'method': 'post', 'id': 'addProject'})
                    .append($('<input/>')
                      .attr({'type': 'hidden', 'name': 'verifyCase', 'value': "CANCEL"})
                    )
                    .append($('<input/>')
                      .attr({'type': 'hidden', 'name': 'projId', 'value': projId})
                    )
                    .append($('<input/>')
                      .attr({'type': 'hidden', 'name': 'remarks', 'value': verifyReason})
                    )
                  ).find('#addProject').submit();
                } else {
                    return false;
                }
            }            
        });
        
        $("#addProject").submit(function () {
            var budgetPlan = $("#budgetPlan");

            if ("" === budgetPlan.val()) {
                alert('กรุณาเลือก Budget Plan');
                return false;
            }
        });

        $('select[id="budgetPlan"]').change(function (event) {
            
            event.preventDefault();
            var yearS = $('option:selected', this).attr('yearS');
            var yearT = $('option:selected', this).attr('yearT');
            var loop = yearT - yearS;
            var formBudget = $("#formBudget");
            formBudget.find("div").remove();
            formBudget.append('<div class="row"><div class="col-sm-10" ><label class="col-sm-3 control-label"><h6>งบประมาณรายปี</h6></label></div></div>');
            formBudget.append('<input type="hidden" id="yearStart" name="yearStart" value="' + yearS + '"/>');
            for (var i = 0; i <= loop; i++) {
                 var years = (yearS++);
                    formBudget.append(
                        '<div class="row col-sm-offset-1">' +
                        '<div class="col-sm-11" >' +
                        '<input type="hidden" id="loop" name="loop" value="'+loop+'"/>'+
                        '<table class="table"><tbody>'+
                        '<tr style="align-content: center">'+
                        '<td rowspan="2" style="vertical-align: middle">งบประมาณปี '+ years + '</td>' +
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m1" placeholder="JAN" data-id="'+years+'_m1" onblur="calculator()" ></td>'+
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m2" placeholder="FEB" data-id="'+years+'_m2" onblur="calculator()" ></td>'+
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m3" placeholder="MAR" data-id="'+years+'_m3" onblur="calculator()"></td>'+
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m4" placeholder="APR" data-id="'+years+'_m4" onblur="calculator()"></td>'+
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m5" placeholder="MAY" data-id="'+years+'_m5" onblur="calculator()"></td>'+
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m6" placeholder="JUN" data-id="'+years+'_m6" onblur="calculator()"></td>'+
                        '</tr>'+
                        '<tr>'+
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m7" placeholder="JUL" data-id="'+years+'_m7" onblur="calculator()"></td>'+
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m8" placeholder="AUG" data-id="'+years+'_m8" onblur="calculator()"></td>'+
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m9" placeholder="SEP" data-id="'+years+'_m9" onblur="calculator()"></td>'+
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m10" placeholder="OCT" data-id="'+years+'_m10" onblur="calculator()"></td>'+
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m11" placeholder="NOV" data-id="'+years+'_m11" onblur="calculator()"></td>'+
                        '<td><input class="form-control input-sm" type="number" name="budget_request" id="budget_request_m12" placeholder="DEC" data-id="'+years+'_m12" onblur="calculator()"></td>'+
                        '</tr></tbody></table>'+
                        '</div>' +
                        '</div>'

                        );            
            }
            return false;
        });
        
        if($("#id").val() !== ""){
          $("#budgetPlan").attr('disabled','disabled');
        }
        
        $('#formBudget').find('input').focusout(function () {
            calculatorBudgetTotal();
        });
        
        //For Edit
        function calculatorBudgetTotal(){
            var budget_year = $("#budgetPlan").find('option:selected').attr('yearS');           
            var size = ${sizePjWorking};
            var budgetTotal = 0;
            for (var loop = 1; loop <= size; loop++) {   
                for (var i = 1; i < 13; i++) {
                    //var name = "budget_request_m"+ i;
                    var req = budget_year+"_m"+i;
                    //console.log('name ::=='+name);
                    var budgetM =$("#formBudget").find('input[data-id="' + req + '"]').val();
                    //console.log('budgetM ::=='+budgetM );
                    budgetTotal += parseInt(((budgetM != '' && budgetM != undefined) ? budgetM : 0));         
                }
                //console.log("____________________________");
                //console.log('budgetTotal ::=='+budgetTotal);
                budget_year = (parseInt(budget_year)+1);
            }
            budgetTotal = budgetTotal.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"); 
            $('input[name="totalBudget"]').val(budgetTotal);
        }
    });
    //for Add
    function calculator(){
        var budget_year = $("#budgetPlan").find('option:selected').attr('yearS');           
        var size = $("#loop").val();
        var budgetTotal = 0;
            for (var l = 0;  l<= size; l++) {   
                for (var i = 1; i < 13; i++) {
                    var req = budget_year+"_m"+i;
                    var budgetM =$("#formBudget").find('input[data-id="' + req + '"]').val();
                    budgetTotal += parseInt(((budgetM != '' && budgetM != undefined) ? budgetM : 0)); 
                }
                budget_year = (parseInt(budget_year)+1);
            }
        budgetTotal = budgetTotal.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,"); 
        $('input[name="totalBudget"]').val(budgetTotal);
    }
    
</script>
<jsp:include page="../include/inc_footer.jsp"/>
