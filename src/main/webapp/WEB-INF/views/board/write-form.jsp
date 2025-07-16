<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>게시글 작성</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f9f9f9;
            padding: 40px;
        }
        .form-container {
            max-width: 600px;
            margin: auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .form-container h2 {
            text-align: center;
            margin-bottom: 30px;
        }
        label {
            display: block;
            margin-top: 15px;
            font-weight: bold;
        }
        input[type="text"], textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        textarea {
            height: 200px;
        }
        .form-actions {
            margin-top: 20px;
            text-align: right;
        }
        .form-actions button {
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
        }
        .form-actions button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h2>게시글 작성</h2>
    <form action="/write" method="post">
        <label for="title">제목</label>
        <input type="text" id="title" name="title" maxlength="20" required />

        <label for="content">내용</label>
        <textarea id="content" name="content" required></textarea>

        <!-- authorName은 서버에서 principal로 자동 설정되므로 입력받지 않음 -->

        <div class="form-actions">
            <button type="submit">작성하기</button>
        </div>
    </form>
</div>
</body>
</html>
