<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Tables / Data - NiceAdmin Bootstrap Template</title>
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

    <!-- =======================================================
    * Template Name: NiceAdmin
    * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
    * Updated: Apr 20 2024 with Bootstrap v5.3.3
    * Author: BootstrapMade.com
    * License: https://bootstrapmade.com/license/
    ======================================================== -->
</head>

<body>

<!-- 탈퇴 모달 창 -->
<div class="modal fade" id="deleteModal" tabindex="-1" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="deleteModalLabel">정말 삭제할까요?</h5><br>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                스토어 삭제를 원하시면 관리자 비밀번호를 입력해주세요
                <form id="deleteForm">
                    <div class="mb-3">
                        <label for="password" class="form-label">비밀번호</label>
                        <input type="password" class="form-control" id="password" required>
                    </div>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button> <!-- 추가된 취소 버튼 -->
                    <button type="submit" class="btn btn-primary">확인</button>
                </form>
            </div>
        </div>
    </div>
</div>

<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registerModal">
    스토어 등록
</button>

<!-- 스토어 등록 모달 창 -->
<div class="modal fade" id="registerModal" tabindex="-1" aria-labelledby="registerModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="registerModalLabel">스토어 등록</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                등록할 스토어의 정보를 입력해주세요
                <form id="registerForm">
                    <div class="mb-3">
                        <label for="storeName" class="form-label">스토어명</label>
                        <input type="text" class="form-control" id="storeName" name="addStoreName" required>
                    </div>
                    <div class="mb-3">
                        <label for="businessNumber" class="form-label">사업자번호</label>
                        <input type="text" class="form-control" id="businessNumber" name="addBusinessNumber" pattern="\d{3}-\d{2}-\d{5}" placeholder="123-45-67890" required>
                    </div>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                    <button type="submit" class="btn btn-primary">등록완료</button>
                </form>
            </div>
        </div>
    </div>
</div>



