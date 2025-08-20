<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <title>게시글 수정</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
        }
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
            --active-tab: #00b050;
        }

        .container {
            width: 60%;
            margin: 40px auto;
            background-color: var(--table-bg);
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        :root {
            --bg-color: #f2f4f7;
            --text-color: #000;
            --table-bg: #fff;     /* 컨테이너 기본 색 (라이트모드) */
        }

        [data-theme="dark"] {
            --bg-color: #121212;
            --text-color: #f1f1f1;
            --table-bg: #1e1e1e;  /* 컨테이너 다크모드 색 */
        }

        h2 {
            font-size: 24px;
            margin-bottom: 20px;
        }

        form label {
            display: block;
            font-weight: bold;
            margin-top: 15px;
        }

        form input[type="text"],
        form textarea {
            width: 100%;
            padding: 10px;
            font-size: 14px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            background-color: var(--table-bg);  /* 테마 적용 */
            color: var(--text-color);           /* 글자색 테마 적용 */
        }
        [data-theme="dark"] form input[type="text"],
        [data-theme="dark"] form textarea {
            border: 1px solid #555;
        }

        form textarea {
            resize: vertical;
            height: 200px;
        }

        .buttons {
            text-align: right;
            margin-top: 20px;
        }

        .buttons button {
            padding: 8px 16px;
            font-size: 14px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        .buttons button:hover {
            background-color: #0056b3;
        }

        .buttons a {
            margin-left: 10px;
            text-decoration: none;
            color: #555;
            font-size: 14px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>게시글 수정</h2>

    <form action="/board/edit/{id}" method="post">
        <input type="hidden" name="id" value="${id}" />

        <label for="title">제목</label>
        <input type="text" id="title" name="title" value="${board.title}" required />

        <label for="content">내용</label>
        <textarea id="content" name="content" required>${board.content}</textarea>

        <div class="buttons">
            <button type="submit">수정 완료</button>
            <a href="/board/view/${id}">취소</a>
        </div>
    </form>
</div>
<script>
    window.addEventListener('pageshow', function (event) {
        if (event.persisted || (window.performance && performance.navigation.type === 2)) {
            // 뒤로가기로 접근한 경우 목록 페이지로 이동
            window.location.replace('/');
        }
    });
</script>
</body>
</html>
