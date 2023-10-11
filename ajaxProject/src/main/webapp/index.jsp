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
	
		// 1) id 부여해서 선택하는 방법
		/* 
		$("#btn").click(function(){
			어쩌구저쩌구
		})
		*/
		
		
		// 2) onclick 으로 선택하는 방법
		/* 		응답데이터 1개일 경우
		function text1(){
			$.ajax({
				url:"ajax1.do",
				data:{//키:벨류
					name:$("#name").val(),
					age:$("#age").val()
				},
				success:function(result){//controller에서 넘어온 값 아무변수명으로 받기 가넝(값이 이 매개변수로 꽂힘)
					console.log(result);
					$("#result1").text(result);
				},
				error:function(){
					console.log("ajax 통신 실패!")
				}
			})
		}
		*/
		
		function text1(){
			$.ajax({
				url:"ajax1.do",
				data:{//키:벨류
					name:$("#name").val(),
					age:$("#age").val()
				},
				success:function(result){//controller에서 넘어온 값 아무변수명으로 받기 가넝(값이 이 매개변수로 꽂힘)
					
					console.log(result);
					
					// * 첫번째 방법 : JSONArray
					//   응답데이터가 배열의 형태일 경우 => 인덱스 접근 가능!		변수명[인덱스번호];
					/*
					let value = "이름 : " + result[0] + "<br> 나이 : " + result[1];
					$("#result1").html(value);
					*/
					
					// * 두번째 방법 : JSONObject
					//	 응답데이터가 객체의 형태일 경우 => 속성에 접근 가능!		변수명.속성명;(==넘어온키값)
					let value = "이름 : " + result.name + "<br> 나이 : " + result.age;
					$("#result1").html(value);
					
				},
				error:function(){
					console.log("ajax 통신 실패!")
				}
			})
		}
		
	</script>
	
	
	
	
	
	<h3>2. 조회 요청 후 조회된 한 회원 객체를 응답 받아서 출력 해보기</h3>
	조회할 회원번호 : <input type="number" id="userNo">
	<button id="btn2">조회</button>
	
	<div id="result2"></div>
	
	<script>
		$(function(){
			$("#btn2").click(function(){
				$.ajax({
					url:"ajax2.do",
					data:{
						num:$("#userNo").val()
					},
					success:function(obj){
						
						console.log(obj);
						
						// 속성 접근하려면 .으로 접근!!
						let value = "<ul>"
									+ "<li>이름 : " + obj.userName + "</li>"
									+ "<li>아이디 : " + obj.userId + "</li>"
									+ "<li>나이 : " + obj.age + "</li>"
									+ "</ul>";
						
						$("#result2").html(value);
						
					},
					error:function(){
						console.log("ajax 통신 실패!")
					}
				})
			})
		})
	</script>
	
	
	
	
	
	<h3>3. 조회요청 후 조회된 회원리스트 응답받아서 출력해보기</h3>
	<button onclick="test3();">회원 전체 조회</button>
	<br><br>
	
	<table border="1" id="result3">
		<thead>
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>나이</th>
				<th>전화번호</th>
			</tr>
		</thead>
		<tbody>
			
		</tbody>
	</table>
	
	<script>
		function test3(){
			$.ajax({ //조회. 넘길값data 필요음슴
				url:"ajax3.do",
				success:function(list){
					
					console.log(list);
					//console.log(list[0]);
					//console.log(list[0].userName);	// object의 속성 접근 : '.'
					
					let value = "";
					for(let i in list){
						value += "<tr>"
								 + "<td>" + list[i].userId + "</td>"
								 + "<td>" + list[i].userName + "</td>"
								 + "<td>" + list[i].age + "</td>"
								 + "<td>" + list[i].phone + "</td>"
								 + "</tr>"
					}
					
					$("#result3 tbody").html(value);
					
				},
				arror:function(){
					console.log("ajax 통신 실패!")
				}
			})
		}
	</script>
	

</body>
</html>