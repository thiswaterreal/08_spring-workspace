<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- 이쪽에 메뉴바 포함 할꺼임 -->
    <jsp:include page="../common/header.jsp"/>
    
    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>
			
			<!-- Controller에서 4번째 방법 쓸거기 때문에 반드시! name값 = vo필드명 동일하게! -->
            <form action="insert.me" method="post" id="enrollForm">
                <div class="form-group">
                    <label for="userId">* ID :</label>
                    <input type="text" class="form-control" id="userId" name="userId" placeholder="Please Enter ID" required>
                    <!-- 아이디 중복 확인 -->
                    <div id="checkResult" style="font-size:0.8em; display:none"></div>
                    
                    <br>
                    <label for="userPwd">* Password :</label>
                    <input type="password" class="form-control" id="userPwd" name="userPwd" placeholder="Please Enter Password" required><br>
                    
                    <label for="checkPwd">* Password Check :</label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required><br>
                    
                    <label for="userName">* Name :</label>
                    <input type="text" class="form-control" id="userName" name="userName" placeholder="Please Enter Name" required><br>
                    
                    <label for="email"> &nbsp; Email :</label>
                    <input type="email" class="form-control" id="email" name="email" placeholder="Please Enter Email"><br>
                    
                    <label for="age"> &nbsp; Age :</label>
                    <input type="number" class="form-control" id="age" name="age" placeholder="Please Enter Age"><br>
                    
                    <label for="phone"> &nbsp; Phone :</label>
                    <input type="tel" class="form-control" id="phone" name="phone" placeholder="Please Enter Phone (-없이)"><br>
                    
                    <label for="address"> &nbsp; Address :</label>
                    <input type="text" class="form-control" id="address" name="address" placeholder="Please Enter Address"><br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Male" value="M">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" name="gender" id="Female" value="F">
                    <label for="Female">여자</label><br>
                    
                </div>
                <br>
                <div class="btns" align="center">
                    <button id="enrollBtn" type="submit" class="btn btn-primary" disabled>회원가입</button>
                    <button type="reset" class="btn btn-danger">초기화</button>
                </div>
            </form>
        </div>

        <br><br>
    </div>

    <script>
		$(function(){
			// 아이디를 입력하는 input 요소객체 선택 => 변수에 담아두기
			const $idInput = $("#enrollForm input[name=userId]");
			
			$idInput.keyup(function(){ //해당 input에 글자 작성할 때마다 실행되는..
				//console.log($idInput.val());
				
				// 우선 최소 5글자 이상으로 입력되어있을때만 ajax 요청해서 중복체크 하도록
				if($idInput.val().length >= 5){
					
					$.ajax({
						url:"idCheck.me",
						data:{//키:벨류
							checkId:$idInput.val()
						},
						success:function(result){//controller에서 넘어온 값 아무 변수(result)로 받기 가넝
							
							if(result == "NNNNN"){//사용불가능
								// => 빨간색메세지 '사용불가능' 출력
								$("#checkResult").show();
								$("#checkResult").css("color", "red").text("중복된 아이디가 존재합니다. 다시 입력해주세요.");
								// => 버튼 비활성화
								$("#enrollForm :submit").attr("disabled", true);
							}else {//사용가능
								// => 초록색메세지 '사용가능' 출력
								$("#checkResult").show();
								$("#checkResult").css("color", "green").text("사용 가능한 아이디 입니다!");
								// 버튼 활성화
								$("#enrollForm :submit").removeAttr("disabled");
							}
							
						},
						error:function(){
							console.log("아이디 중복 체크용 ajax 통신 실패!")
						}
					})
					
				}else { //5글자 미만일 경우 => 버튼 비활성화, 메시지 숨기기
					$("#checkResult").hide();
					$("#enrollForm :submit").attr("disabled", true); // :submit == 타입이 submit인것
				}
			})
			
		})    
    </script>
        
        
    <!-- 이쪽에 푸터바 포함할꺼임 -->
    <jsp:include page="../common/footer.jsp"/>

</body>
</html>