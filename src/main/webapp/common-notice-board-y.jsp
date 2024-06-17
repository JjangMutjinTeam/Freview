<%@ page import="com.nuguna.freview.entity.member.Member" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>공지게시판</title>
    <%
        Member loginUser = (Member) session.getAttribute("Member");
        Integer memberSeq = loginUser.getMemberSeq();
        String gubun = loginUser.getGubun();
        request.setAttribute("gubun", gubun);
        request.setAttribute("memberSeq", memberSeq);
    %>
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
<%--    세션의 member 정보를 전부 가져오기 (memberSeq, gubun 외)--%>
<%--    <div class="header-hr-right">--%>
<%--        <a href="/brand-page?gubun=${member.gubun}&mid=${member.mid}" style="margin-right: 20px">--%>
<%--            <%=memberInfo.getNickname()%>--%>
<%--            <img src="<%=memberInfo.getPhotoUrl()%>" alt=" " style="width: 30px;--%>
<%--    margin-top: 15px;">--%>
<%--        </a>--%>
<%--        <a href="COMM_logout.jsp" style="margin-top: 17px;">로그아웃</a>--%>
<%--    </div>--%>
</header><!-- End Header -->
<main id="main" class="main">

    <div class="pagetitle">
        <h1>공지게시판</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="/noticeBoard">Home</a></li>
                <li class="breadcrumb-item">Pages</li>
                <li class="breadcrumb-item active">Blank</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <div class="card">
        <div class="card-body">
            <h5 class="card-title">공지 게시판</h5>
            <p>매우 중요한 공지가 올라옵니다 <br></p>

            <c:if test="${gubun == 'A'}">
                <div class="d-flex justify-content-end">
                    <a href="/noticeBoard/createPost" class="btn btn-primary">
                        공지 등록
                    </a>
                </div>
            </c:if>
            <!-- Table with stripped rows -->
            <table class="table">
                <thead>
                <tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성일자</th>
                    <th>수정일자</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${postList}" var="post" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/noticeBoard/detail?postId=${post.postSeq}">${post.title}</a>
                        </td>
                        <td>${post.createdAt}</td>
                        <td>${post.updatedAt}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="d-flex justify-content-between">
                <c:if test="${previousPostSeq != Integer.MAX_VALUE}">
                    <a class="btn btn-primary" href="?previousPostSeq=${postList[0].postSeq + 11}">이전</a>
                </c:if>
                <c:if test="${!empty postList && postList.size() == 10}">
                    <a class="btn btn-primary"
                       href="?previousPostSeq=${postList[postList.size() - 1].postSeq}">다음</a>
                </c:if>
            </div>

        </div>
    </div>

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