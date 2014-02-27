#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.lang.Throwable" %>
<%@ page import="org.nutz.log.Log,org.nutz.log.Logs" %>
<%@ page import="org.nutz.mvc.Mvcs,org.nutz.mvc.ActionContext" %>
<%@ page import="org.nutz.mvc.impl.processor.ViewProcessor" %>

<%
 	Throwable ex = (Throwable)request.getAttribute(ViewProcessor.DEFAULT_ATTRIBUTE);
	//记录日志
	//Log logger = Logs.get();
	//logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>500 - 系统内部错误</title>
	<script language="javascript">
		function showDetail()
		{
			var elm = document.getElementById('detail_system_error_msg');
			if(elm.style.display == '') {
				elm.style.display = 'none';
			}else {
				elm.style.display = '';
			}
		}
	</script>	
</head>

<body>
<div><h1>系统发生内部错误.</h1></div>
<b style="color: red;">错误信息:<%=ex.getMessage()%></b> 
<br/>
<a href="javascript:history.back();">返回</a>
	<br/>

	<p><a href="${symbol_pound}" onclick="showDetail();">点击这里查看具体错误消息</a>,报告以下错误消息给系统管理员,可以更加快速的解决问题</p>

	<div id="detail_system_error_msg" style="display:none">
		<pre><%ex.printStackTrace(new java.io.PrintWriter(out));%></pre>
	</div>
</body>
</html>
