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
            <input type="hidden" id="id" name="id" value="${proj_id}"/>
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
                            <input class="form-control" type="text" name="account" id="account" value="${account}" placeholder="Account..." required>
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
                        <label for="budp_id" class="col-sm-2 control-label">Budget Plan</label>                        
                        <div class="col-sm-8">
                            <select class="form-control" id="budp_id" name="budp_id" >
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
                <c:if test="${projectWorkingList != null}">
                    <div class="row col-sm-offset-1">
                        <div class="col-sm-10" ><label class="col-sm-3 control-label"><h6>งบประมาณรายปี</h6></label></div>
                        <div class="col-sm-11" >                            
                            <table class="table"> 
                                <tbody>
                                    <c:forEach items="${projectWorkingList}" var="i">
                                        <tr style="align-content: center">
                                        <td rowspan="2" style="vertical-align: middle">งบประมาณปี${i.budgetYear}<input type="hidden" id="yearStart+${i}" name="yearStart_edit" value="${i.budgetYear}"/></td>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m1" value="${i.budgetRequestM1}" placeholder="JAN"></td>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m2" value="${i.budgetRequestM2}" placeholder="FEB"></td>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m3" value="${i.budgetRequestM3}" placeholder="MAR"></td>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m4" value="${i.budgetRequestM4}" placeholder="APR"></td>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m5" value="${i.budgetRequestM5}" placeholder="MAY"></td>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m6" value="${i.budgetRequestM6}" placeholder="JUN"></td>
                                        </tr>
                                        <tr>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m7" value="${i.budgetRequestM7}" placeholder="JUL"></td>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m8" value="${i.budgetRequestM8}" placeholder="AUG"></td>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m9" value="${i.budgetRequestM9}" placeholder="SEP"></td>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m10" value="${i.budgetRequestM10}" placeholder="OCT"></td>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m11" value="${i.budgetRequestM11}" placeholder="NOV"></td>
                                        <td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m12" value="${i.budgetRequestM12}" placeholder="DEC"></td>                                      
                                        </tr>
                                    </c:forEach>                                  
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
            <table style="align-content: center"></table>
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
        $("#addProject").submit(function () {
            var budp_id = $("#budp_id");

            if ("" === budp_id.val()) {
                alert('กรุณาเลือก Budget Plan');
                return false;
            }
        });

        $('select[id="budp_id"]').change(function (event) {
            
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
                        '<table class="table"><tbody>'+
                        '<tr style="align-content: center">'+
                        '<td rowspan="2" style="vertical-align: middle">งบประมาณปี '+ years + '</td>' +
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m1" placeholder="JAN"></td>'+
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m2" placeholder="FEB"></td>'+
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m3" placeholder="MAR"></td>'+
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m4" placeholder="APR"></td>'+
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m5" placeholder="MAY"></td>'+
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m6" placeholder="JUN"></td>'+
                        '</tr>'+
                        '<tr>'+
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m7" placeholder="JUL"></td>'+
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m8" placeholder="AUG"></td>'+
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m9" placeholder="SEP"></td>'+
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m10" placeholder="OCT"></td>'+
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m11" placeholder="NOV"></td>'+
                        '<td><input class="form-control input-sm" type="text" name="budget_request" id="budget_request_m12" placeholder="DEC"></td>'+
                        '</tr></tbody></table>'+
                        '</div>' +
                        '</div>'

                        );            
            }
            return false;
        });
        
          if($("#id").val() !== ""){
            $("#budp_id").attr('disabled','disabled');
        }
        
       
    });
</script>
<jsp:include page="../include/inc_footer.jsp"/>
