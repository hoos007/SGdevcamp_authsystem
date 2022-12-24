<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"%>
<%@ page import="java.util.*, com.smile.AuthServer.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
</head>
<body>
	<form action="<%= request.getContextPath() %>/login?action=login"
		method="POST">
		<table>
			<tr>
				<td><label for="ID">ID: </label></td>
				<td><input type="text" placeholder="ID" name="id" required /></td>
			</tr>
			<tr>
				<td><label for="Password">Password: </label></td>
				<td><input type="password" placeholder="Password"
					name="password" required /></td>
			</tr>
		</table>
		<c:if test="${id != null}">
			현재 로그인중인 아이디 : ${id}
		</c:if>
		<hr>
		<input type="submit" value="로그인" />
		<input type="button" value="회원가입" onclick="location.href='${pageContext.request.contextPath}/login?action=user_register_form'" />
		<input type="button" value="로그아웃" onclick="location.href='${pageContext.request.contextPath}/login?action=logout'" />
		<input type="button" value="사용자 관리" onclick="location.href='${pageContext.request.contextPath}/UserManage?action=user_management'" />
	</form>
</body>
</html>