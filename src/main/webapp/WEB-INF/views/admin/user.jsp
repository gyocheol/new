<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <meta charset="UTF-8">
    <title>사용자 관리</title>
    <style>
        :root {
            --bg-color: #f2f4f7;
            --text-color: #000;
            --table-bg: #fff;
            --header-bg: #fff;
            --th-bg: #f5f5f5;
            --btn-bg: #007bff;
            --btn-text: #fff;
            --active-tab: #00b050;
        }

        [data-theme="dark"] {
            --bg-color: #121212;
            --text-color: #f1f1f1;
            --table-bg: #1e1e1e;
            --header-bg: #1f1f1f;
            --th-bg: #2a2a2a;
            --btn-bg: #3a82f7;
            --btn-text: #fff;
            --active-tab: #00b050;
        }

        body {
            font-family: Arial, sans-serif;
            background-color: var(--bg-color);
            color: var(--text-color);
            margin: 0;
            padding: 0;
            transition: background-color 0.3s, color 0.3s;
        }

        h1 {
            text-align: center;
            padding: 20px 0;
            margin: 0;
            background-color: var(--header-bg);
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .container {
            width: 80%;
            margin: 40px auto;
            background-color: var(--table-bg);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .tabs {
            display: flex;
            border-bottom: 2px solid #ccc;
            margin-bottom: 20px;
        }

        .tabs button {
            padding: 12px 20px;
            border: none;
            background: none;
            cursor: pointer;
            font-weight: bold;
            color: var(--text-color);
            border-bottom: 3px solid transparent;
        }

        .tabs button.active {
            border-bottom: 3px solid var(--active-tab);
            color: var(--active-tab);
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ccc;
        }

        th {
            background-color: var(--th-bg);
        }

        .title-link {
            color: var(--btn-bg);
            text-decoration: none;
        }

        .title-link:hover {
            text-decoration: underline;
        }

        .theme-toggle {
            position: fixed;
            bottom: 30px;
            left: 30px;
            width: 48px;
            height: 48px;
            border: none;
            background: none;
            cursor: pointer;
        }

        .theme-toggle img {
            width: 100%;
            height: auto;
        }

        .tab-content {
            display: none;
        }

        .tab-content.active {
            display: block;
        }
    </style>
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
        // 탭 전환 JavaScript (마이페이지에서 제공된 코드와 동일)
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