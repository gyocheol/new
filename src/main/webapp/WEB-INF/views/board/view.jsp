<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>게시글 상세보기</title>
    <style>
        body {
            background-color: #f5f5f5;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 60%;
            margin: 40px auto;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
        }

        .title {
            font-size: 28px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .info {
            color: #777;
            font-size: 14px;
            margin-bottom: 20px;
        }

        .content {
            font-size: 16px;
            white-space: normal;
            word-break: break-word;
            margin-bottom: 30px;
        }

        .button-group {
            display: flex;
            justify-content: space-between;
            margin-bottom: 30px;
        }

        .button-left,
        .button-right {
            display: flex;
            gap: 10px;
        }

        .button-left button,
        .button-right button {
            padding: 8px 16px;
            font-size: 14px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        .button-left button {
            background-color: #6c757d;
            color: white;
        }

        .button-left button:hover {
            background-color: #5a6268;
        }

        .button-right button {
            background-color: #007bff;
            color: white;
        }

        .button-right button:hover {
            background-color: #0056b3;
        }

        .delete-button {
            background-color: #dc3545 !important;
        }

        .delete-button:hover {
            background-color: #b52a37 !important;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="title">${board.title}</div>
    <div class="info">
        작성자: ${board.author.username} |
        작성일: <fmt:formatDate value="${board.createdAtDate}" pattern="yy-MM-dd HH:mm"/>
    </div>

    <div class="content" id="contentBox">
        <c:out value="${board.content}" escapeXml="true" />
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const el = document.getElementById('contentBox');
            const text = el.textContent;

            const lines = text.split('\n').map(line =>
                line.trimStart()
                    .replace(/&/g, "&amp;")
                    .replace(/</g, "&lt;")
                    .replace(/>/g, "&gt;")
            );

            el.innerHTML = lines.join('<br/>');
        });
    </script>

    <div class="button-group">
        <div class="button-left">
            <form action="/" method="get">
                <button type="submit">목록</button>
            </form>
        </div>

        <c:if test="${loginUsername == board.author.username}">
            <div class="button-right">
                <form action="/board/edit/${board.id}" method="get">
                    <button type="submit">수정</button>
                </form>
                <form action="/board/delete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                    <input type="hidden" name="_method" value="delete"/>
                    <input type="hidden" name="id" value="${board.id}"/>
                    <button type="submit" class="delete-button">삭제</button>
                </form>
            </div>
        </c:if>
    </div>

    <!-- 댓글 포함 영역 -->
    <jsp:include page="/WEB-INF/views/comment/comment.jsp" />
</div>
</body>
</html>
