<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판</title>
    <style>
        :root {
            --bg-color: #f2f4f7;
            --text-color: #000;
            --table-bg: #fff;
            --header-bg: #fff;
            --th-bg: #f5f5f5;
            --btn-bg: #007bff;
            --btn-text: #fff;
        }

        [data-theme="dark"] {
            --bg-color: #121212;
            --text-color: #f1f1f1;
            --table-bg: #1e1e1e;
            --header-bg: #1f1f1f;
            --th-bg: #2a2a2a;
            --btn-bg: #3a82f7;
            --btn-text: #fff;
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
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .container {
            width: 80%;
            margin: 40px auto;
            background-color: var(--table-bg);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
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

        td.title-cell {
            width: 60%;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

        td.author-cell, td.date-cell {
            width: 20%;
        }

        .header-top-right {
            position: absolute;
            top: 20px;
            right: 30px;
        }

        .header-top-right a, .user-info span {
            margin-left: 12px;
            color: var(--text-color);
            text-decoration: none;
            font-weight: bold;
        }

        .header-top-right a:hover {
            text-decoration: underline;
        }

        .fab {
            position: fixed;
            bottom: 30px;
            right: 30px;
            background-color: var(--btn-bg);
            color: var(--btn-text);
            padding: 15px 20px;
            border-radius: 50px;
            font-weight: bold;
            text-decoration: none;
            box-shadow: 0 4px 10px rgba(0,0,0,0.15);
            transition: background-color 0.2s;
        }

        .fab:hover {
            background-color: #0056b3;
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

    </style>
</head>
<body data-theme="light">

<header>
    <h1>게시판</h1>
    <div class="header-top-right">
        <c:choose>
            <c:when test="${not empty username}">
                <div class="user-info">
                    <span>안녕하세요, ${username}님!</span>
                    <a href="/my-page">내 정보</a>
                    <a href="/logout">로그아웃</a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="nav-links">
                    <a href="/login">로그인</a>
                    <a href="/register">회원가입</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</header>

<div class="container">
    <table>
        <thead>
            <tr>
                <th style="width:60%;">제목</th>
                <th style="width:20%;">작성자</th>
                <th style="width:20%;">작성일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="board" items="${boardList}">
                <tr>
                    <td class="title-cell">
                        <a href="/board/view/${board.id}" style="text-decoration:none; color:#007bff;">
                            ${board.title}
                        </a>
                    </td>
                    <td class="author-cell">${board.author}</td>
                    <td class="date-cell">
                        <fmt:formatDate value="${board.createdAtDate}" pattern="yy-MM-dd HH:mm"/>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- 게시글 작성 버튼 -->
<c:if test="${not empty username}">
    <a href="/board/write-form" class="fab">게시글 작성</a>
</c:if>

<!-- 다크모드 토글 버튼 (좌측 하단) -->
<button class="theme-toggle" onclick="toggleTheme()" title="테마 전환">
    <img src="https://cdn-icons-png.flaticon.com/512/6714/6714978.png" alt="테마 전환 아이콘" />
</button>

<script>
    function toggleTheme() {
        const body = document.body;
        const current = body.getAttribute('data-theme');
        const next = current === 'dark' ? 'light' : 'dark';
        body.setAttribute('data-theme', next);
    }
</script>

</body>
</html>
