<%-- 
    Document   : pagination
    Created on : May 31, 2013, 9:37:36 PM
    Author     : Nguyen Van Manh - mail@nguyenvanmanh.com
--%>

<%@tag description="pagination tag" pageEncoding="UTF-8"
	isELIgnored="false"%>
<%@ attribute name="listname" description="For show" required="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="pagination">
	<input id="current-page" type="hidden" value="1"/>
	<ul class="pages backward">
		<li class="first"><a href="javascript:void(0);">First</a></li>
		<li class="prev"><a href="javascript:void(0);">Previous</a></li>
	</ul>
	<div class="pagination-wrap">
	<ul class="pages p-wrap">
		<c:forEach begin="1" end="${totalPages}" varStatus="v_status">
			<li><a id="page-${v_status.index}" class="pagination-p" href="javascript:void(0);">
			<c:if test="${v_status.index < 10}">&nbsp;&nbsp;</c:if>${v_status.index}</a></li>
		</c:forEach>
	</ul>
	</div>
	<ul class="pages forward">
		<li class="next"><a href="javascript:void(0);">Next</a></li>
		<li class="last"><a href="javascript:void(0);">Last</a></li>
		<li class="last">Total: ${totalRows} ${listname}</li>
	</ul>
</div>