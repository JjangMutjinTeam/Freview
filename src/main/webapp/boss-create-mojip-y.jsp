<%@ page import="com.nuguna.freview.entity.member.Member" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    Member loginUser = (Member) session.getAttribute("Member");
    Integer memberSeq = loginUser.getMemberSeq();
    String gubun = loginUser.getGubun();
    request.setAttribute("gubun", gubun);
    request.setAttribute("memberSeq", memberSeq);
%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>모집 글 등록하기</title>
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
    <style>
      /* Custom CSS to make all table rows white */
      table tbody tr {
        background-color: white !important;
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
<header id="header" class="header fixed-top d-flex align-items-center">
    <div class="d-flex align-items-center justify-content-between">
        <a href="/main?seq=<%=memberSeq%>&pagecode=Requester"
           class="logo d-flex align-items-center">
            <img src="assets/img/logo/logo-vertical.png" alt="">
            <span class="d-none d-lg-block">Freeview</span>
        </a>
        <i class="bi bi-list toggle-sidebar-btn"></i>
    </div><!-- End Logo -->

    <nav class="header-nav ms-auto">
        <ul class="d-flex align-items-center">
            <li class="nav-item dropdown pe-3">
                <a class="nav-link nav-profile d-flex align-items-center pe-0" href="#">
                    <img src="assets/img/basic/basic-profile-img.png" alt="Profile"
                         class="rounded-circle">
                    <span id="store-name-holder-head"
                          class="d-none d-md-block">${brandInfo.storeName}</span>
                </a><!-- End Profile Iamge Icon -->
            </li><!-- End Profile Nav -->
        </ul>
    </nav><!-- End Icons Navigation -->
</header><!-- End Header -->

<main id="main" class="main">

    <div class="pagetitle">
        <h1>모집게시판</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                <li class="breadcrumb-item">Pages</li>
                <li class="breadcrumb-item active">Blank</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <%
        request.setAttribute("memberSeq", memberSeq);
    %>

    <div class="card">
        <div class="card-body">
            <h5 class="card-title">새로운 모집글을 입력해주세요</h5>

            <!-- Form for creating a post -->
            <form id="createPostForm" action="/mojipBoard/createPost" method="post">
                <div class="mb-3">
                    <label for="title" class="form-label">제목</label>
                    <input type="text" class="form-control" id="title" name="title" required>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="applyStartDate" class="form-label">모집 시작일</label>
                        <input type="date" class="form-control" id="applyStartDate"
                               name="applyStartDate" required readonly>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="applyEndDate" class="form-label">모집 마감일</label>
                        <input type="date" class="form-control" id="applyEndDate"
                               name="applyEndDate" required>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="experienceDate" class="form-label">방문 날짜</label>
                    <input type="date" class="form-control" id="experienceDate"
                           name="experienceDate" required>
                </div>
                <div class="mb-3">
                    <label for="content" class="form-label">내용</label>
                    <textarea class="form-control" id="content" name="content" rows="15"
                              required></textarea>
                </div>
                <input type="hidden" id="memberSeq" name="memberSeq" value="${memberSeq}">
                <div class="d-flex justify-content-between">
                    <button type="button" class="btn btn-secondary"
                            onclick="location.href='/mojipBoard'">이전
                    </button>
                    <button type="submit" class="btn btn-primary">등록</button>
                </div>
            </form>
            <!-- End Form for creating a post -->
        </div>
    </div>

    <script>
      document.addEventListener("DOMContentLoaded", function () {
        var applyStartDateInput = document.getElementById('applyStartDate');
        var applyEndDateInput = document.getElementById('applyEndDate');
        var experienceDateInput = document.getElementById('experienceDate');

        var today = new Date().toISOString().split('T')[0];
        applyStartDateInput.value = today;
        applyEndDateInput.min = today;

        // Add event listener to applyEndDate to set the minimum date for experienceDate
        applyEndDateInput.addEventListener('change', function () {
          experienceDateInput.setAttribute('min', applyEndDateInput.value);
        });

        document.getElementById('createPostForm').addEventListener('submit', function (event) {
          var applyStartDate = applyStartDateInput.value;
          var applyEndDate = applyEndDateInput.value;
          var experienceDate = experienceDateInput.value;

          if (applyEndDate < applyStartDate) {
            alert('모집 마감일은 모집 시작일 이후로 선택해야 합니다.');
            event.preventDefault();
            return false;
          }

          if (experienceDate < applyEndDate) {
            alert('체험 날짜는 모집 마감일 이후로 선택해야 합니다.');
            event.preventDefault();
            return false;
          }
        });
      });
    </script>

    <script>
      document.getElementById('createPostForm').addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent the default form submission

        var formData = new URLSearchParams(new FormData(this));

        fetch("/mojipBoard/createPost", {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          body: formData.toString()
        })
        .then(response => {
          if (response.ok) {
            return response.text().then(data => {
              console.log(data);
              alert('게시글이 성공적으로 등록되었습니다.');
              location.replace("/mojipBoard");
            });
          } else {
            response.text().then(data => {
              console.error(data);
              alert('게시글 등록에 실패했습니다. 다시 시도해 주세요.');
            });
          }
        })
        .catch(error => console.error('Error:', error));
      });
    </script>

</main><!-- End #main -->

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