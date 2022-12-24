<%@page import="com.smile.AuthServer.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사용자 관리 - 사용자 목록</title>
<style>
	body{
		text-align: center;
	}
	table{
		border-collapse: collapse;
	}
	td{
		padding: 5px 30px;
		text-align: center;
		border: 1px solid black;
	}
	a{
		text-decoration: none;
		color: green;
	}
</style>
<script>
	function confirmAndDelete(id)
	{
		let result = confirm('"' + id + '" 사용자를 정말로 삭제하시겠습니까?');
		if(result)
		{
			let formEl = document.getElementById('list_form');
			formEl.setAttribute("action","${pageContext.request.contextPath}/UserManage?action=user_delete&id=" + id);
			formEl.submit();
		}
	}
</script>
</head>
<body>
	<header>
		<h1>사용자 관리</h1>
		<hr/><br>
	</header>
	<article>
		<form id='list_form' action='' method='POST'>
			<table align="center">
				<thead>
					<tr><th>아이디</th><th>이름</th><th>처리</th></tr>
				</thead>
				<tbody>
				<c:if test="${user_list != null}">
				<c:forEach var="user" items="${user_list}">
					<tr>
						<td>${user.id}</td>
						<td>${user.name}</td>
						<td>
							<button href='#' onclick='confirmAndDelete("${user.id}");'>삭제</button>
						</td>
					</tr>
				</c:forEach>
				</c:if>
				<c:if test="${user_list == null}">
					<tr>
						<td colspan="5">등록된 사용자가 없습니다!</td>
					</tr>
				</c:if>
				</tbody>
			</table>
		</form>
		<br>
		<%-- <button onclick="location.href='${pageContext.request.contextPath}/userlist/main'"/>메인 화면으로</button> --%>
	</article>
</body>
</html>