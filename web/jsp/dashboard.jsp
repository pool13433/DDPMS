<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="include/inc_header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-lg-6">
            <div class="panel panel-ddpms">
                <div class="panel-heading">                    
                    งบประมาณประจำปี
                </div>
                <div class="panel-body">
                    <canvas id="chartBudgetInYear" width="400" height="400"></canvas>
                </div>
            </div>
        </div>
        <div class="col-lg-6">
            <div class="panel panel-ddpms">
                <div class="panel-heading">                    
                    แผนยุทธศาสตร์
                </div>
                <div class="panel-body">
                    <canvas id="chartGroupPlan" width="400" height="400"></canvas>
                </div>
            </div>
        </div>
    </div>
    
    <div class="row">
        <div class="col-lg-6">
            <div class="panel panel-ddpms">
                <div class="panel-heading">
                    <strong>Project Movement</strong>
                </div>
                <div class="panel-body">                        
                    <table id="search_table" class="table table-striped"> 
                        <thead style="background-color: #8c8c8c">
                            <tr>
                                <th>Project Status</th>
                                <th>Quality</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="sum" items="${sumStatusProjectList}">
                                <tr>
                                    <td>${sum.status}</td> 
                                    <td>${sum.sumProjectStatus}</td>
                                </tr>
                            </c:forEach>                            
                        </tbody>
                    </table>          
                </div>
            </div>
        </div>
        
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        var colors = ["#F44336", "#673AB7", "#9C27B0", "#673AB7", "#4CAF50", "#CDDC39", "#FFEB3B", "#FFC107", "#FF9800", "#FF5722", "#795548", "#9E9E9E"];
        $(function () {
            chartBudgetInYear();
            chartGroupPlan();
        });
        function chartBudgetInYear() {
            $.get('${context}/DatasetServlet?chartId=chartBudgetInYear', {year: '2017'}, function (data) {
                var labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
                var datas = [data['Jan'], data['Feb'], data['Mar'], data['Apr'], data['May'], data['Jun'], data['Jul'], data['Aug'], data['Sep'], data['Oct'], data['Nov'], data['Dec']];
                var data = {
                    labels: labels,
                    datasets: [{label: "Count Project In 2017", backgroundColor: colors,
                            borderColor: colors, borderWidth: 1, data: datas, }]
                };
                var ctx = $('#chartBudgetInYear');
                var myBarChart = new Chart(ctx, {
                    type: 'bar',
                    data: data,
                    options: {
                        scales: {
                            xAxes: [{
                                    stacked: true
                                }],
                            yAxes: [{
                                    stacked: true
                                }]
                        }
                    }
                });
            }, 'json');
        }
        function chartGroupPlan() {
            $.get('${context}/DatasetServlet?chartId=chartGroupPlan', {}, function (response) {
                var labels = [];
                var datas = [];
                $.each(response, function (index, value) {
                    labels.push(value.confName);
                    datas.push(value.confValue);
                });
                var data = {
                    labels: labels,
                    datasets: [
                        {data: datas,
                            backgroundColor: colors,
                            hoverBackgroundColor: colors
                        }]
                };
                var ctx = $('#chartGroupPlan');
                var myBarChart = new Chart(ctx, {
                    type: 'doughnut',
                    data: data,
                    options: {animation: {animateScale: true}}
                });
            }, 'json');
        }
    });
    
</script>
<jsp:include page="include/inc_footer.jsp"/>

