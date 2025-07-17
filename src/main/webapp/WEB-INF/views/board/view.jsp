<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
            width: 60%; /* 전체 화면의 60% = 5등분 중 가운데 3 */
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
            white-space: pre-wrap;
            margin-bottom: 30px;
        }

        .buttons {
            text-align: right;
            margin-bottom: 40px;
        }

        .buttons form {
            display: inline-block;
            margin-left: 10px;
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

        .comments {
            margin-top: 40px;
        }

        .comments h3 {
            font-size: 20px;
            margin-bottom: 10px;
        }

        .comment-table {
            width: 100%;
            border-collapse: collapse;
        }

        .comment-table th,
        .comment-table td {
            border: 1px solid #ddd;
            padding: 10px;
            font-size: 14px;
        }

        .comment-table th {
            background-color: #f2f2f2;
            text-align: left;
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

    <div class="content">
        ${board.content}
    </div>

    <!-- 수정/삭제 버튼 (작성자와 로그인 유저가 같을 경우에만) -->
    <c:if test="${loginUsername == board.author.username}">
        <div class="buttons">
            <form action="/board/edit/${board.id}" method="get">
                <button type="submit">수정</button>
            </form>
            <form action="/board/delete" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                <input type="hidden" name="_method" value="delete"/>
                <input type="hidden" name="id" value="${board.id}"/>
                <button type="submit">삭제</button>
            </form>
        </div>
    </c:if>

    <!-- 댓글 영역 -->
    <div class="comments">
        <h3>댓글</h3>
        <table class="comment-table">
            <thead>
                <tr>
                    <th>작성자</th>
                    <th>내용</th>
                    <th>작성일</th>
                </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty comments}">
                    <c:forEach var="comment" items="${comments}">
                        <tr>
                            <td>${comment.author}</td>
                            <td>${comment.content}</td>
                            <td><fmt:formatDate value="${comment.createdAt}" pattern="yy-MM-dd HH:mm"/></td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="3" style="text-align: center;">등록된 댓글이 없습니다.</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