<!-- ======= Header ======= -->
<header id="header" class="header fixed-top d-flex align-items-center">

    <div class="d-flex align-items-center justify-content-between">
        <a href="index.html" class="logo d-flex align-items-center">
            <img src="/assets/img/logo.png" alt="">
            <span class="d-none d-lg-block">NiceAdmin</span>
        </a>
        <i class="bi bi-list toggle-sidebar-btn"></i>
    </div><!-- End Logo -->

    <div class="search-bar">
        <form class="search-form d-flex align-items-center" method="POST" action="#">
            <input type="text" name="query" placeholder="Search" title="Enter search keyword">
            <button type="submit" title="Search"><i class="bi bi-search"></i></button>
        </form>
    </div><!-- End Search Bar -->

    <nav class="header-nav ms-auto">
        <ul class="d-flex align-items-center">

            <li class="nav-item d-block d-lg-none">
                <a class="nav-link nav-icon search-bar-toggle " href="#">
                    <i class="bi bi-search"></i>
                </a>
            </li><!-- End Search Icon-->

            <li class="nav-item dropdown">

                <a class="nav-link nav-icon" href="#" data-bs-toggle="dropdown">
                    <i class="bi bi-bell"></i>
                    <span class="badge bg-primary badge-number">4</span>
                </a><!-- End Notification Icon -->

                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow notifications">
                    <li class="dropdown-header">
                        You have 4 new notifications
                        <a href="#"><span class="badge rounded-pill bg-primary p-2 ms-2">View all</span></a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li class="notification-item">
                        <i class="bi bi-exclamation-circle text-warning"></i>
                        <div>
                            <h4>Lorem Ipsum</h4>
                            <p>Quae dolorem earum veritatis oditseno</p>
                            <p>30 min. ago</p>
                        </div>
                    </li>

                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li class="notification-item">
                        <i class="bi bi-x-circle text-danger"></i>
                        <div>
                            <h4>Atque rerum nesciunt</h4>
                            <p>Quae dolorem earum veritatis oditseno</p>
                            <p>1 hr. ago</p>
                        </div>
                    </li>

                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li class="notification-item">
                        <i class="bi bi-check-circle text-success"></i>
                        <div>
                            <h4>Sit rerum fuga</h4>
                            <p>Quae dolorem earum veritatis oditseno</p>
                            <p>2 hrs. ago</p>
                        </div>
                    </li>

                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li class="notification-item">
                        <i class="bi bi-info-circle text-primary"></i>
                        <div>
                            <h4>Dicta reprehenderit</h4>
                            <p>Quae dolorem earum veritatis oditseno</p>
                            <p>4 hrs. ago</p>
                        </div>
                    </li>

                    <li>
                        <hr class="dropdown-divider">
                    </li>
                    <li class="dropdown-footer">
                        <a href="#">Show all notifications</a>
                    </li>

                </ul><!-- End Notification Dropdown Items -->

            </li><!-- End Notification Nav -->

            <li class="nav-item dropdown">

                <a class="nav-link nav-icon" href="#" data-bs-toggle="dropdown">
                    <i class="bi bi-chat-left-text"></i>
                    <span class="badge bg-success badge-number">3</span>
                </a><!-- End Messages Icon -->

                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow messages">
                    <li class="dropdown-header">
                        You have 3 new messages
                        <a href="#"><span class="badge rounded-pill bg-primary p-2 ms-2">View all</span></a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li class="message-item">
                        <a href="#">
                            <img src="/assets/img/messages-1.jpg" alt="" class="rounded-circle">
                            <div>
                                <h4>Maria Hudson</h4>
                                <p>Velit asperiores et ducimus soluta repudiandae labore officia est ut...</p>
                                <p>4 hrs. ago</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li class="message-item">
                        <a href="#">
                            <img src="/assets/img/messages-2.jpg" alt="" class="rounded-circle">
                            <div>
                                <h4>Anna Nelson</h4>
                                <p>Velit asperiores et ducimus soluta repudiandae labore officia est ut...</p>
                                <p>6 hrs. ago</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li class="message-item">
                        <a href="#">
                            <img src="/assets/img/messages-3.jpg" alt="" class="rounded-circle">
                            <div>
                                <h4>David Muldon</h4>
                                <p>Velit asperiores et ducimus soluta repudiandae labore officia est ut...</p>
                                <p>8 hrs. ago</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li class="dropdown-footer">
                        <a href="#">Show all messages</a>
                    </li>

                </ul><!-- End Messages Dropdown Items -->

            </li><!-- End Messages Nav -->

            <li class="nav-item dropdown pe-3">

                <a class="nav-link nav-profile d-flex align-items-center pe-0" href="#" data-bs-toggle="dropdown">
                    <img src="/assets/img/profile-img.jpg" alt="Profile" class="rounded-circle">
                    <span class="d-none d-md-block dropdown-toggle ps-2">K. Anderson</span>
                </a><!-- End Profile Iamge Icon -->

                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
                    <li class="dropdown-header">
                        <h6>Kevin Anderson</h6>
                        <span>Web Designer</span>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="users-profile.html">
                            <i class="bi bi-person"></i>
                            <span>My Profile</span>
                        </a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="users-profile.html">
                            <i class="bi bi-gear"></i>
                            <span>Account Settings</span>
                        </a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="pages-faq.html">
                            <i class="bi bi-question-circle"></i>
                            <span>Need Help?</span>
                        </a>
                    </li>
                    <li>
                        <hr class="dropdown-divider">
                    </li>

                    <li>
                        <a class="dropdown-item d-flex align-items-center" href="#">
                            <i class="bi bi-box-arrow-right"></i>
                            <span>Sign Out</span>
                        </a>
                    </li>

                </ul><!-- End Profile Dropdown Items -->
            </li><!-- End Profile Nav -->

        </ul>
    </nav><!-- End Icons Navigation -->

</header><!-- End Header -->

<!-- ======= Sidebar ======= -->
<aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">
        <li class="nav-item">
            <a class="nav-link " data-bs-target="#tables-nav" data-bs-toggle="collapse" href="#">
                <i class="bi bi-layout-text-window-reverse"></i><span>관리</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="tables-nav" class="nav-content collapse show" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="/AdminPage/user">
                        <i class="bi bi-circle"></i><span>유저</span>
                    </a>
                </li>
                <li>
                    <a href="/AdminPage/store" class="active">
                        <i class="bi bi-circle"></i><span>스토어</span>
                    </a>
                </li>
            </ul>
        </li><!-- End Tables Nav -->
    </ul>

</aside><!-- End Sidebar-->

