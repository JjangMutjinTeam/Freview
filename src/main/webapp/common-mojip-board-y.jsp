<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="loginUser" value="${requestScope.loginUser}"/>
<c:set var="memberSeq" value="${loginUser.memberSeq}"/>
<c:set var="nickname" value="${loginUser.nickname}"/>
<c:set var="gubun" value="${loginUser.gubun}"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>모집 게시판</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="assets/img/favicon.png" rel="icon">
    <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
          rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="assets/vendor/quill/quill.snow.css" rel="stylesheet">
    <link href="assets/vendor/quill/quill.bubble.css" rel="stylesheet">
    <link href="assets/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="assets/vendor/simple-datatables/style.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="assets/css/style.css" rel="stylesheet">
<%--    <link href="/assets/css/hr.css" rel="stylesheet">--%>

    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>

    <!-- Day.js -->
    <script src="https://cdn.jsdelivr.net/npm/dayjs@1.10.7/dayjs.min.js"></script>

    <style>
      /* Custom CSS to make all table rows white */
      table tbody tr {
        background-color: white !important;
      }
      .post-list {
        display: flex;
        flex-wrap: wrap;
        gap: 16px; /* 카드 사이의 간격을 설정합니다 */
      }

      .post-item {
        flex: 0 0 calc(25% - 16px); /* 카드의 너비를 4등분하여 설정합니다. 카드 간격을 고려하여 너비를 계산합니다 */
        box-sizing: border-box;
        border: 1px solid #ddd; /* 카드 테두리 */
        border-radius: 8px; /* 카드 모서리를 둥글게 */
        padding: 16px; /* 카드 내부 여백 */
        background-color: #fff; /* 카드 배경색 */
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 카드 그림자 */
        text-decoration: none; /* 링크의 기본 밑줄 제거 */
        color: inherit; /* 링크의 기본 색상 상속 */
      }

      .post-item img.profile-img {
        max-width: 100%;
        height: auto;
        border-radius: 50%; /* 이미지 둥글게 */
      }

      .post-item h3, .post-item p {
        margin: 8px 0; /* 제목과 내용의 여백 설정 */
      }
    </style>


    <!-- =======================================================
    * Template Name: NiceAdmin
    * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
    * Updated: Apr 20 2024 with Bootstrap v5.3.3
    * Author: BootstrapMade.com
    * License: https://bootstrapmade.com/license/
    ======================================================== -->
</head>

<body>

<!-- ======= Header ======= -->
<header id="header" class="header fixed-top d-flex align-items-center header-hr">
    <div class="d-flex align-items-center justify-content-between ">
        <a href="/main?seq=${memberSeq}&pagecode=Requester"
           class="logo d-flex align-items-center">
            <img src="assets/img/logo/logo-vertical.png" alt=""
                 style="  width: 50px; margin-top: 20px;">
            <span class="d-none d-lg-block">Freview</span>
        </a>
    </div>
    <div class="header-hr-right">
        <a href="/my-info?member_seq=${memberSeq}" style="margin-right: 20px">
            ${nickname}
            <img src="assets/img/basic/basic-profile-img.png" alt=" " style="width: 30px;
                margin-top: 15px;">
            <%--            <img src="<%=profileURL()%>" alt=" " style="width: 30px;--%>
            <%--    margin-top: 15px;"> TODO: 세션의 프로필 url을 적용할 것--%>
        </a>
        <a href="/COMM_logout.jsp" style="margin-top: 17px;">로그아웃</a>
    </div>
</header>

<main id="main" style="margin:auto; margin-top:50px">

    <div class="pagetitle">
        <h1>모집 게시판</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/mojipBoard">Home</a></li>
                <li class="breadcrumb-item">Pages</li>
                <li class="breadcrumb-item active">Blank</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <div class="card">
        <div class="card-body">
            <h5 class="card-title">모집 게시판</h5>
            <p>매우 중요한 모집글이 올라옵니다 <br></p>

            <c:if test="${gubun == 'B'}">
                <div>
                    <a href="/mojipBoard/createPost" class="btn btn-primary">
                        모집 등록
                    </a>
                </div>
            </c:if>

            <div id="postList" class="post-list">
            </div>
        </div>
    </div>

</main>

<script>
    $(document).ready(function() {

      loadInitialData();

      function loadInitialData() {
        $.ajax({
          method: "POST",
          url: "/mojip",
          dataType: "json",
          success: function (response) {
            renderData(response.data);
            if (response.hasMore) {
              $('#loadMoreBtn').data('previous-member-seq', response.data[response.data.length - 1].memberSeq).show();
            } else {
              $('#loadMoreBtn').hide();
            }
          },
          error: function() {
            console.error("[ERROR] 데이터 초기화 중 오류 발생");
          }
        });
      }

      function renderData(data) {
        var htmlStr = "";
        $.map(data, function (post) {
          htmlStr += "<a href='/mojipboard/detail?postSeq=" + post["postSeq"] + "' class='post-item'>";
          htmlStr += "<img src='" + post["profilePhotoUrl"] + "' alt='Profile' class='profile-img'>";
          htmlStr += "<h5>" + post["title"] + "</h5>";
          htmlStr += "<p>모집 가게: " + post["storeName"] + "</p>";
          htmlStr += "<p>모집 기간: " + post["applyStartDate"] + " ~ " + post["applyEndDate"] + "</p>";
          htmlStr += "<p>방문 날짜: " + post["experienceDate"] + "</p>";
          htmlStr += "<p>좋아요 수: " + post["numberOfLikes"] + "</p>";
          htmlStr += "</a>";
        });
        $('#postList').empty().append(htmlStr);
    };

    });
</script>
<!-- ======= Footer ======= -->
<footer id="footer" class="footer">
    <div class="copyright">
        &copy; Copyright <strong><span>NiceAdmin</span></strong>. All Rights Reserved
    </div>
    <div class="credits">
        <!-- All the links in the footer should remain intact. -->
        <!-- You can delete the links only if you purchased the pro version. -->
        <!-- Licensing information: https://bootstrapmade.com/license/ -->
        <!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/ -->
        Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
    </div>
</footer><!-- End Footer -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>

<!-- Vendor JS Files -->
<script src="assets/vendor/apexcharts/apexcharts.min.js"></script>
<script src="assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="assets/vendor/chart.js/chart.umd.js"></script>
<script src="assets/vendor/echarts/echarts.min.js"></script>
<script src="assets/vendor/quill/quill.js"></script>
<script src="assets/vendor/simple-datatables/simple-datatables.js"></script>
<script src="assets/vendor/tinymce/tinymce.min.js"></script>
<script src="assets/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="assets/js/main.js"></script>

</body>

</html>