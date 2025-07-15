<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>게시판</title>
    <style>
        /* Reset 및 기본 스타일 */
        * {
            box-sizing: border-box;
        }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            background-color: #f0f2f5;
            color: #333;
        }
        /* 상단 헤더 */
        header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px 40px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgb(0 0 0 / 0.1);
            position: sticky;
            top: 0;
            z-index: 100;
        }
        header h1 {
            margin: 0;
            font-size: 2rem;
            color: #222;
            flex-grow: 1;
            text-align: center;
        }
        /* 좌측 빈 공간: 로그인영역과 균형 맞추기 위한 */
        .header-spacer {
            width: 150px;
        }
        /* 로그인/회원가입 링크 */
        .nav-links a,
        .user-info a {
            margin-left: 15px;
            text-decoration: none;
            color: #007bff;
            font-weight: 600;
            transition: color 0.2s ease-in-out;
        }
        .nav-links a:hover,
        .user-info a:hover {
            color: #0056b3;
        }
        .user-info span {
            margin-right: 10px;
            font-weight: 600;
            color: #555;
        }
        /* 게시판 리스트 컨테이너 */
        .board-list {
            max-width: 900px;
            margin: 30px auto;
            background-color: #fff;
            border-radius: 8px;
            padding: 25px 30px;
            box-shadow: 0 3px 8px rgb(0 0 0 / 0.1);
        }
        /* 게시판 글 아이템 */
        .board-item {
            padding: 15px 0;
            border-bottom: 1px solid #ddd;
            font-size: 1rem;
        }
        .board-item:last-child {
            border-bottom: none;
        }
        .board-item strong {
            color: #007bff;
        }
        /* 비어있을 때 메시지 */
        .empty-message {
            text-align: center;
            color: #999;
            font-style: italic;
            padding: 60px 0;
            font-size: 1.2rem;
        }
    </style>
</head>
<body>

<header>
    <div class="header-spacer"></div>
    <h1>게시판</h1>
    <div>
        <c:choose>
            <c:when test="${not empty username}">
                <div class="user-info">
                    <span>안녕하세요, ${username}님!</span>
                    <a href="/my-page">내 정보</a>
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

<div class="board-list">
    <c:choose>
        <c:when test="${not empty boardList}">
            <c:forEach var="board" items="${boardList}">
                <div class="board-item">
                    <strong>${board.title}</strong> - 작성자: ${board.author} - 날짜: ${board.createdDate}
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <div class="empty-message">게시판이 비어있습니다.</div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>
