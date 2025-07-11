<!-- /WEB-INF/views/auth/register.jsp -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>회원가입</title></head>
<body>
<h2>회원가입</h2>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<form method="post" action="/register">
    <input type="text" name="username" placeholder="아이디" required /><br/>
    <input type="password" name="password" placeholder="비밀번호" required /><br/>
    <input type="password" name="confirmPassword" placeholder="비밀번호 확인" required /><br/>
    <button type="submit">가입하기</button>
</form>
</body>
</html>