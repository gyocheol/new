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
        display: flex;
        align-items: center;
        gap: 8px;
    }

    .reply-button {
        font-size: 11px;
        color: #007bff;
        cursor: pointer;
        text-decoration: underline;
        background: none;
        border: none;
        padding: 0;
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

    .no-comment {
        font-size: 14px;
        color: #999;
        margin-top: 10px;
        margin-bottom: 10px;
    }
</style>

<h3 style="margin-top: 20px;">댓글</h3>

<!-- 댓글 출력 -->
<c:choose>
    <c:when test="${empty comments}">
        <div class="no-comment">댓글이 없습니다.</div>
    </c:when>
    <c:otherwise>
        <c:forEach var="comment" items="${comments}">
            <c:if test="${comment.parent == null}">
                <!-- 원댓글 -->
                <div class="comment-box">
                    <div class="comment-meta">
                        <strong>${comment.author}</strong>
                        <fmt:formatDate value="${comment.createdAtDate}" pattern="yy-MM-dd HH:mm"/>
                        <c:if test="${not empty loginUsername}">
                            <button class="reply-button" data-id="${comment.id}">답글달기</button>
                        </c:if>
                    </div>
                    <div class="comment-content">
                        <c:choose>
                            <c:when test="${comment.hidden}">
                                <span style="color: gray;">관리자에 의해 가려진 댓글입니다.</span>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${comment.content}" />
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- 대댓글 출력 -->
                    <c:forEach var="reply" items="${comments}">
                        <c:if test="${reply.parent != null && reply.parent.id == comment.id}">
                            <div class="reply-box">
                                <div class="comment-meta">
                                    <strong>${reply.author}</strong>
                                    <fmt:formatDate value="${reply.createdAtDate}" pattern="yy-MM-dd HH:mm"/>
                                </div>
                                <div class="comment-content">
                                    <c:choose>
                                        <c:when test="${reply.hidden}">
                                            <span style="color: gray;">관리자에 의해 가려진 댓글입니다.</span>
                                        </c:when>
                                        <c:otherwise>
                                            <c:out value="${reply.content}" />
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>

                    <!-- 대댓글 작성 폼 (초기 숨김) -->
                    <c:if test="${not empty loginUsername}">
                        <div class="comment-form reply-form" id="reply-form-${comment.id}" style="display: none;">
                            <form action="/comment/write" method="post">
                                <input type="hidden" name="boardId" value="${board.id}" />
                                <input type="hidden" name="parentId" value="${comment.id}" />
                                <textarea name="content" rows="2" placeholder="답글을 입력하세요..." required></textarea><br/>
                                <button type="submit">답글 작성</button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </c:if>
        </c:forEach>
    </c:otherwise>
</c:choose>

<!-- 원댓글 작성 폼 (로그인 시에만) -->
<c:if test="${not empty loginUsername}">
    <div class="comment-form" style="margin-top: 30px;">
        <form action="/comment/write" method="post">
            <input type="hidden" name="boardId" value="${board.id}" />
            <textarea name="content" rows="3" placeholder="댓글을 입력하세요..." required></textarea><br/>
            <button type="submit">댓글 작성</button>
        </form>
    </div>
</c:if>

<!-- 답글 폼 토글 스크립트 -->
<script>
    document.addEventListener('DOMContentLoaded', function () {
        const buttons = document.querySelectorAll('.reply-button');

        buttons.forEach(button => {
            button.addEventListener('click', function () {
                const id = button.getAttribute('data-id');
                const form = document.getElementById('reply-form-' + id);
                if (form) {
                    form.style.display = (form.style.display === 'none') ? 'block' : 'none';
                }
            });
        });
    });
</script>
