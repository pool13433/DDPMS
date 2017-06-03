<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />
<c:set var="menu" value="${param.menu}"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ระบบสารสนเทศเพื่อการจัดการโครงการแผนกสารสนเทศ (Design and development of project management system for IT department)</title>        
        <link href='https://fonts.googleapis.com/css?family=Marmelad' rel="stylesheet" type="text/css">
        <link href="${context}/asset/jquery-ui/jquery-ui.css" rel="stylesheet" type="text/css">        
        <link href="${context}/asset/flat-ui/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />         
        <link href="${context}/asset/flat-ui/css/flat-ui.css" rel="stylesheet" type="text/css" />           
        <link href="${context}/asset/css/app-style.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${context}/asset/js/jquery.2.2.4.js"></script>        
        <script type="text/javascript" src="${context}/asset/flat-ui/js/bootstrap.min.js"></script>  
        <script type="text/javascript" src="${context}/asset/chart.js/Chart.min.js"></script>                   
        <script type="text/javascript" src="${context}/asset/jquery-ui/jquery-ui.js"></script>    
        <script type="text/javascript" src="${context}/asset/js/app-core.js"></script>        
          
        <link href="${context}/asset/flat-ui/bootstrap/css/bootstrap-multiselect.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${context}/asset/flat-ui/bootstrap/js/bootstrap-multiselect.js"></script>
        <style type="text/css">
            body *{font-family: 'Marmelad', sans-serif;}
            a{color: #0B0F00;font-weight: bold}
            /*boostrap checkbox*/
            .btn span.glyphicon{
                opacity: 0;
            }
            .btn.active span.glyphicon{
                opacity: 1;
            }
        </style>
    </head>
    <body style="background-color: #F8F8F8;">
        
        <!-- http://designmodo.github.io/Flat-UI/docs/components.html#fui-pagination -->
        <nav class="navbar navbar-default navbar-fixed-top" style="background-color: #FB7B55;">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">DDPMS</a>
                </div>
                <ul class="nav navbar-nav">           
                    <li class="<c:out value="${menu == 'dashboard' ? 'active': ''}"/>">
                        <a href="${context}/DashboardServlet?menu=dashboard"><i class="glyphicon glyphicon-dashboard"></i> Dashboard <span class="sr-only">(current)</span></a>                            
                    </li>
                    <c:if test="${!empty EMPLOYEE}">                        
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Budget
                                <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li class="<c:out value="${menu == 'budget_plan' ? 'active': ''}"/>">                                
                                    <a href="${context}/BudgetplanSearchServlet?menu=budget_plan"><i class="glyphicon glyphicon-inbox"></i> Manage BudgetPlan</a>
                                </li>
                                <li class="<c:out value="${menu == 'project' ? 'active': ''}"/>">                                
                                    <a href="${context}/ProjectSearchServlet?menu=project"><i class="glyphicon glyphicon-list"></i> Manage Project Master</a>
                                </li>
                               <!-- <li class="<c:out value="${menu == 'project_working' ? 'active': ''}"/>">                                
                                    <a href="${context}/ProjectWorkingSearchServlet?menu=project-working"><i class="glyphicon glyphicon-play-circle"></i> Manage Project Working</a>
                                </li>
                                <li class="<c:out value="${menu == 'project_shift' ? 'active': ''}"/>">                                
                                    <a href="#"><i class="glyphicon glyphicon-share"></i> Manage Project Shift</a>
                                </li>-->                                
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Task
                                <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li class="<c:out value="${menu == 'task_assign' ? 'active': ''}"/>">                                
                                    <a href="${context}/TaskAssignListServlet?menu=task_assign"><i class="glyphicon glyphicon-import"></i> Manage Task Assign</a>
                                </li>
                                <li class="<c:out value="${menu == 'task_work' ? 'active': ''}"/>">                                
                                    <a href="${context}/TaskWorkListServlet?menu=task_work"><i class="glyphicon glyphicon-saved"></i> Manage Task Working</a>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Setting
                                <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li class="<c:out value="${menu == 'strategic' ? 'active': ''}"/>">                                
                                    <a href="${context}/StrategicSearchServlet?menu=strategic"><i class="glyphicon glyphicon-flag"></i> Manage Strategic</a>
                                </li>
                                <li class="<c:out value="${menu == 'plan' ? 'active': ''}"/>">                                
                                    <a href="${context}/PlanSearchServlet?menu=plan"><i class="glyphicon glyphicon-list-alt"></i> Manage Plan</a>
                                </li>
                                <li class="<c:out value="${menu == 'project-type' ? 'active': ''}"/>">                                
                                    <a href="${context}/ProjectTypeListServlet?menu=project-type"><i class="glyphicon glyphicon-usd"></i> Manage Project Type</a>
                                </li>
                                <li class="<c:out value="${menu == 'config' ? 'active': ''}"/>">                                
                                    <a href="${context}/ConfigListServlet?menu=config"><i class="glyphicon glyphicon-cog"></i> Manage Config</a>
                                </li>
                                <li class="<c:out value="${menu == 'department' ? 'active': ''}"/>">                                
                                    <a href="${context}/DepartmentListServlet?menu=department"><i class="glyphicon glyphicon-oil"></i> Manage Department</a>
                                </li>
                                <li class="<c:out value="${menu == 'task' ? 'active': ''}"/>">                                
                                    <a href="${context}/TaskListServlet?menu=task"><i class="glyphicon glyphicon-hourglass"></i> Manage Task</a>
                                </li>
                                <li class="<c:out value="${menu == 'shift' ? 'active': ''}"/>">                                
                                    <a href="${context}/ProjectShiftSearchServlet?menu=shift"><i class="glyphicon glyphicon-arrow-right"></i> Project Shift</a>
                                </li>
                                <li class="<c:out value="${menu == 'project_expense' ? 'active': ''}"/>">                                
                                    <a href="${context}/ProjectExpenseSearchServlet?menu=project_expense"><i class="glyphicon glyphicon-usd"></i> Manage Project Expense</a>
                                </li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Report
                                <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li class="<c:out value="${menu == 'report-overview' ? 'active': ''}"/>">                                
                                    <a href="${context}/ReportOverviewServlet?menu=report-overview"><i class="glyphicon glyphicon-flag"></i> Report Overview</a>
                                </li>
                            </ul>
                        </li>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:if test="${empty EMPLOYEE}">
                        <li><a href="${context}/jsp/login.jsp?menu=login" style="color: #0B0F00;">Login</a></li>                            
                        </c:if>
                        <c:if test="${!empty EMPLOYEE}">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" style="color: #0B0F00;">ยินดีต้อนรับคุณ ${EMPLOYEE.username} <span class="caret"></span></a>
                            <ul class="dropdown-menu">
                                <li><a href="${context}/jsp/authen/password.jsp">Change Password</a></li>
                                <li><a href="${context}/ChangeProfileServlet">Change Profile</a></li>
                                <li role="separator" class="divider"></li>
                                <li><a href="${context}/LogoutServlet">Sign Out</a></li>
                            </ul>
                        </li>
                    </c:if>
                </ul>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row" style="margin-top: 80px;">
                <div class="col-sm-12 col-md-12 col-lg-12">      
