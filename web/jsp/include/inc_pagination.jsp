<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<c:set var="page" value="${pagination.pages}" />

pageCurrent :: ${pagination.pageCurrent} <br/>
paginLimit :: ${pagination.paginLimit} <br/>
recordCurrent :: ${pagination.recordCurrent} <br/>
recordLimit :: ${pagination.recordLimit} <br/>
pages :: ${page} <br/>
pageUrl :: ${pagination.pageUrl} <br/>


<div class="navigation pull-right">
    <ul class="pagination">
        <li><a href="${pagination.pageUrl}"> First</a></li>

        <c:if test="${pagination.pageCurrent > 1}">
            <li class="previous">
                <a href="${pagination.pageUrl}&offset=${pagination.recordCurrent -pagination.recordLimit}" class="fui-arrow-left">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
        </c:if>

        <c:set value="${(pagination.recordLimit  * page )}" var="offsetLast"></c:set>
        <c:forEach begin="${pagination.pageBegin}" end="${pagination.pageEnd}" varStatus="loop">
            <c:set value="${loop.index}" var="index"></c:set>                    
            <c:set value="${index +1}" var="label"></c:set> 
            <c:set value="${(pagination.recordCurrent / pagination.recordLimit) == index ? 'active' : ''}" var="active"></c:set>
            <c:if test="${label <= page}">
                <li class="${active}"><a href="${pagination.pageUrl}&offset=${index * pagination.recordLimit}">${label}</a></li>                            
                </c:if>                                    
            </c:forEach>
            <c:if test="${pagination.recordCurrent == offsetLast}">
            <li class="${(pagination.recordCurrent == offsetLast ? 'active' : '')}"><a href="${pagination.pageUrl}">${page+1}</a></li>      
            </c:if>          
            <c:if test="${pagination.pageCurrent < (page-1)}">
            <li>
                <a href="${pagination.pageUrl}&offset=${pagination.recordCurrent + pagination.recordLimit}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
            <li><a href="${pagination.pageUrl}&offset=${offsetLast}"> Last</a></li>
            </c:if>   

        <li class="next">
            <a href="#" class="fui-arrow-right">
                <span aria-hidden="true">${pagination.countRecordAll} Record ,${page} Pages</span>
            </a>
        </li>
    </ul>
</div>