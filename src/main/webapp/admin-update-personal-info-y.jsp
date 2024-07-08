<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="loginUser" value="${requestScope.loginUser}"/>
<c:set var="memberSeq" value="${loginUser.memberSeq}"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>관리자 페이지</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="/assets/img/favicon.png" rel="icon">
    <link href="/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

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
    <link href="/assets/css/hr.css" rel="stylesheet">

    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>

    <!-- Day.js -->
    <script src="https://cdn.jsdelivr.net/npm/dayjs@1.10.7/dayjs.min.js"></script>

    <!-- =======================================================
    * Template Name: NiceAdmin
    * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
    * Updated: Apr 20 2024 with Bootstrap v5.3.3
    * Author: BootstrapMade.com
    * License: https://bootstrapmade.com/license/
    ======================================================== -->
</head>

<style>
  .sidebar {
    display: flex;
    flex-direction: column;
    height: 100vh; /* Full viewport height */
  }

  .sidebar-nav {
    flex: 1; /* Takes up available space */
    overflow-y: auto; /* Scroll if necessary */
  }

  .sidebar-nav:last-child {
    margin-top: auto; /* Pushes the last ul to the bottom */
  }

  .nav-link {
    display: flex;
    align-items: center;
  }
</style>

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
        <i class="bi bi-list toggle-sidebar-btn"></i>
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

<!-- ======= Sidebar ======= -->
<aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">

        <li class="nav-item">
            <a class="nav-link " data-bs-target="#tables-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-layout-text-window-reverse"></i><span>관리</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="tables-nav" class="nav-content collapse show" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="/admin-member-management">
                        <i class="bi bi-circle"></i><span>멤버</span>
                    </a>
                </li>
                <li>
                    <a href="/admin-store-management">
                        <i class="bi bi-circle"></i><span>스토어</span>
                    </a>
                </li>
            </ul>
        </li>
        <ul class="sidebar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/personal-info-update">
                    <i class="bi bi-person"></i><span>개인정보수정</span>
                </a>
            </li>
        </ul>
    </ul>

</aside><!-- End Sidebar-->

<main id="main" class="main">

    <div class="pagetitle">
        <h1>개인정보수정</h1>
    </div>

    <div class="card">
        <div class="card-body">

            <div id="memberInfo">
            </div>
        </div>
    </div>

</main>

