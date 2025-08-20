<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style>
.theme-toggle {
    position: fixed;
    bottom: 20px;
    left: 20px;
    width: 50px;
    height: 50px;
    border: none;
    background: transparent;
    cursor: pointer;
    z-index: 9999;
}
.theme-toggle img {
    width: 100%;
    height: 100%;
}
/* 기본 (라이트 모드) */
body[data-theme="light"] {
    background-color: #f5f5f5 !important;
    color: #000 !important;
}

/* 다크 모드 */
body[data-theme="dark"] {
    background-color: #1e1e1e !important;
    color: #fff !important;
}
</style>

<!-- 공통 헤더 -->
<header>
    <!-- 다크모드 토글 버튼 -->
    <button class="theme-toggle" onclick="toggleTheme()" title="테마 전환">
        <img src="https://cdn-icons-png.flaticon.com/512/6714/6714978.png" alt="테마 전환 아이콘" />
    </button>
</header>

<script>
    // 페이지 로드 시 저장된 테마 불러오기
    document.addEventListener("DOMContentLoaded", () => {
        const savedTheme = localStorage.getItem("theme");
        if (savedTheme) {
            document.body.setAttribute("data-theme", savedTheme);
        } else {
            document.body.setAttribute("data-theme", "light"); // 기본값
        }
    });

    // 테마 전환 함수
    function toggleTheme() {
        const body = document.body;
        const current = body.getAttribute("data-theme");
        const next = current === "dark" ? "light" : "dark";
        body.setAttribute("data-theme", next);

        // localStorage에 저장
        localStorage.setItem("theme", next);
    }
</script>
