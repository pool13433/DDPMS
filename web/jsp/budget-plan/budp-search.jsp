<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">                
        <div class="container-fluid text-center"><h4>Manage Budget Plan</h4></div>
        <div class="panel-body">
             <!-- Alert Message -->
            <c:if test="${!empty MessageUI}">
                <div class="alert alert-${MessageUI.cssClass} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong>${MessageUI.title}!</strong> ${MessageUI.message}
                </div>
                <c:remove var="MessageUI" scope="session" />
            </c:if>            
            <!-- Alert Message -->
            <form id="searchBudp" method="get" action="${context}/BudgetplanSearchServlet" class="form-horizontal">          
                <input type="hidden" id="menu" name="menu" value="searching"/>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-10" >
                            <div class="form-group">
                                <label for="budp_name" class="col-sm-2 control-label">Budget Plan Name</label>
                                <div class="col-sm-8">
                                    <input class="form-control" type="text" name="budp_name" id="budp_name" value="${budp_name}" placeholder="budget plan name..." >
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-10" >
                            <div class="form-group">
                                <label for="budp_begin" class="col-sm-2 control-label">Budget Plan Begin</label>
                                <div class="col-sm-3">
                                    <input class="form-control" type="text" name="budp_begin" id="budp_begin" value="${budp_begin}" placeholder="Begin..." >
                                </div>
                                <label for="budp_end" class="col-sm-2 control-label">Budget Plan End</label>
                                <div class="col-sm-3">
                                    <input class="form-control" type="text" name="budp_end" id="budp_end" value="${budp_end}" placeholder="End..." >
                                </div>
                            </div>
                        </div>                        
                    </div>
                </div>                  
                
                <div class="row">
                    <div class="form-group">
                        <div class="col-sm-offset-6">
                            <button type="submit" class="btn btn-success">
                                <i class="glyphicon glyphicon-search"></i> Search
                            </button>
                            <a href="${context}/BudgetplanSearchServlet?menu=budget-plan" class="btn btn-warning">
                                <i class="glyphicon glyphicon-erase"></i> Reset
                            </a>
                            <a href="${context}/BudgetplanAddServlet?menu=budget-plan-form" class="btn btn-default btn-primary">
                                <i class="glyphicon glyphicon-plus"></i> Add
                            </a>
                        </div>
                    </div>
                </div>
                   
            </form>
            <div class="row">
                <div class="col-md-12">
                    <c:import url="../include/inc_pagination.jsp"/>
                </div>
            </div>     
            <div id="msgBox" class="alert alert-warning" hidden="">
                <strong>Warning! </strong><text id="msg" name="msg" value=""></text>
            </div>
            <form id="budpList" action="${context}/BudgetplanSearchServlet"   method="post" class="form-horizontal">
                <input type="hidden" id="menu" name="menu" value="manage"/>
                <div style="overflow-y: scroll;max-height: 400px;">                    
                    <table id="search_table" class="table table-bordered table-striped">                        
                        <tr>
                        <th>#</th>
                        <th>Budget plan name</th> 
                        <th>Budget plan begin</th>
                        <th>Budget plan end</th>
                        <th>Modified By</th>
                        <th>Modified Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="bp" items="${budgetPlanList}">
                        <tr>
                            <td  nowrap>        
                                <a href="${context}/BudgetplanEditServlet?id=${bp.budpId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                <a href="${context}/BudgetplanDeleteServlet?id=${bp.budpId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                            </td>
                            <td>${bp.budpName}</td>
                            <td>${bp.budpYearBegin}</td> 
                            <td>${bp.budpYearEnd}</td>
                            <td>${bp.modifiedBy}</td>
                            <td>${bp.modifiedDate}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${budgetPlanList.isEmpty()}">
                        <tr>                    
                            <td colspan="15"><div class="alert"><span style="padding: 40%">ไม่พบข้อมูลที่ค้นหา</span></div> </td>
                        </tr>
                    </c:if>
                    <c:if test="${budgetPlanList == null}">
                        <tr>                    
                            <td colspan="17"><div class="alert"><span style="padding: 40%">กรุณาระบุเงื่อนไขในการค้นหา</span></div> </td>
                        </tr>
                    </c:if>
                </tbody>
                    </table>
                </div>                
                <div class="row">
                    <div class="col-md-12">
                        <c:import url="../include/inc_pagination.jsp"/>
                    </div>
                </div>     
            </form>

        </div>        
    </div>        
</div>   

<script type="text/javascript">

    $(document).ready(function () {
        
        
        
        
    });
</script>

<jsp:include page="../include/inc_footer.jsp"/>
