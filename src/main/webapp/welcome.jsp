<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="../common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>欢迎-admin</title>

<!-- 导入jquery，可以进行ajax请求 -->
<script type="text/javascript" src="./jquery/jquery.js"></script>

</head>

<body>
	<script type="text/javascript">
		var serverName = '<%=request.getServerName()%>';
		var serverPort = '<%=request.getServerPort()%>';
		var contextPath= '<%=request.getContextPath()%>';
		var _webRootPath = 'http://' + serverName + ':' + serverPort + "/";
	</script>

	<jsp:include page="./welcome/head.jsp"></jsp:include>
	
	
	<c:set var="updatedCount" value="5" />
	<c:set var="addEpisode" value="1" />
	<c:set var="updatedCount" value="${updatedCount+addEpisode}" />
	<h1>${updatedCount}</h1>
	<c:set var="ljw" value="${fn:substring('0123456789', 3, -1)}" />
	<h1>${ljw}</h1>
	
	<c:set var="playId" value="5" />
	<c:set var="billId" value="1" />
	<c:set var="ly" value="${playId}${billId}" />
	<h1>${ly}</h1>
	
	
	
	
	
</body>
</html>