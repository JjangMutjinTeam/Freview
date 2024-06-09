<%@ page import="com.nuguna.freview.dto.cust.brand.CustMyBrandInfoDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    CustMyBrandInfoDto brandInfo = (CustMyBrandInfoDto) request.getAttribute("brandInfo");
%>


<!DOCTYPE html>
<html lang="en">

<head>
    <style>
      .tag-button {
        display: inline-block;
        margin: 5px;
        padding: 5px 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
        cursor: pointer;
      }

      .selected-tag {
        display: inline-block;
        margin: 5px;
        padding: 5px 10px;
        border: 1px solid #007bff;
        border-radius: 5px;
        background-color: #007bff;
        color: white;
      }
    </style>
    <style>
      .form-control {
        width: 100%;
        height: 38px;
        padding: 6px 12px;
        font-size: 14px;
        line-height: 1.42857143;
        color: #555;
        background-color: #fff;
        background-image: none;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
        transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
      }

      .form-control-readonly {
        background-color: #e9ecef;
        opacity: 1;
      }
    </style>

    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>Users / Profile - NiceAdmin Bootstrap Template</title>
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
    <link href="assets/css/style-h.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <!-- icon bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
          crossorigin="anonymous">

    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script>
      function toggleFoodType(foodType) {
        var input = document.getElementById('foodTypesInput');
        var currentValue = input.value.split(',').filter(Boolean);
        var index = currentValue.indexOf(foodType);

        if (index >= 0) {
          currentValue.splice(index, 1);
        } else {
          currentValue.push(foodType);
        }
        input.value = currentValue.join(',');
      }
    </script>
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
        <a href="index.html" class="logo d-flex align-items-center">
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

<!-- ======= Sidebar ======= -->
<aside id="sidebar" class="sidebar">
    <ul class="sidebar-nav" id="sidebar-nav">
        <li class="nav-item">
            <a class="nav-link" data-bs-target="#components-nav"
               href="#">
                <i class="bi bi-person"></i><span>브랜딩</span>
            </a>
        </li><!-- End Components Nav -->

        <li class="nav-item">
            <a class="nav-link collapsed" href="users-profile.html">
                <i class="bi bi-layout-text-window-reverse"></i>
                <span>활동</span>
            </a>
        </li><!-- End Profile Page Nav -->

        <li class="nav-item">
            <a class="nav-link collapsed" href="pages-faq.html">
                <i class="bi bi-envelope"></i>
                <span>알림</span>
            </a>
        </li><!-- End F.A.Q Page Nav -->
    </ul>
</aside><!-- End Sidebar-->

