<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
    .comment-box {
        padding: 10px 15px;
        border-bottom: 1px solid #ddd;
        font-size: 14px;
    }

    .comment-meta {
        color: #666;
        font-size: 12px;
        margin-bottom: 5px;
    }

    .comment-content {
        margin-bottom: 10px;
    }

    .reply-box {
        margin-left: 30px;
        background-color: #f9f9f9;
        padding: 10px 15px;
        border-left: 2px solid #ccc;
    }

    .comment-form {
        margin-top: 10px;
    }

    .comment-form textarea {
        width: 100%;
        padding: 5px;
        font-size: 14px;
        resize: vertical;
    }

    .comment-form button {
        margin-top: 5px;
        padding: 5px 10px;
        font-size: 13px;
    }
</style>

<h3 style="margin-top: 20px;">댓글</h3>

<c:forEach var="comment" items="${board.comments}">
    <c:if test="${comment.parent == null}">
        <!-- 원댓글 -->
        <div class="comment-box">
            <div class="comment-meta">
                <strong>${comment.author.username}</strong> |
                <fmt:formatDate value="${comment.createdAtDate}" pattern="yy-MM-dd HH:mm"/>
            </div>
            <div class="comment-content">
                <c:out value="${comment.content}" />
            </div>

            <!-- 대댓글 작성 폼 -->
            <div class="comment-form">
                <form action="/comment/write" method="post">
                    <input type="hidden" name="boardId" value="${board.id}" />
                    <input type="hidden" name="parentId" value="${comment.id}" />
                    <textarea name="content" rows="2" placeholder="답글을 입력하세요..." required></textarea><br/>
                    <button type="submit">답글 작성</button>
                </form>
            </div>

            <!-- 대댓글 목록 -->
            <c:forEach var="reply" items="${board.comments}">
                <c:if test="${reply.parent != null && reply.parent.id == comment.id}">
                    <div class="reply-box">
                        <div class="comment-meta">
                            <strong>${reply.author.username}</strong> |
                            <fmt:formatDate value="${reply.createdAtDate}" pattern="yy-MM-dd HH:mm"/>
                        </div>
                        <div class="comment-content">
                            <c:out value="${reply.content}" />
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </c:if>
</c:forEach>

<!-- 원댓글 작성 폼 -->
<div class="comment-form" style="margin-top: 30px;">
    <form action="/comment/write" method="post">
        <input type="hidden" name="boardId" value="${board.id}" />
        <textarea name="content" rows="3" placeholder="댓글을 입력하세요..." required></textarea><br/>
        <button type="submit">댓글 작성</button>
    </form>
</div>
