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

    <title>추천 페이지</title>
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
      .profile-card img {
        border-radius: 0; /* 이미지를 네모로 만듭니다 */
        width: 100%; /* 이미지를 카드의 전체 너비로 설정합니다 */
        max-width: none; /* 이미지를 카드의 전체 너비로 설정합니다 */
      }

      .profile-card {
        padding: 0; /* 카드 내부의 여백을 없앱니다 */
      }

      .col-xl-2 {
        flex: 0 0 auto;
        width: 20%;
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
                    <span id="nickname-holder-head"
                          class="d-none d-md-block"><%=loginUser.getNickname()%></span>
                </a><!-- End Profile Iamge Icon -->
            </li><!-- End Profile Nav -->
        </ul>
    </nav><!-- End Icons Navigation -->
</header><!-- End Header -->
<main id="main" class="main">

    <div class="pagetitle">
        <h1>추천페이지</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/recommendation">Home</a></li>
                <li class="breadcrumb-item">Users</li>
                <li class="breadcrumb-item active">Profile</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section profile">
        <div class="row">
            <!-- Tabs navigation -->
            <ul class="nav nav-tabs" role="tablist">
                <li class="nav-item">
                    <a class="nav-link active" id="boss-tab" data-bs-toggle="tab" href="#boss"
                       role="tab" aria-controls="boss" aria-selected="true">Boss</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="customer-tab" data-bs-toggle="tab" href="#customer"
                       role="tab" aria-controls="customer" aria-selected="false">Customer</a>
                </li>
            </ul>
            <div class="tab-content">
                <!-- Boss Tab -->
                <div class="tab-pane fade show active" id="boss" role="tabpanel"
                     aria-labelledby="boss-tab">
                    <div class="row">
                        <c:forEach var="boss" items="${bossInfoList}">
                            <div class="col-xl-2">
                                <div class="card">
                                    <div class="card-body profile-card pt-4 d-flex flex-column align-items-center">
                                        <a href="/MemberBrandingServlet?gubun=B&mid=${boss.mid}">
                                            <img src="${boss.profilePhotoUrl}" alt="Profile"
                                                 class="profile-img">
                                        </a>
                                        <h2>
                                            <a href="/MemberBrandingServlet?gubun=B&mid=${boss.mid}">${boss.nickname}</a>
                                        </h2>
                                        <h3>${boss.foodTypes}</h3>
                                        <h3>${boss.tags}</h3>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <!-- Customer Tab -->
                <div class="tab-pane fade" id="customer" role="tabpanel"
                     aria-labelledby="customer-tab">
                    <div class="row">
                        <c:forEach var="customer" items="${customerInfoList}">
                            <div class="col-xl-2">
                                <div class="card">
                                    <div class="card-body profile-card pt-4 d-flex flex-column align-items-center">
                                        <a href="MemberBrandingServlet?gubun=C&mid=${customer.mid}">
                                            <img src="${customer.profilePhotoUrl}" alt="Profile"
                                                 class="profile-img">
                                        </a>
                                        <h2>
                                            <a href="MemberBrandingServlet?gubun=C&mid=${customer.mid}">${customer.nickname}</a>
                                        </h2>
                                        <h3>${customer.foodTypes}</h3>
                                        <h3>${customer.tags}</h3>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </section>

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