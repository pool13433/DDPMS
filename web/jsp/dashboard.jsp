<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:include page="include/inc_header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-lg-6">
            <div class="panel panel-ddpms">
                <div class="panel-heading">                    
                    งบประมาณประจำปี <%= new java.text.SimpleDateFormat("yyyy").format(new java.util.Date()) %>
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
</div>
<script type="text/javascript">
    var colors = ["#F44336", "#673AB7", "#9C27B0", "#673AB7", "#4CAF50", "#CDDC39", "#FFEB3B", "#FFC107", "#FF9800", "#FF5722", "#795548", "#9E9E9E"];
    var colors2 = ["#D32F2F", "#512DA8", "#7B1FA2", "#512DA8", "#388E3C", "#AFB42B", "#FBC02D", "#FFA000", "#F57C00", "#E64A19", "#5D4037", "#616161"];
    var colors3 = ["#B71C1C", "#311B92", "#4A148C", "#311B92", "#1B5E20", "#827717", "#F57F17", "#FF6F00", "#E65100", "#BF360C", "#3E2723", "#212121"];
    $(function () {
        chartBudgetInYear();
        chartGroupPlan();
    });
    function chartBudgetInYear() {
        $.get('${context}/DatasetServlet?chartId=chartBudgetInYear', {year: '2017'}, function (snapshot) {
            console.log('snapshort : ', snapshot);
            var datasets = [];
            var labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
            var dataStatus = [
                {name : 'ALL',color : '#F44336', label: 'ทั้งหมด'},
                {name : 'COMPLETE',color : '#E91E63', label: 'เรียบร้อย'},
                {name : 'ADJUST',color : '#9C27B0', label: 'ปรับเปลี่ยน'},
                {name : 'CANCEL',color : '#673AB7', label: 'ยกเลิก'},
                {name : 'SUPPORT',color : '#3F51B5', label: 'สนับสนุน'},
                {name : 'INPLAN',color : '#2196F3', label: 'อยู่ในแผน'},
                {name : 'PROCESSING',color : '#03A9F4', label: 'กำลังดำเนินการ'},
                {name : 'WAITING',color : '#00BCD4', label: 'รออนุมัติ'},                
                {name : 'REJECT',color : '#009688', label: 'ปฏิเสธ'}
            ];
            $.each(dataStatus, function (ind, status) {
                var data = snapshot[status.name];
                if (data != undefined) {
                    var _year = data['Year'];
                    console.log('data ::==', data);
                    console.log('_year ::==', _year);
                    if (_year != undefined) {
                        var datas = [data['Jan'], data['Feb'], data['Mar'], data['Apr'], data['May'],
                         data['Jun'], data['Jul'], data['Aug'], data['Sep'], data['Oct'], data['Nov'], data['Dec']];
                         datasets.push({
                         label: "จำนวนโครงการ ประเภท " + status.label,
                         backgroundColor: status.color,
                         borderColor: colors,
                         borderWidth: 1,
                         data: datas});
                    }
                }


            });
            var collection = {
                labels: labels,
                datasets: datasets,
            };
            var ctx = $('#chartBudgetInYear');
            var myBarChart = new Chart(ctx, {
                type: 'bar',
                data: collection,
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
</script>
<jsp:include page="include/inc_footer.jsp"/>

