<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">        
        <div class="panel-heading">Manage Expense Details</div>
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
            <form id="searchBudp" method="get" action="${context}/ProjectExpenseSearchServlet" class="form-horizontal">          
                <input type="hidden" id="menu" name="menu" value="searching"/>
                <div class="container">
                    <div class="row">
                        <div class="col-sm-10" >
                            <div class="form-group">
                                <label for="proj_id" class="col-sm-2 control-label">Project</label>
                                <div class="col-sm-8">
                                    <select class="form-control" id="proj_id" name="proj_id" placeholder="proj_id" >
                                        <option value="" selected>--select project--</option>                                        
                                        <c:forEach items="${projectList}" var="p">
                                            <c:choose>
                                                <c:when test="${proj_id == p.projId}">
                                                    <option value="${p.projId}" selected>${p.projName}</option>
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
                </div>                  
                
                <div class="row">
                    <div class="form-group">
                        <div class="col-sm-offset-6">
                            <button type="submit" class="btn btn-success">
                                <i class="glyphicon glyphicon-search"></i> Search
                            </button>
                            <a href="${context}/ProjectExpenseSearchServlet?menu=budget-plan" class="btn btn-warning">
                                <i class="glyphicon glyphicon-erase"></i> Reset
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
            <form id="expList" action="${context}/ProjectExpenseSearchServlet"   method="post" class="form-horizontal">
                <input type="hidden" id="menu" name="menu" value="manage"/>
                <div class="table-responsive" style="overflow-y: scroll;max-height: 400px;">                    
                    <table id="search_table" class="table table-bordered table-responsive">                        
                        <tr class="bg-info">                       
                            <th>Project</th> 
                            <th>Amount</th>
                            <th>#</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="e" items="${expenseList}">
                            <tr>
                                <td>${e.projId}</td>
                                <td>${e.expAmount}</td>
                                <td  nowrap class="col-md-2">  
                                    <a href="${context}/ProjectExpenseAddServlet?menu=budget-plan-form&proj_id=${e.projId}" class="btn btn-default btn-primary">
                                        <i class="glyphicon glyphicon-plus"></i> Add Expense
                                    </a>                                
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${expenseList.isEmpty()}">
                            <tr>                    
                                <td colspan="15"><div class="alert"><span style="padding: 40%">ไม่พบข้อมูลที่ค้นหา</span></div> </td>
                            </tr>
                        </c:if>
                        <c:if test="${expenseList == null}">
                            <tr>                    
                                <td colspan="17"><div class="alert"><span style="padding: 40%">กรุณาระบุเงื่อนไขในการค้นหา</span></div> </td>
                            </tr>
                        </c:if>
                    </tbody>
                    </table>
                    <c:if test="${expenseTotalSumList != null}">
                        <c:forEach var="s" items="${expenseTotalSumList}">
                            <div class="panel panel-info">
                                <div class="panel-heading col-sm-12 form-horizontal">
                                    <div class="col col-sm-6">
                                        Project
                                    </div>
                                    <div class="col col-sm-3">
                                        Total Amount
                                    </div>
                                    <div class="col col-sm-3">
                                        <a href="${context}/ProjectExpenseAddServlet?menu=expense-form&proj_id=${s.projId}" class="btn btn-default btn-primary">
                                            <i class="glyphicon glyphicon-plus"></i> Add Expense
                                        </a>  
                                    </div>
                                </div>                                
                                <div class="panel-body">
                                    <div class="col col-md-12 form-horizontal">
                                        <div class="col col-md-6">
                                            <a href="${context}/ProjectExpenseSearchServlet?menu=list_expense&proj_id=${s.projId}" class="text-info">${s.projName}</a> 
                                        </div>
                                        <div class="col col-md-3">
                                            ${s.expAmount}
                                        </div>
                                        <div class="col col-md-3">
                                            <span class="text-left small">Last update : ${s.modifiedDate}</span>
                                        </div>
                                    </div>
                                
                                </div>                                    
                            </div>
                        </c:forEach>
                    </c:if>
                    
                    
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
        colsole.log("log:"+${"#{expenseTotalSumList}"});
        
        
        
    });
</script>

<jsp:include page="../include/inc_footer.jsp"/>
