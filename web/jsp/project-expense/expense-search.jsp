<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<jsp:include page="../include/inc_header.jsp"/>
<div class="container">    
    <div class="panel panel-ddpms">                
        <div class="container-fluid text-center"><h4>Manage Expense Details</h4></div>
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
                <div class="form-group">
                    <label for="projId" class="col-sm-2 control-label">Project</label>
                    <div class="col-sm-10">
                        <select class="form-control" name="projId">
                            <option value="" selected>--select project--</option>                                        
                            <c:forEach items="${projectList}" var="p">
                                <c:choose>
                                    <c:when test="${criteria.projId == p.projId}">
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
                <div class="form-group">
                    <label for="expPr" class="col-sm-2 control-label">PO/PR Number</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" name="expPr" value="${criteria.expPr}"/>
                    </div>       
                    <label for="expVoch" class="col-sm-1 control-label">Voucher Number/Type</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="expVoch" value="${criteria.expVoch}"/>
                    </div>       
                    <label for="receipt" class="col-sm-1 control-label">Receipt</label>
                    <div class="col-sm-3">
                        <input type="text" class="form-control" name="receipt" value="${criteria.receipt}"/>
                    </div>       
                </div> 
                    <div class="form-group">
                    <label for="expAmount" class="col-sm-2 control-label">Amount</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control" name="expAmount" value="${criteria.expAmount}"/>
                    </div>       
                    <label for="expDateBegin" class="col-sm-1 control-label">Expense Date Start</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control datepicker" name="expDateBegin" value="${criteria.expDateBegin}"/>
                    </div>       
                    <label for="expDateEnd" class=" col-sm-offset-1 col-sm-1 control-label">Expense Date End</label>
                    <div class="col-sm-2">
                        <input type="text" class="form-control datepicker" name="expDateEnd" value="${criteria.expDateEnd}"/>
                    </div>       
                </div> 
                <div class="form-group">
                    <div class="col-sm-offset-6">
                        <button type="submit" class="btn btn-success">
                            <i class="glyphicon glyphicon-search"></i> Search
                        </button>
                        <a href="${context}/ProjectExpenseSearchServlet?menu=project_expense" class="btn btn-warning">
                            <i class="glyphicon glyphicon-erase"></i> Reset
                        </a>
                        <a href="${context}/ProjectExpenseFormServlet?menu=expense-form" class="btn btn-primary">
                            <i class="glyphicon glyphicon-plus"></i> Add
                        </a>

                    </div>
                </div>

            </form>
            <div class="row">
                <div class="col-md-12">
                    <c:import url="../include/inc_pagination.jsp"/>
                </div>
            </div>     
            <table id="search_table" class="table table-bordered table-responsive">                        
                <tr class="bg-info">          
                    <th>#</th>
                    <th>Project</th> 
                    <th>Desc</th> 
                    <th>Amount</th> 
                    <th>Voch</th>
                    <th>Pr/Po</th>
                    <th>Receipt</th>
                    <th>Date</th>                    
                </tr>
                </thead>
                <tbody>
                    <c:forEach var="expense" items="${expenseList}">
                        <tr>
                            <td  nowrap>        
                                <a href="${context}/ProjectExpenseFormServlet?menu=expense-form&expId=${expense.expId}" class="btn btn-default btn-info"><i class="glyphicon glyphicon-pencil"></i></a>
                                <a href="${context}/ProjectExpenseDeleteServlet?expId=${expense.expId}" onclick="return confirm('ยืนยันการลบข้อมูลนี้')" class="btn btn-default btn-danger" ><i class="glyphicon glyphicon-trash"></i></a>
                            </td>
                            <td>${expense.projId}</td>
                            <td>${expense.expDesc}</td>
                            <td>${expense.expAmount}</td>
                            <td>${expense.expVoch}</td>
                            <td>${expense.expPr}</td>
                            <td>${expense.receipt}</td>
                            <td>${expense.expDate}</td>                            
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="row">
                <div class="col-md-12">
                    <c:import url="../include/inc_pagination.jsp"/>
                </div>
            </div>     

        </div>        
    </div>        
</div>   

<script type="text/javascript">

    $(document).ready(function () {
        colsole.log("log:" +${"#{expenseTotalSumList}"});



    });
</script>

<jsp:include page="../include/inc_footer.jsp"/>
