<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>홈</title></head>
<body>
<h2>홈 페이지</h2>

<c:choose>
    <c:when test="${pageContext.request.userPrincipal != null}">
        <p>안녕하세요, ${pageContext.request.userPrincipal.name}님!</p>
        <a href="/logout">로그아웃</a>
    </c:when>
    <c:otherwise>
        <a href="/login">로그인</a> | <a href="/register">회원가입</a>
    </c:otherwise>
</c:choose>
</body>
</html>