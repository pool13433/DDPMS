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
        </div>
            <form id="searching" method="get" action="${context}/ProjectExpenseSearchServlet" class="form-horizontal">          
                <input type="hidden" id="menu" name="menu" value="searching"/>
                
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
                <div class="panel panel-info">
                    <div class="panel-heading ">${proj_name}</div>  
                </div>                
                <div class="table-responsive" style="overflow-y: scroll;max-height: 400px;">      
                    
                    <table id="search_table" class="table table-bordered table-responsive">                        
                        <tr class="bg-info">  
                            <th>Description</th>
                            <th>Date</th> 
                            <th>Amount</th>
                            <th>PR/PO</th> 
                            <th>Voucher</th>
                            <th>Receipt</th> 
                            <th>Vendor</th>
                            <th>ModifiedBy</th> 
                            <th>ModifiedDate</th>                            
                            
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="e" items="${expenseList}">
                            <tr>
                                <td>${e.expDesc}</td>
                                <td>${e.expDate}</td>
                                <td>${e.expAmount}</td>
                                <td>${e.expPr}</td>
                                <td>${e.expVoch}</td>
                                <td>${e.receipt}</td>
                                <td>${e.vender}</td>
                                <td>${e.modifiedBy}</td>
                                <td>${e.modifiedDate}</td>                                
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
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="col-md-10"></div>
                        <div class="col-md-2">
                            <a href="${context}/ProjectExpenseAddServlet?menu=expense-form&proj_id=${proj_id}" class="btn btn-default btn-primary">
                                <i class="glyphicon glyphicon-plus"></i> Add Expense
                            </a> 
                        </div>                        
                    </div>
                </div>
                               
                <div class="row">
                    <div class="col-md-12">
                        <c:import url="../include/inc_pagination.jsp"/>
                    </div>
                </div>     
            </form>
    </div>        
</div>   

<script type="text/javascript">

    $(document).ready(function () {
        colsole.log("log:"+${"#{expenseTotalSumList}"});
        
        
        
    });
</script>

<jsp:include page="../include/inc_footer.jsp"/>