<main id="main" class="main">

    <div class="pagetitle">
        <h1>스토어</h1>
        <nav>
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="index.html">Home</a></li>
                <li class="breadcrumb-item">Tables</li>
                <li class="breadcrumb-item active">Data</li>
            </ol>
        </nav>
    </div><!-- End Page Title -->

    <section class="section">
        <div class="row">
            <div class="col-lg-12">

                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">스토어 리스트</h5>
                        <p>전국 스토어 리스트와 가입한 사장님 목록입니다 <br>
                            아이디를 클릭하면 해당 유저의 브랜딩 페이지로 이동할 수 있습니다 <br>
                            가입한 사장님이 있는 스토어는 삭제할 수 없습니다 <br>
                        </p>
                        <div class="d-flex justify-content-end">
                            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#registerModal">
                                스토어 등록
                            </button>
                        </div>

                        <!-- Table with stripped rows -->
                        <table class="table datatable">
                            <thead>
                            <tr>
                                <th>번호</th>
                                <th>스토어명</th>
                                <th>사업자번호</th>
                                <th>가입한 사장님 아이디</th>
                                <th data-type="date" data-format="YYYY/DD/MM">사장님 가입일자</th>
                                <th>삭제</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${storeAndBossList}" var="store" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${store.storeName}</td>
                                    <td>${store.businessNumber}</td>
                                    <td><a href="MemberBrandingServlet?mid=${store.mid}">${store.mid}</a></td>
                                    <td>${store.createdAt}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${store.mid == null}">
                                                <button class="btn btn-danger btn-sm" data-bs-toggle="modal"
                                                        data-bs-target="#deleteModal"
                                                        data-id="${store.businessNumber}">x
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button class="btn btn-secondary btn-sm" disabled>x</button>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <!-- End Table with stripped rows -->
                        <script>
                          var deleteModal = document.getElementById('deleteModal');
                          deleteModal.addEventListener('show.bs.modal', function (event) {
                            var button = event.relatedTarget;
                            var businessNumber = button.getAttribute('data-id');
                            var modalTitle = deleteModal.querySelector('.modal-title');
                            var modalBodyInput = deleteModal.querySelector('.modal-body input');

                            modalTitle.textContent = '정말 삭제시킬까요? (사업자번호: ' + businessNumber + ')';
                            modalBodyInput.value = '';

                            document.getElementById('deleteForm').onsubmit = function(event) {
                              event.preventDefault();
                              var password = modalBodyInput.value;

                              fetch('/AdminPage/store', {
                                method: 'POST',
                                headers: {
                                  'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: 'deleteBusinessNumber=' + encodeURIComponent(businessNumber) + '&password=' + encodeURIComponent(password)
                              })
                              .then(response => {
                                if (response.status === 200) {
                                  return response.text().then(data => {
                                    console.log(data);
                                    alert('정상적으로 삭제 처리 되었습니다.');
                                    var modal = bootstrap.Modal.getInstance(deleteModal);
                                    modal.hide();
                                    location.reload();
                                  });
                                } else if (response.status === 401) {
                                  modalBodyInput.classList.add('is-invalid');
                                  var invalidFeedback = deleteModal.querySelector('.invalid-feedback');
                                  if (!invalidFeedback) {
                                    invalidFeedback = document.createElement('div');
                                    invalidFeedback.classList.add('invalid-feedback');
                                    invalidFeedback.textContent = '비밀번호가 잘못되었습니다.';
                                    modalBodyInput.parentNode.appendChild(invalidFeedback);
                                  }
                                } else {
                                  return response.text().then(data => {
                                    console.error(data);
                                    alert('오류가 발생했습니다. 다음에 다시 시도해주세요.');
                                  });
                                }
                              })
                              .catch(error => console.error('Error:', error));
                            };
                          });
                        </script>
                        <script>
                          var registerModal = document.getElementById('registerModal');
                          registerModal.addEventListener('show.bs.modal', function (event) {
                            document.getElementById('registerForm').onsubmit = function(event) {
                              event.preventDefault(); // Prevent default form submission

                              var storeName = document.getElementById('storeName').value;
                              var businessNumber = document.getElementById('businessNumber').value;

                              // Prepare data to be sent
                              var formData = new URLSearchParams();
                              formData.append('addStoreName', storeName);
                              formData.append('addBusinessNumber', businessNumber);

                              // Send the data using fetch
                              fetch('/AdminPage/addStore', {
                                method: 'POST',
                                headers: {
                                  'Content-Type': 'application/x-www-form-urlencoded'
                                },
                                body: formData.toString()
                              })
                              .then(response => {
                                if (response.status === 200) {
                                  return response.text().then(data => {
                                    console.log(data);
                                    alert('스토어가 성공적으로 등록되었습니다.');
                                    var modal = bootstrap.Modal.getInstance(registerModal);
                                    modal.hide();
                                    location.reload();
                                  });
                                } else {
                                  return response.text().then(data => {
                                    console.error(data);
                                    alert('스토어 등록에 실패했습니다. 다시 시도해 주세요.');
                                  });
                                }
                              })
                              .catch(error => console.error('Error:', error));

                              // Close the modal
                              $('#registerModal').modal('hide');
                            };
                          });
                        </script>

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
