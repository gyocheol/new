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
            white-space: normal;    /* ← pre, pre-wrap, pre-line 이면 줄바꿈+공백 유지됨 */
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
    <script>
        function toggleEditForm(commentId) {
            const form = document.getElementById("edit-form-" + commentId);
            form.style.display = (form.style.display === "none") ? "block" : "none";
        }

        function toggleReplyForm(commentId) {
            const form = document.getElementById('reply-form-' + commentId);
            form.style.display = (form.style.display === 'none') ? 'block' : 'none';
        }

        document.addEventListener('DOMContentLoaded', function () {
            document.querySelectorAll('.comment-content, #contentBox').forEach(function(el) {
                // 텍스트 가져오기 (줄바꿈 포함)
                const lines = el.innerText.split('\n');
                const html = lines.map(line =>
                    line.replace(/^\s+/, '') // 앞 공백 제거
                        .replace(/&/g, "&amp;")
                        .replace(/</g, "&lt;")
                        .replace(/>/g, "&gt;")
                ).join('<br/>');
                el.innerHTML = html;
            });
        });

    </script>
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
                <form action="/board/delete/{comment.id}" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
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
                    <div class="comment-meta" style="display: flex; justify-content: space-between; align-items: center;">
                        <div>
                            ${comment.author} |
                            <fmt:formatDate value="${comment.createdAtDate}" pattern="yy-MM-dd HH:mm"/>
                        </div>
                        <div>
                            <c:if test="${not empty loginUsername}">
                                <span class="reply-button" onclick="toggleReplyForm(${comment.id})">답글달기</span>
                            </c:if>
                            <c:if test="${loginUsername == comment.author}">
                                <span style="color: #ccc;"> | </span>
                                <span class="reply-button" onclick="toggleEditForm(${comment.id})">수정</span>
                                <span style="color: #ccc;"> | </span>
                                <form method="post" action="/comment/delete/${comment.id}" style="display: inline;" onsubmit="return confirm('댓글을 삭제하시겠습니까?');">
                                    <input type="hidden" name="boardId" value="${board.id}" />
                                    <button type="submit" class="reply-button" style="padding-left: 0;">삭제</button>
                                </form>
                            </c:if>
                        </div>
                    </div>

                    <div class="comment-content">
                        <c:choose>
                            <c:when test="${comment.hidden}">
                                <em style="color: #888;">관리자에 의해 숨김 처리된 댓글입니다.</em>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${comment.content}" escapeXml="true" />
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- 댓글 수정 폼 -->
                    <c:if test="${loginUsername == comment.author}">
                        <div class="edit-form" id="edit-form-${comment.id}" style="display:none;">
                            <form method="post" action="/comment/edit/${comment.id}">
                                <input type="hidden" name="boardId" value="${board.id}" />
                                <textarea name="content"><c:out value="${comment.content}" /></textarea>
                                <button type="submit" class="update-btn">수정 완료</button>
                            </form>
                        </div>
                    </c:if>

                    <!-- 대댓글 출력 -->
                    <c:forEach var="reply" items="${comments}">
                        <c:if test="${reply.parent != null && reply.parent.id == comment.id}">
                            <div class="reply">
                                <div class="comment-meta" style="display: flex; justify-content: space-between; align-items: center;">
                                    <div>
                                        ${reply.author} ·
                                        <fmt:formatDate value="${reply.createdAtDate}" pattern="yy-MM-dd HH:mm"/>
                                    </div>
                                    <div>
                                        <c:if test="${loginUsername == reply.author}">
                                            <button class="reply-button" onclick="toggleEditForm(${reply.id})">수정</button>
                                            <span style="color: #ccc;"> | </span>
                                            <form method="post" action="/comment/delete/${reply.id}" style="display: inline;" onsubmit="return confirm('댓글을 삭제하시겠습니까?');">
                                                <input type="hidden" name="boardId" value="${board.id}" />
                                                <button type="submit" class="reply-button">삭제</button>
                                            </form>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="comment-content">
                                    <c:choose>
                                        <c:when test="${reply.hidden}">
                                            <em style="color: #888;">관리자에 의해 숨김 처리된 댓글입니다.</em>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${reply.content}" escapeXml="true" />
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <!-- 대댓글 수정 폼 -->
                                <c:if test="${loginUsername == reply.author}">
                                    <div class="edit-form" id="edit-form-${reply.id}" style="display:none;">
                                        <form method="post" action="/comment/edit/${reply.id}">
                                            <input type="hidden" name="boardId" value="${board.id}" />
                                            <textarea name="content"><c:out value="${reply.content}" /></textarea>
                                            <button type="submit" class="update-btn">수정 완료</button>
                                        </form>
                                    </div>
                                </c:if>
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
            <p style="margin-top: 20px; color: #777;">댓글을 작성하려면 <a href="/login?redirectURL=/board/view/${board.id}">로그인</a>이 필요합니다.</p>
        </c:if>
    </div>
    <!-- 댓글 영역 끝 -->

</div>
</body>
</html>
