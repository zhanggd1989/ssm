<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
	<head>
	<title>用户登录</title>
	<link href="${ctx}/css/login.css" rel="stylesheet" type="text/css" />
	</head>
	
	<body>
		<div class="login">
			<div class="box png">
				<div class="logo png"></div>
				<div class="input">
					<div class="log">
						<form action="<c:url value="/index"/>" method="post" id="loginform">
							<div class="name">
								<label>用户名</label><input type="text" class="text"
									placeholder="用户名" name="userName" tabindex="1">
							</div>
							<div class="pwd">
								<label>密 码</label><input type="password" class="text"
									placeholder="密码" name="password" tabindex="2">
							</div>
							<div>
								<input type="submit" class="button" tabindex="3" value="登录">
								<input type="reset" class="button" tabindex="4" value="重置">
							</div>
						</form>
					</div>
					<h2 style="text-align: center">${message}</h2>
				</div>
			</div>
			<div class="air-balloon ab-1 png"></div>
			<div class="air-balloon ab-2 png"></div>
			<div class="footer"></div>
		</div>
	</body>
</html>