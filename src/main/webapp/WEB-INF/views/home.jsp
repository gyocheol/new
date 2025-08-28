<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
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

        .hidden-board {
            opacity: 0.5;
            text-decoration: line-through;
        }

        .admin-action-cell {
            text-align: center;   /* 셀 가운데 정렬 */
        }

        .admin-action-cell form {
            display: inline-block; /* form을 가로로 배치 */
            margin: 0 4px;         /* 버튼 사이 간격 */
        }

        .admin-action-wrapper {
            display: flex;
            justify-content: center; /* 버튼들을 가운데 정렬 */
            gap: 6px;                /* 버튼 간격 */
        }

        .act-btn {
            padding: 8px 16px;
            font-size: 14px;
            background-color: var(--btn-bg);
            color: var(--btn-text);
            border: none;
            border-radius: 6px;
            cursor: pointer;
            white-space: nowrap;   /* 줄바꿈 방지 */
            min-width: 60px;       /* 버튼 최소 너비 확보 */
            text-align: center;    /* 글자 가운데 정렬 */
        }

        .act-btn:hover {
            background-color: #0056b3;
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
                    <c:choose>
                        <c:when test="${role == 'ROLE_ADMIN'}">
                            <span>안녕하세요, 관리자님!</span>
                            <a href="/admin">관리자 탭</a>
                        </c:when>
                        <c:otherwise>
                            <span>안녕하세요, ${username}님!</span>
                            <a href="/mypage">내 정보</a>
                        </c:otherwise>
                    </c:choose>
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
                <th style="width:auto;">제목</th>
                <th style="width:10%;">작성자</th>
                <th style="width:20%;">작성일</th>
                <c:if test="${role == 'ROLE_ADMIN'}">
                    <th style="width:20%;">관리</th>
                </c:if>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="board" items="${boardList}">
                <tr id="board-row-${board.id}" class="${board.hidden ? 'hidden-board' : ''}">
                    <td class="title-cell">
                        <a href="/board/view/${board.id}" style="text-decoration:none; color:#007bff;">
                            ${board.title}
                        </a>
                    </td>
                    <td class="author-cell">${board.author}</td>
                    <td class="date-cell">
                        <fmt:formatDate value="${board.createdAtDate}" pattern="yy-MM-dd HH:mm"/>
                    </td>

                    <c:if test="${role == 'ROLE_ADMIN'}">
                        <td class="admin-action-cell">
                            <form action="/admin/toggle-hidden/${board.id}" method="post" style="display:inline;">
                                <button type="submit" class="act-btn">
                                    ${board.hidden ? '해제' : '숨김'}
                                </button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- 게시글 작성 버튼 -->
<c:if test="${not empty username}">
    <a href="/board/write-form" class="fab">게시글 작성</a>
</c:if>
</body>
</html>