<main id="main" class="main">

    <div class="pagetitle">
        <h1>Profile</h1>
    </div><!-- End Page Title -->

    <section class="section profile">
        <div class="row">
            <div class="card">
                <!-- profile  -->
                <div class="card-body profile-card pt-4 d-flex flex-column align-items-center">
                    <img src="assets/img/basic/basic-profile-img.png" alt="Profile"
                         class="rounded-circle">
                    <h2 id="nickname-holder-section">${brandInfo.nickname}</h2>
                    <div class="social-links mt-2 ri-heart-3-fill">
                        ${brandInfo.zzimCount}
                    </div>
                </div>

                <div class="card-body pt-3">
                    <!-- Bordered Tabs -->
                    <div class="tab-content pt-2">

                        <div class="tab-pane fade show active profile-overview"
                             id="profile-overview">

                            <h5 class="card-title">Profile Details</h5>

                            <!-- 소개 -->
                            <div class="row">
                                <div class="col-lg-3 col-md-4 label">소개</div>
                                <div class="col-lg-8 col-md-6">
                                    <input id="introduce-input" type="text" name="to_nickname"
                                           value="<%= brandInfo.getIntroduce() %>"
                                           class="form-control" readonly>
                                </div>
                                <div class="col-lg-1 col-md-2">
                                    <button id="introduce-update-btn" type="button"
                                            class="btn btn-primary edit-btn">수정
                                    </button>
                                    <button id="introduce-submit-btn" type="button"
                                            class="btn btn-success send-btn"
                                            style="display: none;">전송
                                    </button>
                                    <button id="introduce-cancel-btn" type="button"
                                            class="btn btn-secondary cancel-btn"
                                            style="display: none;">취소
                                    </button>
                                </div>
                            </div>

                            <script>
                              $(document).ready(function () {
                                $("#introduce-cancel-btn").click(function () {
                                  $("#introduce-cancel-btn").hide();
                                  $("#introduce-submit-btn").hide();
                                  $("#introduce-update-btn").show();
                                  $('#introduce-input').prop('readonly', false);
                                });

                                $("#introduce-update-btn").click(function () {
                                  $("#introduce-update-btn").hide();
                                  $("#introduce-cancel-btn").show();
                                  $("#introduce-submit-btn").show();
                                  $('#introduce-input').prop('readonly', false);
                                });

                                $("#introduce-submit-btn").click(function () {
                                  var newIntroduce = $('#introduce-input').val();
                                  // Ajax 요청
                                  $.ajax({
                                    url: '<%=request.getContextPath()%>/api/my-brand/introduce',
                                    method: 'POST',
                                    data: JSON.stringify({
                                      'member_seq': ${member_seq},
                                      'to_introduce': newIntroduce
                                    }),
                                    success: function (response) {
                                      // 성공적으로 응답을 받았을 때 처리
                                      $('#introduce-input').val(response.item).prop('readonly',
                                          true);
                                      $("#introduce-submit-btn").hide();
                                      $("#introduce-cancel-btn").hide();
                                      $("#introduce-update-btn").show();
                                    },
                                    error: function (error) {
                                      // 실패 시 처리
                                      console.log(error);
                                      alert('소개글 변경에 실패하였습니다.');
                                    }
                                  });
                                });
                              });
                            </script>

                            <!-- 닉네임 보여주기/등록하기 -->
                            <div class="row">
                                <div class="col-lg-3 col-md-4 label">닉네임</div>
                                <div class="col-lg-8 col-md-6">
                                    <input id="nickname-input" type="text" name="to_nickname"
                                           value="<%= brandInfo.getNickname() %>"
                                           class="form-control" readonly>
                                </div>
                                <div class="col-lg-1 col-md-2">
                                    <button type="button" class="btn btn-primary edit-btn"
                                            id="nickname-update-btn">수정
                                    </button>
                                    <button type="button" class="btn btn-success send-btn"
                                            style="display: none;" id="nickname-submit-btn">전송
                                    </button>
                                    <button type="button" class="btn btn-secondary cancel-btn"
                                            style="display: none;" id="nickname-cancel-btn">취소
                                    </button>
                                </div>
                            </div>

                            <script>
                              $(document).ready(function () {
                                $("#nickname-cancel-btn").click(function () {
                                  $("#nickname-cancel-btn").hide();
                                  $("#nickname-submit-btn").hide();
                                  $("#nickname-update-btn").show();
                                  $('#nickname-input').prop('readonly', false);
                                });

                                $("#nickname-update-btn").click(function () {
                                  $("#nickname-update-btn").hide();
                                  $("#nickname-cancel-btn").show();
                                  $("#nickname-submit-btn").show();
                                  $('#nickname-input').prop('readonly', false);
                                });

                                $("#nickname-submit-btn").click(function () {
                                  var newNickname = $('#nickname-input').val();
                                  // Ajax 요청
                                  $.ajax({
                                    url: '<%=request.getContextPath()%>/api/my-brand/nickname',
                                    method: 'POST',
                                    data: JSON.stringify({
                                      'member_seq': ${member_seq},
                                      'to_nickname': newNickname
                                    }),
                                    success: function (response) {
                                      // 성공적으로 응답을 받았을 때 처리
                                      $('#nickname-input').val(response.item).prop('readonly',
                                          true);
                                      $("#nickname-holder-head").text(response.item);
                                      $("#nickname-holder-section").text(response.item);
                                      $("#nickname-submit-btn").hide();
                                      $("#nickname-cancel-btn").hide();
                                      $("#nickname-update-btn").show();
                                    },
                                    error: function (error) {
                                      // 실패 시 처리
                                      console.log(error);
                                      alert('닉네임 변경에 실패하였습니다.');
                                    }
                                  });
                                });
                              });
                            </script>

                            <!-- 연령대 보여주기/등록하기 -->
                            <div class="row">
                                <div class="col-lg-3 col-md-4 label">연령대</div>
                                <select id="age-group-input"
                                        class="form-control form-control-readonly col-lg-8 col-md-6"
                                        aria-label="Default select example" disabled>
                                    <%
                                        String[] ageGroups = {"10대", "20대", "30대", "40대", "50대",
                                                "60대", "70대", "80대", "90대"};
                                        String selectedAgeGroup = brandInfo.getAgeGroup();
                                    %>
                                    <%
                                        for (String ageGroup : ageGroups) {
                                    %>
                                    <option value="<%= ageGroup %>" <%=
                                    ageGroup.equals(selectedAgeGroup) ? "selected"
                                            : "" %>><%= ageGroup %>
                                    </option>
                                    <%
                                        }
                                    %>
                                </select>

                                <script>
                                  $(document).ready(function () {
                                    $("#age-group-cancel-btn").click(function () {
                                      $("#age-group-cancel-btn").hide();
                                      $("#age-group-submit-btn").hide();
                                      $("#age-group-update-btn").show();
                                      $('#age-group-input').prop('disabled', true);
                                    });

                                    $("#age-group-update-btn").click(function () {
                                      $("#age-group-update-btn").hide();
                                      $("#age-group-cancel-btn").show();
                                      $("#age-group-submit-btn").show();
                                      $('#age-group-input').prop('disabled', false);
                                    });

                                    $("#age-group-submit-btn").click(function () {
                                      var newAgeGroup = $('#age-group-input').val();
                                      // Ajax 요청
                                      $.ajax({
                                        url: '<%=request.getContextPath()%>/api/cust/my-brand/age-group',
                                        method: 'POST',
                                        data: JSON.stringify({
                                          'member_seq': ${member_seq},
                                          'to_age_group': newAgeGroup
                                        }),
                                        success: function (response) {
                                          // 성공적으로 응답을 받았을 때 처리
                                          $('#age-group-input').val(response.item).prop('disabled',
                                              true);
                                          $("#age-group-submit-btn").hide();
                                          $("#age-group-cancel-btn").hide();
                                          $("#age-group-update-btn").show();
                                        },
                                        error: function (error) {
                                          // 실패 시 처리
                                          console.log(error);
                                          alert('연령대 변경에 실패하였습니다.');
                                        }
                                      });
                                    });
                                  });
                                </script>

                                <div class="col-lg-1 col-md-2">
                                    <button id="age-group-update-btn" type="button"
                                            class="btn btn-primary edit-btn">수정
                                    </button>
                                    <button id="age-group-submit-btn"
                                            type="button" class="btn btn-success send-btn"
                                            style="display: none;">전송
                                    </button>
                                    <button id="age-group-cancel-btn"
                                            type="button" class="btn btn-secondary cancel-btn"
                                            style="display: none;">취소
                                    </button>
                                </div>
                            </div>

                            <script>
                              $(document).ready(function () {
                                $("#age-group-cancel-btn").click(function () {
                                  $("#age-group-cancel-btn").hide();
                                  $("#age-group-submit-btn").hide();
                                  $("#age-group-update-btn").show();
                                  $('#age-group-input').prop('readonly', false);
                                });

                                $("#age-group-update-btn").click(function () {
                                  $("#age-group-update-btn").hide();
                                  $("#age-group-cancel-btn").show();
                                  $("#age-group-submit-btn").show();
                                  $('#age-group-input').prop('readonly', false);
                                });

                                $("#age-group-submit-btn").click(function () {
                                  var newAgeGroup = $('#age-group-input').val();
                                  // Ajax 요청
                                  $.ajax({
                                    url: '<%=request.getContextPath()%>/api/cust/my-brand/age-group',
                                    method: 'POST',
                                    data: JSON.stringify({
                                      'member_seq': ${member_seq},
                                      'to_age_group': newAgeGroup
                                    }),
                                    success: function (response) {
                                      // 성공적으로 응답을 받았을 때 처리
                                      $('#age-group-input').val(response.item).prop('readonly',
                                          true);
                                      $("#age-group-submit-btn").hide();
                                      $("#age-group-cancel-btn").hide();
                                      $("#age-group-update-btn").show();
                                    },
                                    error: function (error) {
                                      // 실패 시 처리
                                      console.log(error);
                                      alert('닉네임 변경에 실패하였습니다.');
                                    }
                                  });
                                });
                              });
                            </script>

                            <!-- 활동분야 보여주기/등록하기 -->
                            <div class="row">
                                <div class="col-lg-3 col-md-4 label">활동 분야</div>
                                <div class="col-lg-8 col-md-6">
                                    <input type="hidden" name="to_food_types" id="foodTypesInput"
                                           class="form-control" readonly
                                           value="<%
                                       if (brandInfo.getFoodTypes() != null && !brandInfo.getFoodTypes().isEmpty()) {
                                           for (int i = 0; i < brandInfo.getFoodTypes().size(); i++) {
                                               out.print(brandInfo.getFoodTypes().get(i));
                                               if (i < brandInfo.getFoodTypes().size() - 1) {
                                                   out.print(",");
                                               }
                                           }
                                       }
                                   %>">
                                    <div id="foodTypesContainer" class="selected-tags">
                                        <%
                                            String[] defaultFoodTypes = {"한식", "양식", "중식", "일식",
                                                    "빵&베이커리", "기타"};
                                            for (String foodType : defaultFoodTypes) {
                                        %>
                                        <span class="selected-tag foodtype-button"
                                              onclick="toggleFoodType('<%= foodType %>')"><%= foodType %></span>
                                        <%
                                            }
                                        %>
                                    </div>
                                </div>
                                <div class="col-lg-1 col-md-2">
                                    <button type="button" class="btn btn-primary edit-btn">수정
                                    </button>
                                    <button type="button" class="btn btn-success send-btn"
                                            style="display: none;">전송
                                    </button>
                                    <button type="button" class="btn btn-secondary cancel-btn"
                                            style="display: none;">취소
                                    </button>
                                </div>
                            </div>

                            <script>
                              $(document).ready(function () {
                                $('.foodtype-button').click(function () {
                                  var foodType = $(this).text();
                                  var input = $('#foodTypesInput');
                                  var currentValue = input.val().split(',').filter(Boolean);
                                  var index = currentValue.indexOf(foodType);

                                  if (index >= 0) {
                                    currentValue.splice(index, 1);
                                  } else {
                                    currentValue.push(foodType);
                                  }
                                  input.val(currentValue.join(','));

                                  // 선택된 활동 분야 스타일 변경
                                  $(this).toggleClass('selected');
                                });
                              });
                            </script>

                            <style>
                              .selected-tag.foodtype-button {
                                display: inline-block;
                                margin: 5px;
                                padding: 5px 10px;
                                border: 1px solid #ff007f;
                                border-radius: 5px;
                                cursor: pointer;
                                background-color: #ff007f;
                                color: white;
                              }

                              .selected-tag.foodtype-button.selected {
                                background-color: #ff007f;
                                color: white;
                              }
                            </style>

                            <!-- 태그들 보여주기/등록하기 -->
                            <div class="row">
                                <div class="col-lg-3 col-md-4 label">태그</div>
                                <div class="col-lg-8 col-md-6">
                                    <input type="hidden" name="to_tags"
                                           value="${brandInfo.tagInfos}"
                                           class="form-control">
                                    <div class="selected-tags mb-3"></div>
                                    <div class="tag-buttons">
                                        <span class="tag-button" data-tag="초식">초식</span>
                                        <span class="tag-button" data-tag="육식">육식</span>
                                        <span class="tag-button" data-tag="빵빵이">빵빵이</span>
                                    </div>
                                </div>
                                <div class="col-lg-1 col-md-2">
                                    <button type="button" class="btn btn-primary edit-btn">수정
                                    </button>
                                    <button type="button" class="btn btn-success send-btn"
                                            style="display: none;">전송
                                    </button>
                                    <button type="button" class="btn btn-secondary cancel-btn"
                                            style="display: none;">취소
                                    </button>
                                </div>
                            </div>
                        </div>

                        <!-- 리뷰로그들 보여주기/ URL 등록하기 -->
                        <div class="row">
                            <div class="col-lg-3 col-md-4 label">리뷰 로그</div>
                            <div class="col-lg-8 col-md-6">
                                <input type="text" name="to_review_url"
                                       value="${brandInfo.reviewInfos}"
                                       class="form-control" readonly>
                            </div>
                            <div class="col-lg-1 col-md-2">
                                <button type="button" class="btn btn-primary edit-btn">수정
                                </button>
                                <button type="button" class="btn btn-success send-btn"
                                        style="display: none;">전송
                                </button>
                                <button type="button" class="btn btn-secondary cancel-btn"
                                        style="display: none;">취소
                                </button>
                            </div>
                        </div>
                        <div class="tab-pane fade profile-edit pt-3" id="profile-edit">
                        </div>
                    </div>
                </div>

