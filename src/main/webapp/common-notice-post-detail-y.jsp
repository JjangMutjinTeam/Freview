<%@ page import="com.nuguna.freview.entity.member.Member" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>공지글 상세보기</title>
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
    <link href="/assets/img/favicon.png" rel="icon">
    <link href="/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
          rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
    <link href="/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
    <link href="/assets/vendor/quill/quill.snow.css" rel="stylesheet">
    <link href="/assets/vendor/quill/quill.bubble.css" rel="stylesheet">
    <link href="/assets/vendor/remixicon/remixicon.css" rel="stylesheet">
    <link href="/assets/vendor/simple-datatables/style.css" rel="stylesheet">

    <!-- Template Main CSS File -->
    <link href="/assets/css/style.css" rel="stylesheet">
    <style>
      /* Custom CSS to make all table rows white */
      table tbody tr {
        background-color: white !important;
      }
    </style>
    <style>
      .content-cell {
        height: 200px; /* 높이를 약 10줄 정도로 설정 */
        vertical-align: top;
        white-space: pre-wrap; /* 띄어쓰기 인식 */
      }
    </style>
    <style>
      .fixed-width {
        width: 150px;
        word-wrap: break-word;
      }

      .table {
        table-layout: fixed;
        width: 100%;
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
                    <span id="nickname-holder-head"
                          class="d-none d-md-block">${brandInfo.nickname}</span>
                </a><!-- End Profile Iamge Icon -->
            </li><!-- End Profile Nav -->
        </ul>
    </nav><!-- End Icons Navigation -->
</header><!-- End Header -->

<main id="main" class="main">

    <div class="pagetitle">
        <h1>공지게시판</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                <li class="breadcrumb-item">Pages</li>
                <li class="breadcrumb-item active">Blank</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <div class="container">
        <div class="card mt-4">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="card-title mb-0">공지</h5>
                    <c:if test="${memberSeq == POST.memberSeq}">
                        <div>
                            <button type="button" class="btn btn-danger" onclick="confirmDelete()">
                                삭제
                            </button>
                            <button type="button" class="btn btn-primary" onclick="editPost()">수정
                            </button>
                        </div>
                    </c:if>
                    <div>
                        <button type="button" class="btn btn-primary"
                                onclick="location.href='/noticeBoard'">목록으로
                        </button>
                    </div>
                </div>
                <form id="postForm" action="/noticeBoard/detail/update" method="post">
                    <input type="hidden" name="postSeq" value="${POST.postSeq}">
                    <table class="table table-bordered" style="table-layout: fixed; width: 100%;">
                        <tbody>
                        <tr>
                            <th class="fixed-width">제목</th>
                            <td>
                                <span id="titleView">${POST.title}</span>
                                <input type="text" class="form-control d-none" id="titleEdit"
                                       name="title" value="${POST.title}">
                            </td>
                        </tr>
                        <tr>
                            <th class="fixed-width">작성자</th>
                            <td>${POST.memberSeq}</td>
                        </tr>
                        <tr>
                            <th class="fixed-width">작성일자</th>
                            <td>${POST.createdAt}</td>
                        </tr>
                        <tr>
                            <th class="fixed-width">수정일자</th>
                            <td>${POST.updatedAt}</td>
                        </tr>
                        <tr>
                            <th class="fixed-width">내용</th>
                            <td>
                                <span id="contentView"
                                      style="white-space: pre-line;">${POST.content}</span>
                                <textarea class="form-control d-none" id="contentEdit"
                                          name="content" rows="10">${POST.content}</textarea>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div id="editButtons" class="d-none">
                        <button type="submit" class="btn btn-primary">완료</button>
                        <button type="button" class="btn btn-secondary" onclick="cancelEdit()">취소
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel"
         aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteModalLabel">삭제 확인</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    정말 삭제하겠습니까?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소
                    </button>
                    <button type="button" class="btn btn-danger" onclick="deletePost()">확인</button>
                </div>
            </div>
        </div>
    </div>

    <script>
      function editPost() {
        document.getElementById('titleView').classList.add('d-none');
        document.getElementById('contentView').classList.add('d-none');
        document.getElementById('titleEdit').classList.remove('d-none');
        document.getElementById('contentEdit').classList.remove('d-none');
        document.getElementById('editButtons').classList.remove('d-none');
      }

      function cancelEdit() {
        document.getElementById('titleView').classList.remove('d-none');
        document.getElementById('contentView').classList.remove('d-none');
        document.getElementById('titleEdit').classList.add('d-none');
        document.getElementById('contentEdit').classList.add('d-none');
        document.getElementById('editButtons').classList.add('d-none');
      }

      document.getElementById('postForm').addEventListener('submit', function (event) {
        event.preventDefault();

        var formData = new URLSearchParams(new FormData(this));

        fetch('/noticeBoard/detail/update', {
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
              alert('게시글이 성공적으로 수정되었습니다.');
              location.replace("/noticeBoard")
            })
          } else {
            response.text().then(data => {
              console.error(data);
              alert('게시글 수정에 실패했습니다. 다시 시도해 주세요.');
            });
          }
        })
        .catch(error => console.error('Error:', error));
      });

      function confirmDelete() {
        var deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
        deleteModal.show();
      }

      function deletePost() {
        var postSeq = document.querySelector('input[name="postSeq"]').value;

        fetch('/noticeBoard/detail/delete', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          body: new URLSearchParams({postSeq: postSeq}).toString()
        })
        .then(response => {
          if (response.ok) {
            return response.text().then(data => {
              console.log(data);
              alert('게시글이 성공적으로 삭제되었습니다.');
              location.replace("/noticeBoard");
            });
          } else {
            response.text().then(data => {
              console.error(data);
              alert('게시글 삭제에 실패했습니다. 다시 시도해 주세요.');
            });
          }
        })
        .catch(error => console.error('Error:', error));
      }
    </script>

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
<script src="/assets/vendor/apexcharts/apexcharts.min.js"></script>
<script src="/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/assets/vendor/chart.js/chart.umd.js"></script>
<script src="/assets/vendor/echarts/echarts.min.js"></script>
<script src="/assets/vendor/quill/quill.js"></script>
<script src="/assets/vendor/simple-datatables/simple-datatables.js"></script>
<script src="/assets/vendor/tinymce/tinymce.min.js"></script>
<script src="/assets/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="/assets/js/main.js"></script>

</body>

</html>