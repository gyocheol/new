<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <meta charset="UTF-8">
    <title>마이페이지</title>
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

        .strikethrough {
            text-decoration: line-through; /* 글자 가운데 줄 */
            color: gray; /* 글자 색상 변경 */
        }
    </style>
</head>
<body data-theme="light">

<h1>마이페이지</h1>

<div class="container">
    <!-- 탭 메뉴 -->
    <div class="tabs">
        <button class="tab-btn active" data-tab="board">작성글</button>
        <button class="tab-btn" data-tab="comment">작성댓글</button>
    </div>

    <!-- 작성글 목록 -->
    <div class="tab-content active" id="tab-board">
        <table>
            <thead>
            <tr>
                <th style="width:60%;">제목</th>
                <th style="width:20%;">작성일</th>
                <th style="width:20%;">수정일</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="board" items="${myBoardList}">
                <tr>
                    <c:choose>
                        <c:when test="${board.hidden}">
                            <td class="strikethrough">${board.title}</td>
                        </c:when>
                        <c:otherwise>
                            <td><a href="/board/view/${board.id}" class="title-link">${board.title}</a></td>
                        </c:otherwise>
                    </c:choose>
                    <td><fmt:formatDate value="${board.createdAtDate}" pattern="yy-MM-dd HH:mm" /></td>
                    <td><fmt:formatDate value="${board.updatedAtDate}" pattern="yy-MM-dd HH:mm" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- 작성댓글 목록 -->
    <div class="tab-content" id="tab-comment">
        <table>
            <thead>
            <tr>
                <th style="width:60%;">댓글 내용</th>
                <th style="width:20%;">작성일</th>
                <th style="width:20%;">수정일</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="comment" items="${myCommentList}">
                <tr>
                    <td>
                        <a href="/board/view/${comment.board.id}" class="title-link">
                            ${comment.content}
                            <br/>
                            <span style="font-size: 0.9em; color: #aaa;">${comment.board.title}</span>
                        </a>
                    </td>
                    <td><fmt:formatDate value="${comment.createdAtDate}" pattern="yy-MM-dd HH:mm" /></td>
                    <td><fmt:formatDate value="${comment.updatedAtDate}" pattern="yy-MM-dd HH:mm" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    // 탭 전환
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
