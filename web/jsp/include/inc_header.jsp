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
        <link href="${context}/asset/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="${context}/asset/bootstrap/css/dashboard.css" rel="stylesheet" type="text/css" />
        <link href="${context}/asset/css/app-style.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="${context}/asset/js/jquery.2.2.4.js"></script>
        <script type="text/javascript" src="${context}/asset/bootstrap/js/bootstrap.js"></script>        
        <script type="text/javascript" src="${context}/asset/jquery-ui/jquery-ui.js"></script>    
        <script type="text/javascript" src="${context}/asset/js/app-core.js"></script>
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
    <body>

        <nav class="navbar navbar-inverse navbar-fixed-top" style="background-color: #64B5F6;">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#" style="color: #0B0F00;">Design and development of project management system for IT department ${menu}</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <c:if test="${empty EMPLOYEE}">
                            <li><a href="${context}/jsp/login.jsp?menu=login" style="color: #0B0F00;">Login</a></li>                            
                            </c:if>
                            <c:if test="${!empty EMPLOYEE}">
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false" style="color: #0B0F00;">ยินดีต้อนรับคุณ ${EMPLOYEE.username} <span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="${context}/jsp/authen/password.jsp">เปลี่ยนรหัสผ่าน</a></li>
                                    <li><a href="${context}/ChangeProfileServlet">แก้ไขข้อมูลส่วนตัว</a></li>
                                    <li role="separator" class="divider"></li>
                                    <li><a href="${context}/LogoutServlet">Sign Out</a></li>
                                </ul>
                            </li>
                        </c:if>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="container-fluid">
            <div class="row">
                <div class="col-sm-3 col-md-2 sidebar">
                    <c:if test="${empty EMPLOYEE}">
                        <ul class="nav nav-sidebar">
                            <li><a href="${context}/jsp/login.jsp?menu=login" style="color: #0B0F00;">Login</a></li>
                        </ul>
                    </c:if>
                    <c:if test="${!empty EMPLOYEE}">
                        <ul class="nav nav-sidebar">
                            <li class="<c:out value="${menu == 'dashboard' ? 'active': ''}"/>">
                                <a href="${context}/jsp/dashboard.jsp?menu=dashboard"><i class="glyphicon glyphicon-dashboard"></i> Dashboard <span class="sr-only">(current)</span></a>                            
                            </li>
                        </ul>
                        <ul class="nav nav-sidebar">
                            <li class="<c:out value="${menu == 'budget_plan' ? 'active': ''}"/>">                                
                                <a href="${context}/BudgetplanSearchServlet?menu=budget_plan"><i class="glyphicon glyphicon-inbox"></i> Manage BudgetPlan</a>
                            </li>
                            <li class="<c:out value="${menu == 'project' ? 'active': ''}"/>">                                
                                <a href="${context}/ProjectSearchServlet?menu=project"><i class="glyphicon glyphicon-bed"></i> Manage Project Master</a>
                            </li>
                            <li class="<c:out value="${menu == 'project_working' ? 'active': ''}"/>">                                
                                <a href="${context}/ProjectWorkingSearchServlet?menu=project-working"><i class="glyphicon glyphicon-play-circle"></i> Manage Project Working</a>
                            </li>
                            <li class="<c:out value="${menu == 'project_shift' ? 'active': ''}"/>">                                
                                <a href="#"><i class="glyphicon glyphicon-share"></i> Manage Project Shift</a>
                            </li>
                            <li class="<c:out value="${menu == 'project_expense' ? 'active': ''}"/>">                                
                                <a href="#"><i class="glyphicon glyphicon-usd"></i> Manage Project Expense</a>
                            </li>
                        </ul>   
                        <ul class="nav nav-sidebar">
                            <li class="<c:out value="${menu == 'task_assign' ? 'active': ''}"/>">                                
                                <a href="#"><i class="glyphicon glyphicon-import"></i> Manage Task Assign</a>
                            </li>
                            <li class="<c:out value="${menu == 'task_working' ? 'active': ''}"/>">                                
                                <a href="#"><i class="glyphicon glyphicon-saved"></i> Manage Task Working</a>
                            </li>
                        </ul>   
                        <ul class="nav nav-sidebar">
                            <li class="<c:out value="${menu == 'strategic' ? 'active': ''}"/>">                                
                                <a href="#"><i class="glyphicon glyphicon-flag"></i> Manage Strategic</a>
                            </li>
                            <li class="<c:out value="${menu == 'plan' ? 'active': ''}"/>">                                
                                <a href="#"><i class="glyphicon glyphicon-list-alt"></i> Manage Plan</a>
                            </li>
                            <li class="<c:out value="${menu == 'budget' ? 'active': ''}"/>">                                
                                <a href="#"><i class="glyphicon glyphicon-usd"></i> Manage Budget</a>
                            </li>
                            <li class="<c:out value="${menu == 'config' ? 'active': ''}"/>">                                
                                <a href="#"><i class="glyphicon glyphicon-cog"></i> Manage Config</a>
                            </li>
                            <li class="<c:out value="${menu == 'department' ? 'active': ''}"/>">                                
                                <a href="#"><i class="glyphicon glyphicon-oil"></i> Manage Department</a>
                            </li>
                            <li class="<c:out value="${menu == 'task' ? 'active': ''}"/>">                                
                                <a href="#"><i class="glyphicon glyphicon-hourglass"></i> Manage Task</a>
                            </li>
                        </ul>                       
                    </c:if>
                </div>
                <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">      
