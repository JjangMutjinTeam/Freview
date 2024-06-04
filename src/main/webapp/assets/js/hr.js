$(function(){


  $("#COMM_register_IDcheckBTN").on("click",function(){ //COMM_register 아이디체크


    let inputID = $("#Input_ID").val();

    $.ajax({
      method: "post",
      url : "/auth?pagecode=checkID",
      data : {"id" : inputID},
      error: function(myval){console.log("에러"+myval)},
      success: function(myval){
        console.log("성공"+myval);

        if (myval === 1) {
          $("#COMM_register_IDdeny").removeClass("remove");
          $("#Input_ID").val("");
        } else {
          $("#COMM_register_IDavail").removeClass("remove");
        }

      }
    })

  });

$("#Input_pw").change(function(){ // COMM_register 비밀번호 형식 확인

  let reg = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,25}$/
  let val = $("#Input_pw").val();



  if(reg.test(val)){
    $("#COMM_register_pwvali").removeClass("remove");
    $("#COMM_register_pwdeny").addClass("remove");
  }else if(val===""){
    $("#COMM_register_pwdeny").addClass("remove");
    $("#COMM_register_pwvali").addClass("remove");
  }
  else{
    $("#COMM_register_pwdeny").removeClass("remove");
    $("#Input_pw").val("");
    $("#COMM_register_pwvali").addClass("remove");
  }

})

 $("#Input_PasswordCheck") .change(function(){ // COMM_register 비밀번호 확인하기

   let pw = $("#Input_pw").val();
   let pwCheck = $("#Input_PasswordCheck").val();

   if(pw===pwCheck){
     $("#COMM_register_pwCheckvali").removeClass("remove");
     $("#COMM_register_pwCheckdeny").addClass("remove");
   }else if(pwCheck===""&&pw===""){
     $("#COMM_register_pwCheckdeny").addClass("remove");
     $("#COMM_register_pwCheckvali").addClass("remove");
   }
   else{
     $("#COMM_register_pwCheckdeny").removeClass("remove");
     $("#Input_PasswordCheck").val("");
     $("#COMM_register_pwCheckvali").addClass("remove");
   }

 })

  $("#COMM_register_emailBtn").click(function(){ // 이메일 인증번호 확인하기
    $("#COMM_register_input_emailNumber").attr('disabled',false);
    $("#COMM_register_emailNumberBtnBtn").attr('disabled',false);

  })


})