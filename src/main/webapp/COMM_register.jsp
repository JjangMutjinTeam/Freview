<%--
  Created by IntelliJ IDEA.
  User: rlagk
  Date: 2024-06-04
  Time: 오후 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Pages / Register - NiceAdmin Bootstrap Template</title>
    <meta content="" name="description">
    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="assets/img/favicon.png" rel="icon">
    <link href="assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

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
    <link href="assets/css/hr.css" rel="stylesheet">
    <!-- =======================================================
    * Template Name: NiceAdmin
    * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
    * Updated: Apr 20 2024 with Bootstrap v5.3.3
    * Author: BootstrapMade.com
    * License: https://bootstrapmade.com/license/
    ======================================================== -->
    <script
            src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
            crossorigin="anonymous"></script>
</head>

<body>

<main>
    <div class="container">

        <section class="section register min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center">

                        <div class="d-flex justify-content-center py-4">
                            <a href="index.html" class="logo d-flex align-items-center w-auto">
                                <img src="assets/img/logo.png" alt="">
                                <span class="d-none d-lg-block">NiceAdmin</span>
                            </a>
                        </div><!-- End Logo -->

                        <div class="card mb-3">

                            <div class="card-body">

                                <div class="pt-4 pb-2">
                                    <h5 class="card-title text-center pb-0 fs-4">회원가입</h5>
                                </div>
                                <div class="card">
                                    <div class="card-body" style=width:500px>
                                        <!-- Bordered Tabs Justified -->
                                        <ul class="nav nav-tabs nav-tabs-bordered d-flex" id="borderedTabJustified" role="tablist">
                                            <li class="nav-item flex-fill" role="presentation">
                                                <button class="nav-link w-100" id="home-tab" data-bs-toggle="tab" data-bs-target="#bordered-justified-home" type="button" role="tab" aria-controls="home" aria-selected="false" tabindex="-1">체험단</button>
                                            </li>
                                            <li class="nav-item flex-fill" role="presentation">
                                                <button class="nav-link w-100 active" id="profile-tab" data-bs-toggle="tab" data-bs-target="#bordered-justified-profile" type="button" role="tab" aria-controls="profile" aria-selected="true">사장님</button>
                                            </li>
                                        </ul>
                                        <div class="tab-content pt-2" id="borderedTabJustifiedContent">
                                            <div class="tab-pane fade" id="bordered-justified-home" role="tabpanel" aria-labelledby="home-tab">
                                                <p>협찬을 제공받고 온라인 리뷰를 제출하는 체험단 입니다</p>
                                                <form class="row g-3 needs-validation" novalidate>
                                                    <div class="col-12">
                                                        <label for="Input_ID" class="form-label">아이디</label>
                                                        <div style = display:flex>
                                                            <input type="text" name="id" class="form-control" id="Input_ID" required>
                                                            <button type="button" class="btn btn-outline-primary" id="COMM_register_IDcheckBTN"
                                                            style=
                                                                    "text-align: center;
                                                                    font-size: 13px;
                                                                    width: 110px;
                                                                    margin-left: 5px; "
                                                            >중복확인</button>
                                                        </div>
                                                        <div class="invalid-feedback">아이디를 입력해주세요</div>
                                                        <div id="COMM_register_IDavail" class="remove" style = color:cornflowerblue>아이디 사용 가능합니다</div>
                                                        <div id="COMM_register_IDdeny" class="remove" style = color:red >아이디 사용 불가합니다</div>
                                                    </div>
                                                    <div class="col-12">
                                                        <label for="Input_pw" class="form-label">비밀번호</label>
                                                        <input type="password" name="password" class="form-control" id="Input_pw" required>
                                                        <div class="invalid-feedback">Please enter your password!</div>
                                                        <div id="COMM_register_pwdeny" class="remove" style = color:red >영문 숫자 특수기호 조합 8자리 이상을 입력해주세요</div>
                                                        <div id="COMM_register_pwvali" class="remove" style = color:cornflowerblue>비밀번호 사용 가능합니다</div>
                                                    </div>
                                                    <div class="col-12">
                                                        <label for="Input_PasswordCheck" class="form-label">비밀번호확인</label>
                                                        <input type="password" class="form-control" id="Input_PasswordCheck">
                                                        <div id="COMM_register_pwCheckdeny" class="remove" style = color:red >비밀번호를 확인해주세요</div>
                                                        <div id="COMM_register_pwCheckvali" class="remove" style = color:cornflowerblue>통과</div>
                                                    </div>
                                                    <div class="col-12">
                                                            <label for="COMM_register_input_email" class="form-label">이메일</label>
                                                        <div style = display:flex>
                                                            <input type="email" name="email" class="form-control" id="COMM_register_input_email" required>
                                                            <button type="button" class="btn btn-outline-primary" id="COMM_register_emailBtn"
                                                                    style=
                                                                            "text-align: center;
                                                                    font-size: 13px;
                                                                    width: 110px;
                                                                    margin-left: 5px; "
                                                            >인증번호</button>
                                                        </div>
                                                        <div class="invalid-feedback">Please enter a valid Email adddress!</div>
                                                        <div class="col-12">
                                                            <label for="COMM_register_input_emailNumber" class="form-label">인증번호확인</label>
                                                            <div style = display:flex>
                                                                <input type="email" name="text" class="form-control" id="COMM_register_input_emailNumber" required disabled>
                                                                <button type="button" class="btn btn-outline-primary" id="COMM_register_emailNumberBtn"
                                                                        style=
                                                                                "text-align: center;
                                                                    font-size: 13px;
                                                                    width: 110px;
                                                                    margin-left: 5px; "
                                                                disabled>확인</button>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="col-12">
                                                        <label for="yourUsername" class="form-label">Username</label>
                                                        <div class="input-group has-validation">
                                                            <span class="input-group-text" >@</span>
                                                            <input type="text" name="username" class="form-control" id="yourUsername" required>
                                                            <div class="invalid-feedback">Please choose a username.</div>
                                                        </div>
                                                    </div>
                                                    <div class="col-12">
                                                        <div class="form-check">
                                                            <input class="form-check-input" name="terms" type="checkbox" value="" id="acceptTerms" required>
                                                            <label class="form-check-label" for="acceptTerms">I agree and accept the <a href="#">terms and conditions</a></label>
                                                            <div class="invalid-feedback">You must agree before submitting.</div>
                                                        </div>
                                                    </div>
                                                    <div class="col-12">
                                                        <button class="btn btn-primary w-100" type="submit">Create Account</button>
                                                    </div>
                                                    <div class="col-12">
                                                        <p class="small mb-0">Already have an account? <a href="pages-login.html">Log in</a></p>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="tab-pane fade active show" id="bordered-justified-profile" role="tabpanel" aria-labelledby="profile-tab">
                                                <p>협찬을 제공하고 온라인 리뷰를 제공받는 사장님 입니다</p>
                                                <form class="row g-3 needs-validation" novalidate>
                                                    <div class="col-12">
                                                        <label for="a" class="form-label">Your Name</label>
                                                        <input type="text" name="name" class="form-control" id="a" required>
                                                        <div class="invalid-feedback">Please, enter your name!</div>
                                                    </div>

                                                    <div class="col-12">
                                                        <label for="yourEmail" class="form-label">Your Email</label>
                                                        <input type="email" name="email" class="form-control"  required>
                                                        <div class="invalid-feedback">Please enter a valid Email adddress!</div>
                                                    </div>

                                                    <div class="col-12">
                                                        <label for="yourUsername" class="form-label">Username</label>
                                                        <div class="input-group has-validation">
                                                            <span class="input-group-text" id="inputGroupPrepend">@</span>
                                                            <input type="text" name="username" class="form-control"  required>
                                                            <div class="invalid-feedback">Please choose a username.</div>
                                                        </div>
                                                    </div>

                                                    <div class="col-12">
                                                        <label for="b" class="form-label">Password</label>
                                                        <input type="password" name="password" class="form-control" id="b"  required>
                                                        <div class="invalid-feedback">Please enter your password!</div>
                                                    </div>

                                                    <div class="col-12">
                                                        <div class="form-check">
                                                            <input class="form-check-input" name="terms" type="checkbox" value=""  required>
                                                            <label class="form-check-label" for="acceptTerms">I agree and accept the <a href="#">terms and conditions</a></label>
                                                            <div class="invalid-feedback">You must agree before submitting.</div>
                                                        </div>
                                                    </div>
                                                    <div class="col-12">
                                                        <button class="btn btn-primary w-100" type="submit">Create Account</button>
                                                    </div>
                                                    <div class="col-12">
                                                        <p class="small mb-0">Already have an account? <a href="pages-login.html">Log in</a></p>
                                                    </div>
                                                </form>
                                            </div>
                                        </div><!-- End Bordered Tabs Justified -->
                                    </div>
                                </div>


                            </div>
                        </div>

                        <div class="credits">
                            <!-- All the links in the footer should remain intact. -->
                            <!-- You can delete the links only if you purchased the pro version. -->
                            <!-- Licensing information: https://bootstrapmade.com/license/ -->
                            <!-- Purchase the pro version with working PHP/AJAX contact form: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/ -->
                            Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
                        </div>

                    </div>
                </div>
            </div>

        </section>

    </div>
</main><!-- End #main -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i class="bi bi-arrow-up-short"></i></a>

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
<script src="assets/js/hr.js"></script>
</body>

</html>