</main><!-- End #main -->

<!-- ======= Footer ======= -->
<footer id="footer" class="footer">
    <div class="copyright">
        &copy; Copyright <strong><span>JjangMutjinTeam</span></strong>. All Rights Reserved
    </div>
    <div class="credits">
        Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
    </div>
</footer><!-- End Footer -->

<a href="#" class="back-to-top d-flex align-items-center justify-content-center"><i
        class="bi bi-arrow-up-short"></i></a>
<!-- jquery  -->
<script src="https://code.jquery.com/jquery-3.7.1.js"
        integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
        crossorigin="anonymous"></script>
<script>
  $(document).ready(function () {
    $('.edit-btn').click(function () {
      $(this).hide();
      $(this).siblings('.send-btn, .cancel-btn').show();
      var row = $(this).closest('.row');
      row.find('input').prop('readonly', false);
      if (row.find('.tag-buttons').length > 0) {
        row.find('.tag-buttons').show();
        row.find('input[name="to_tags"]').hide();
      }
    });

    $('.cancel-btn').click(function () {
      $(this).hide();
      $(this).siblings('.send-btn').hide();
      $(this).siblings('.edit-btn').show();
      var row = $(this).closest('.row');
      row.find('input').prop('readonly', true);
      if (row.find('.tag-buttons').length > 0) {
        row.find('.tag-buttons').hide();
        row.find('input[name="to_tags"]').show();
      }
    });

    $('.send-btn').click(function () {
      var row = $(this).closest('.row');
      var inputField = row.find('input');
      var fieldName = inputField.attr('name');
      var fieldValue = inputField.val();
      var formData = {};
      formData[fieldName] = fieldValue;

      if (fieldName === "to_tags") {
        var selectedTags = [];
        row.find('.selected-tag').each(function () {
          selectedTags.push($(this).data('tag'));
        });
        formData[fieldName] = selectedTags.join(', ');
      }

      $.ajax({
        url: 'BrandInfoServlet',
        method: 'POST',
        data: formData,
        success: function (response) {
          alert('성공적으로 수정되었습니다.');
          inputField.prop('readonly', true);
          $(this).hide();
          $(this).siblings('.cancel-btn').hide();
          $(this).siblings('.edit-btn').show();
          if (fieldName === "to_tags") {
            row.find('.tag-buttons').hide();
            row.find('input[name="to_tags"]').show();
          }
        }
      });
    });

    $('.tag-button').click(function () {
      var tag = $(this).data('tag');
      $('<span class="selected-tag" data-tag="' + tag + '">' + tag + '</span>').appendTo(
          '.selected-tags');
      $(this).hide();
    });

    $(document).on('click', '.selected-tag', function () {
      var tag = $(this).data('tag');
      $('.tag-button[data-tag="' + tag + '"]').show();
      $(this).remove();
    });
  });
</script>

<!-- icon bootstrap -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

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