<script>
    $(document).ready(function () {

      loadInitialData();

      function loadInitialData() {
        $.ajax({
          method: "POST",
          url: "/personal-info-update",
          data: {
            memberSeq : ${memberSeq}
          },
          dataType: "json",
          success: function (response) {
            $('#memberInfo').empty();
            renderData(response.data);
          },
          error: function () {
            console.error("[ERROR] 데이터 초기화 중 오류 발생");
          }
        });
      }

      function renderData(data) {
        var htmlStr = "";

        htmlStr += '<div class="mb-3">';
        htmlStr += '  <label for="email" class="form-label">닉네임</label>';
        htmlStr += '  <div class="input-group">';
        htmlStr += '    <input type="text" id="nickname" class="form-control" value="' + data["nickname"] + '">';
        htmlStr += '    <button class="btn btn-primary" type="button" onclick="showNicknameModal()">수정</button>';
        htmlStr += '  </div>';
        htmlStr += '</div>';

        htmlStr += '<div class="mb-3">';
        htmlStr += '  <label for="id" class="form-label">아이디</label>';
        htmlStr += '  <input type="text" id="id" class="form-control" value="' + data["id"] + '" readonly>';
        htmlStr += '</div>';

        htmlStr += '<div class="mb-3">';
        htmlStr += '  <label for="password" class="form-label">비밀번호</label>';
        htmlStr += '  <div class="input-group">';
        htmlStr += '    <input type="password" id="password" class="form-control" value="' + data["password"] + '">';
        htmlStr += '    <button class="btn btn-primary" type="button" onclick="showPasswordModal()">수정</button>';
        htmlStr += '  </div>';
        htmlStr += '</div>';

        htmlStr += '<div class="mb-3">';
        htmlStr += '  <label for="email" class="form-label">이메일</label>';
        htmlStr += '  <div class="input-group">';
        htmlStr += '    <input type="text" id="email" class="form-control" value="' + data["email"] + '">';
        htmlStr += '    <button class="btn btn-primary" type="button" onclick="showEmailModal()">수정</button>';
        htmlStr += '  </div>';
        htmlStr += '</div>';

        $('#memberInfo').html(htmlStr);
      }

      $('#passwordForm').on('submit', function(event) {
        event.preventDefault();

        let currentPassword = $('#currentPassword').val();
        let newPassword = $('#newPassword').val();
        let confirmPassword = $('#confirmPassword').val();

        let reg = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$/;
        if (!reg.test(newPassword)) {
          alert('비밀번호는 영문자와 숫자를 포함하여 8자 이상 25자 이하로 입력해주세요.');
          return;
        }

        if (newPassword !== confirmPassword) {
          alert('새 비밀번호와 비밀번호 확인이 일치하지 않습니다.');
          return;
        }

        $.ajax({
          type: 'POST',
          url: '/password-update',
          data: {
            currentPassword: currentPassword,
            newPassword: newPassword,
            memberSeq: ${memberSeq}
          },
          success: function(response) {
            alert('비밀번호가 성공적으로 수정되었습니다.');
            $('#passwordModal').modal('hide');
            location.replace("/personal-info-update");
          },
          error: function(error) {
            alert('비밀번호 수정에 실패했습니다. 다시 시도해 주세요.');
            console.error(error);
          }
        });
      });

      $('#nicknameForm').on('submit', function(event) {
        event.preventDefault();

        let newNickname = $('#newNickname').val();

        $.ajax({
          type: 'POST',
          url: '/nickname-update',
          data: {
            memberSeq : ${memberSeq},
            newNickname: newNickname,
          },
          success: function(response) {
            alert('닉네임이 성공적으로 수정되었습니다.');
            $('#passwordModal').modal('hide');
            location.replace("/personal-info-update");
          },
          error: function(error) {
            alert('닉네임 수정에 실패했습니다. 다시 시도해 주세요.');
            console.error(error);
          }
        });
      });


    });

    function showPasswordModal() {
      $('#passwordModal').modal('show');
    }

    function showEmailModal() {
      $('#emailModal').modal('show');
    }

    function showNicknameModal() {
      // 여기에 이메일 수정 모달 표시 코드를 작성합니다.
      $('#nicknameModal').modal('show');
    }

</script>

<!-- 비밀번호 수정 모달 -->
<div class="modal fade" id="passwordModal" tabindex="-1" aria-labelledby="passwordModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="passwordModalLabel">비밀번호 수정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="passwordForm">
                    <div class="mb-3">
                        <label for="newPassword" class="form-label">현재 비밀번호</label>
                        <input type="password" class="form-control" id="currentPassword" required>
                    </div>
                    <div class="mb-3">
                        <label for="newPassword" class="form-label">새 비밀번호</label>
                        <input type="password" class="form-control" id="newPassword" required>
                    </div>
                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label">비밀번호 확인</label>
                        <input type="password" class="form-control" id="confirmPassword" required>
                    </div>
                    <button type="submit" class="btn btn-primary">확인</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 이메일 수정 모달 -->
<div class="modal fade" id="emailModal" tabindex="-1" aria-labelledby="emailModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="emailModalLabel">이메일 수정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="emailForm">
                    <div class="mb-3">
                        <label for="newEmail" class="form-label">새 이메일</label>
                        <div class="input-group">
                            <input type="email" class="form-control" id="newEmail" required>
                            <button type="button" class="btn btn-outline-primary">인증번호</button>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="verificationCode" class="form-label">인증번호확인</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="verificationCode" required>
                            <button type="button" class="btn btn-outline-primary">확인</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- 닉네임 수정 모달 -->
<div class="modal fade" id="nicknameModal" tabindex="-1" aria-labelledby="nicknameModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="nicknameModalLabel">닉네임 수정</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="nicknameForm">
                    <div class="mb-3">
                        <label for="newNickname" class="form-label">새 닉네임</label>
                        <input type="text" class="form-control" id="newNickname" required>
                    </div>
                    <button type="submit" class="btn btn-primary">확인</button>
                </form>
            </div>
        </div>
    </div>
</div>

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

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

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
