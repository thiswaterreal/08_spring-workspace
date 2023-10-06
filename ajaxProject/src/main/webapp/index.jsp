<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- jQuery 라이브러리(ajax포함) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

</head>
<body>

	<h1>Spring에서의 AJAX 사용방법</h1>
	
	<h3>1. 요청시 값 전달, 응답 결과 받아보기</h3>
	이름 : <input type="text" id="name"><br>
	나이 : <input type="number" id="age"><br>
	
	<!-- 
		1) id 부여해서 선택하는 방법
		<button id="btn">전송</button>
	 -->
	<button onclick="text1();">전송</button>
	<div id="result1"></div>
	 
	<script>
		/* 
		1) id 부여해서 선택하는 방법
		$("#btn").click(function(){
			어쩌구저쩌구
		})
		*/
		// 2) onclick 으로 선택하는 방법
		function text1(){
			$.ajax({
				url:"ajax1.do",
				data:{//키:벨류
					name:$("#name").val(),
					age:$("#age").val()
				},
				success:function(result){//controller에서 넘어온 값 아무변수명으로 받기 가넝
					console.log(result);
					$("#result1").text(result);
				},
				error:function(){
					console.log("ajax 통신 실패!")
				}
			})
		}
		
	</script>

</body>
</html>