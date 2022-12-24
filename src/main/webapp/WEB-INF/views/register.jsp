<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"%>
<%@ page import="java.util.*, com.smile.AuthServer.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 가입</title>
</head>
<body>
	<form action="<%= request.getContextPath() %>/login?action=register"
		method="POST">
		<table>
			<tr>
				<td><label for="ID">ID: </label></td>
				<td><input type="text" placeholder="ID" name="id" required />
				</td>
			</tr>
			<tr>
				<td><label for="PassWord">Password: </label></td>
				<td><input type="password" placeholder="Password"
					name="password" required /></td>
			</tr>
			<tr>
				<td><label for="PassWordCheck">PasswordCheck: </label></td>
				<td><input type="password" placeholder="PasswordCheck"
					name="passwordCheck" required /></td>
			</tr>
			<tr>
				<td><label for="Name">Name: </label></td>
				<td><input type="text" placeholder="name" name="name" required /></td>
			</tr>
		</table>
		<hr>
		<input type="submit" value="회원가입" />
		<input type="button" value="취소" onclick="window.history.go(-1);" />
	</form>
</body>
</html>