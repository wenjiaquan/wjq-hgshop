<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<nav aria-label>
  <ul class="pagination">
  	<li class='page-item <c:if test="${pageInfo.pageNum==1}">disabled</c:if>'>
      <a class="page-link" href="javascript:gotoPage(1)" tabindex="-1"  <c:if test="${pageInfo.pageNum==1}">aria-disabled="true"</c:if> >首页</a>
    </li>
    <li class="page-item">
      <c:if test="${pageInfo.pageNum!=pageInfo.firstPage}">
      <a class="page-link"  tabindex="-1" aria-disabled="true" onclick="gotoPage(${pageInfo.prePage})">上一页</a>
      </c:if>
    </li>
    <c:forEach items="${pageInfo.navigatepageNums}" var="page">
    <c:if test="${pageInfo.pageNum==page}">
    <li class="page-item active"><a class="page-link" onclick="gotoPage(${page})">${page}</a></li>
    </c:if>
    <c:if test="${pageInfo.pageNum!=page}">
    <li class="page-item"><a class="page-link" onclick="gotoPage(${page})">${page}</a></li>
    </c:if>
    </c:forEach>
    <li class="page-item">
    <c:if test="${pageInfo.pageNum!=pageInfo.lastPage}">
      <a class="page-link"  onclick="gotoPage(${pageInfo.nextPage})">下一页</a>
      </c:if>
    </li>
    <li class='page-item <c:if test="${pageInfo.pageNum==pageInfo.pages}">disabled</c:if>'>
      <a class="page-link" href="javascript:gotoPage(${pageInfo.pages})" tabindex="-1"  <c:if test="${pageInfo.pageNum==pageInfo.pages}">aria-disabled="true"</c:if> >尾页</a>
    </li>
  </ul>
</nav>  