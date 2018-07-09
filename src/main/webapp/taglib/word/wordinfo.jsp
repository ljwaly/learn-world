<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<title>word-info-find</title>
</head>

<body>
	<cms:wordinfo var="wordinfo" wordname="${param.wordname }" deal="${param.deal }"></cms:wordinfo>
	
	<h1>${wordinfo.resultCode}</h1>
	<span>${wordinfo}</span>
</body>

</html>