<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <meta charset="UTF-8">
    <title>사용자 관리</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/my-page.css">
</head>
<body data-theme="light">
    <h1>사용자 관리</h1>
    <div class="container">
        <!-- 탭 메뉴 -->
        <div class="tabs">
            <button class="tab-btn active" data-tab="admin">관리자</button>
            <button class="tab-btn" data-tab="user">일반 사용자</button>
        </div>

        <!-- 관리자 목록 -->
        <div class="tab-content active" id="tab-admin">
            <table>
                <thead>
                    <tr>
                        <th style="width:20%;">No.</th>
                        <th style="width:50%;">사용자명</th>
                        <th style="width:30%;">역할</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="admin" items="${userGroup.admins}" varStatus="loop">
                        <tr>
                            <td>${loop.count}</td>
                            <td>${admin.username}</td>
                            <td>${admin.role}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty userGroup.admins}">
                        <tr>
                            <td colspan="3" style="text-align: center;">등록된 관리자가 없습니다.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <!-- 일반 사용자 목록 -->
        <div class="tab-content" id="tab-user">
            <table>
                <thead>
                    <tr>
                        <th style="width:20%;">No.</th>
                        <th style="width:50%;">사용자명</th>
                        <th style="width:30%;">역할</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${userGroup.users}" varStatus="loop">
                        <tr>
                            <td>${loop.count}</td>
                            <td>
                                <a href="/admin/detail/${user.id}?username=${user.username}" class="user-name-link">
                                    ${user.username}
                                </a>
                            </td>
                            <td>${user.role}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty userGroup.users}">
                        <tr>
                            <td colspan="3" style="text-align: center;">등록된 일반 사용자가 없습니다.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>

    <script>
        document.querySelectorAll('.tab-btn').forEach(button => {
            button.addEventListener('click', () => {
                const target = button.getAttribute('data-tab');

                // 버튼 상태 전환
                document.querySelectorAll('.tab-btn').forEach(btn => btn.classList.remove('active'));
                button.classList.add('active');

                // 콘텐츠 전환
                document.querySelectorAll('.tab-content').forEach(content => {
                    content.classList.remove('active');
                });
                document.getElementById('tab-' + target).classList.add('active');
            });
        });
    </script>
</body>
</html>