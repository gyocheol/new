<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <meta charset="UTF-8">
    <title>마이페이지</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/my-page.css">
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
