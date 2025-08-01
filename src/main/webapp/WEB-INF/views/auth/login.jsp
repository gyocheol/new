<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그인</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <h3 class="text-center mb-4">로그인</h3>

            <c:if test="${param.error != null}">
                <div class="alert alert-danger">아이디 또는 비밀번호가 올바르지 않습니다.</div>
            </c:if>

            <c:if test="${param.logout != null}">
                <div class="alert alert-success">로그아웃되었습니다.</div>
            </c:if>

            <form method="post" action="/login">
                <!-- redirectURL hidden input 추가 -->
                <input type="hidden" name="redirectURL" value="${param.redirectURL}" />
                <div class="mb-3">
                    <label class="form-label">아이디</label>
                    <input type="text" class="form-control" name="username" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">비밀번호</label>
                    <input type="password" class="form-control" name="password" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">로그인</button>
            </form>

            <div class="mt-3 text-center">
                <a href="/register">회원가입</a> | <a href="/">홈으로</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
