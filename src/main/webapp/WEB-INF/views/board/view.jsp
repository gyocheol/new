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

        /* 댓글 스타일 */
        .comment-section {
            margin-top: 40px;
        }

        .comment-section h3 {
            font-size: 18px;
            margin-bottom: 20px;
        }

        .comment {
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 1px solid #e0e0e0;
        }

        .reply {
            background-color: #f9f9f9;
            padding: 10px 15px;
            margin: 10px 0 0 20px;
            border-left: 3px solid #ccc;
            font-size: 14px;
        }

        .comment-meta {
            color: #777;
            font-size: 13px;
            margin-bottom: 5px;
        }

        .comment-content {
            margin-bottom: 10px;
        }

        .comment-form textarea {
            width: 100%;
            height: 80px;
            resize: none;
            padding: 10px;
            font-size: 14px;
            border-radius: 6px;
            border: 1px solid #ccc;
            margin-bottom: 10px;
        }

        .comment-form button {
            padding: 8px 16px;
            font-size: 14px;
            border: none;
            border-radius: 6px;
            background-color: #007bff;
            color: white;
            cursor: pointer;
        }

        .comment-form button:hover {
            background-color: #0056b3;
        }

        .reply-button {
            font-size: 13px;
            color: #007bff;
            background: none;
            border: none;
            cursor: pointer;
            padding: 0;
        }

        .reply-button:hover {
            text-decoration: underline;
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

        function toggleReplyForm(commentId) {
            const form = document.getElementById('reply-form-' + commentId);
            form.style.display = (form.style.display === 'none') ? 'block' : 'none';
        }
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

    <!-- 댓글 영역 시작 -->
    <div class="comment-section">
        <h3>댓글</h3>

        <c:forEach var="comment" items="${comments}">
            <c:if test="${comment.parent == null}">
                <div class="comment">
                    <div class="comment-meta">
                        ${comment.author} · <fmt:formatDate value="${comment.createdAtDate}" pattern="yy-MM-dd HH:mm"/>
                        <c:if test="${not empty loginUsername}">
                            <button class="reply-button" onclick="toggleReplyForm(${comment.id})">답글달기</button>
                        </c:if>
                    </div>
                    <div class="comment-content">${comment.content}</div>

                    <!-- 대댓글 출력 -->
                    <c:forEach var="reply" items="${comments}">
                        <c:if test="${reply.parent != null && reply.parent.id == comment.id}">
                            <div class="reply">
                                <div class="comment-meta">
                                    ${reply.author} · <fmt:formatDate value="${reply.createdAtDate}" pattern="yy-MM-dd HH:mm"/>
                                </div>
                                <div class="comment-content">${reply.content}</div>
                            </div>
                        </c:if>
                    </c:forEach>

                    <!-- 대댓글 작성 폼 -->
                    <c:if test="${not empty loginUsername}">
                        <div class="comment-form" id="reply-form-${comment.id}" style="display: none; margin-top: 10px;">
                            <form action="/comment/write" method="post">
                                <input type="hidden" name="boardId" value="${board.id}" />
                                <input type="hidden" name="parentId" value="${comment.id}" />
                                <textarea name="content" placeholder="답글을 입력하세요..."></textarea>
                                <button type="submit">답글 작성</button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </c:if>
        </c:forEach>

        <!-- 댓글 작성 폼 -->
        <c:if test="${not empty loginUsername}">
            <div class="comment-form">
                <form action="/comment/write" method="post">
                    <input type="hidden" name="boardId" value="${board.id}" />
                    <textarea name="content" placeholder="댓글을 입력하세요..."></textarea>
                    <button type="submit">댓글 작성</button>
                </form>
            </div>
        </c:if>
        <c:if test="${empty loginUsername}">
            <p style="margin-top: 20px; color: #777;">댓글을 작성하려면 <a href="/login">로그인</a>이 필요합니다.</p>
        </c:if>
    </div>
    <!-- 댓글 영역 끝 -->

</div>
</body>
</html>
