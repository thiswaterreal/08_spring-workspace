<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- jQuery 라이브러리(ajax포함) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

</head>
<body>

	<h2>실시간 대기오염 정보</h2>
	
	지역 :
	<select id="location">
		<option>서울</option>
		<option>부산</option>
		<option>대전</option>
		<option>전북</option>
		<option>제주</option>
	</select>
	<button id="btn1">해당 지역 대기오염정보</button>
	
	<table id="result1" border="1">
		<thead>
			<tr>
				<th>측정소명</th>
				<th>층정일시</th>
				<th>통합대기환경수치</th>
				<th>미세먼지농도</th>
				<th>아황산가스농도</th>
				<th>일산화탄소농도</th>
				<th>이산화질소농도</th>
				<th>오존농도</th>
			</tr>
		</thead>
		<tbody>
		
		</tbody>
	</table>
	
	
	
	<script>
		$(function(){
			$("#btn1").click(function(){
				
				// 1. json 형식으로 응답 받을 때!
				/*
				$.ajax({
					url:"air.do",
					data:{location:$("#location").val()},	// 서울, 부산, 대전
					success:function(data){
						//console.log(data);
						//console.log(data.response.body.items);
					
						const itemArr = data.response.body.items;
						
						let value = ""
						for(let i in itemArr) {
							//console.log(itemArr[i]);
							let item = itemArr[i]; // 배열[인덱스0~9] = 객체{} = item
							
							value += "<tr>"
										+ "<td>" + item.stationName + "</td>"
										+ "<td>" + item.dataTime + "</td>"
										+ "<td>" + item.khaiValue + "</td>"
										+ "<td>" + item.pm10Value + "</td>"
										+ "<td>" + item.so2Value + "</td>"
										+ "<td>" + item.coValue + "</td>"
										+ "<td>" + item.no2Value + "</td>"
										+ "<td>" + item.o3Value + "</td>"
									+ "</tr>"
						}
						
						$("#result1 tbody").html(value);
						
					},
					error:function(){
						console.log("ajax 통신 실패!!");
					}
				})
				*/
				
				
				// 2. xml 형식으로 응답 받을 때!
				$.ajax({
					url:"air.do",
					data:{location:$("#location").val()},
					success:function(data){
						console.log(data);
						// jQuery 에서의 .find() 메소드 : 기준이 되는 요소의 하위 요소들 중 특정 요소를 찾을 때 사용 (html, xml)
						// console.log($(data).find("item"));	// find 메소드는 제이쿼리 메소드임 따라서 $()로 선택!!
						
						// xml 형식의 응답데이터를 받았을 때
						// 1. 응답데이터 안에 실제 데이터가 담겨있는 요소 선택
						let itemArr = $(data).find("item");
						
						// 2. 반복문 .each(function(i, item){})을 통해 실제 데이터가 담긴 요소들에 접근해서 동적으로 요소 만들기
						let value = "";
						itemArr.each(function(i, item){
							//console.log(item);
							//console.log($(item).find("stationName").text()); // <stationName>강남구</stationName>
						
							value += "<tr>"
										+ "<td>" + $(item).find("stationName").text() + "</td>"
										+ "<td>" + $(item).find("dataTime").text() + "</td>"
										+ "<td>" + $(item).find("khaiValue").text() + "</td>"
										+ "<td>" + $(item).find("pm10Value").text() + "</td>"
										+ "<td>" + $(item).find("so2Value").text() + "</td>"
										+ "<td>" + $(item).find("coValue").text() + "</td>"
										+ "<td>" + $(item).find("no2Value").text() + "</td>"
										+ "<td>" + $(item).find("o3Value").text() + "</td>"
									+ "</tr>"

						})
						
						// 3. 동적으로 만들어낸 요소를 화면에 출력
						$("#result1 tbody").html(value);	
						
					},
					error:function(){
						console.log("ajax 통신 실패!!");
					}
				
				})
				

				
			})
		})
	</script>
	
	
	<hr>
	
	
	<h2>지진해일대피소 정보</h2>
	<!-- 테이블 미리 안만들고 버튼 누르면 짠! 생기도록 하는 방법 -->
	
	<input type="button" value="실행" id="btn2">
	
	<div id="result2">
		
	</div>
	
	<script>
		$(function(){
			
			// xml형식
			/* 
			$("#btn2").click(function(){
				
				$.ajax({
					url:"disaster.do",
					success:function(data){
						//console.log(data);
						//console.log($(data).find("row"));
						
						let $table = $("<table border='1'></table>");
						
						let $thead = $("<thead></thead>");
						let headTr = "<tr>"
										+ "<th>일련번호</th>"
										+ "<th>시도명</th>"
										+ "<th>시군구명</th>"
										+ "<th>대피장소명</th>"
										+ "<th>주소</th>"
										+ "<th>수용가능인원(명)</th>"
										+ "<th>해변으로부터거리</th>"
										+ "<th>대피소분류명</th>"
									+ "</tr>";
									
						$thead.html(headTr);
						
						let $tbody = $("<tbody></tbody>");
						let bodyTr = "";
						$(data).find("row").each(function(i, row){
							//console.log(row);
							//console.log($(row).find("shel_nm").text());
						
							bodyTr += "<tr>"
										+ "<td>" + $(row).find("id").text() + "</td>"
										+ "<td>" + $(row).find("sido_name").text() + "</td>"
										+ "<td>" + $(row).find("sigungu_name").text() + "</td>"
										+ "<td>" + $(row).find("shel_nm").text() + "</td>"
										+ "<td>" + $(row).find("address").text() + "</td>"
										+ "<td>" + $(row).find("shel_av").text() + "</td>"
										+ "<td>" + $(row).find("lenth").text() + "</td>"
										+ "<td>" + $(row).find("shel_div_type").text() + "</td>"
									+ "</tr>"
						})
						
						$tbody.html(bodyTr);
						
						// 두개(thead, tbody) 결합하기
						// append	: 부모에.append(자식을) 붙이기
						// appendTo	: 자식을.appendTo(부모에) 붙이기
						//$table.append($thead, $tbody);
						//$table.appendTo("#result2");
						
						$table.append($thead, $tbody).appendTo("#result2");
						
					},
					error:function(){
						console.log("ajax 통신 실패!!");
					}
				})
				
			})
			*/
			
			
			/*
				** 화살표 함수 **
				익명함수들을 화살표 함수로 작성할 수 있음
				
				'function(){}'				를	'()=>{}' 방식으로 작성 가능
				'function(data){}'			를	'data=>{}' 방식으로 작성 가능
				'function(a, b){}'			를	'(a, b)=>{}' 방식으로 작성 가능
				'function(){return 10;}'	를	'()=>{10}' 방식으로 작성 가능
			*/
			
			// ** 위 내용을 화살포 함수로 바꿔보자
			$("#btn2").click(()=>{
				
				$.ajax({
					url:"disaster.do",
					success:data=>{
						
						let $table = $("<table border='1'></table>");
						
						let $thead = $("<thead></thead>");
						let headTr = "<tr>"
										+ "<th>일련번호</th>"
										+ "<th>시도명</th>"
										+ "<th>시군구명</th>"
										+ "<th>대피장소명</th>"
										+ "<th>주소</th>"
										+ "<th>수용가능인원(명)</th>"
										+ "<th>해변으로부터거리</th>"
										+ "<th>대피소분류명</th>"
									+ "</tr>";
						
						// thead 안에 요놈들 넣고,
						$thead.html(headTr);
						
						let $tbody = $("<tbody></tbody>");
						let bodyTr = "";
						$(data).find("row").each((i, row)=>{
							
							//console.log(row);
							//console.log($(row).find("shel_nm").text());
							
							bodyTr += "<tr>"
								+ "<td>" + $(row).find("id").text() + "</td>"
								+ "<td>" + $(row).find("sido_name").text() + "</td>"
								+ "<td>" + $(row).find("sigungu_name").text() + "</td>"
								+ "<td>" + $(row).find("shel_nm").text() + "</td>"
								+ "<td>" + $(row).find("address").text() + "</td>"
								+ "<td>" + $(row).find("shel_av").text() + "</td>"
								+ "<td>" + $(row).find("lenth").text() + "</td>"
								+ "<td>" + $(row).find("shel_div_type").text() + "</td>"
							+ "</tr>"
							
						})
						
						// tbody 안에 요놈들 넣고,
						$tbody.html(bodyTr);
						
						// thead 와 tbody 결합하기
						$table.append($thead, $tbody).appendTo("#result2");
						
					},
					error:()=>{
						console.log("ajax 통신 실패!!");
					}
					
				})
				
			})
			
			
			
			
		})
	</script>
	
	<hr>
	
	<!-- ------------------- 실습 기기!! ------------------- -->
	<!-- 
		종류 1개 json, xml 다 되는거 골라서 (데이터 6~7개)

		1. json 버튼 => 테이블 있는 그대로에 넣기
		2. xml 버튼 => 테이블 동적으로 그리기
		
		단, 모두 화살표 함수로 작성
	 -->
	 
	<h2>식품 영양 성분</h2>
	<button id="btn3">json</button>
	<button id="btn4">xml</button>
	
	<table id="result3" border="1">
		<thead>
			<tr>
				<th>식품이름</th>
				<th>1회 제공량(g)</th>
				<th>열량(kcal)</th>
				<th>탄수화물(g)</th>
				<th>단백질(g)</th>
				<th>지방(g)</th>
				<th>당류(g)</th>
				<th>가공업체</th>
			</tr>
		</thead>
		<tbody>
			<!-- 여기로 쏙! -->
		</tbody>
	</table>
	
	<br><br><br><br>
	
	<div id="result4">
		<!-- 여기로 쏙! -->
	</div>
	
	<script>
	$(function(){
		
		// * json
		$("#btn3").click(()=>{
			
			// 1. json 형식
			$.ajax({
				url:"foodJson.do",
				success:data=>{
					
					console.log(data);
					//console.log(data.response.body.items);
								
					const itemArr = data.body.items;
					
					let value = "";
					for(let i in itemArr) {
						//console.log(itemArr[i]);
						let item = itemArr[i]; // 배열[인덱스0~9] = 객체{} = item
						
						value += "<tr>"
									+ "<td>" + item.DESC_KOR + "</td>"
									+ "<td>" + item.SERVING_WT + "</td>"
									+ "<td>" + item.NUTR_CONT1 + "</td>"
									+ "<td>" + item.NUTR_CONT2 + "</td>"
									+ "<td>" + item.NUTR_CONT3 + "</td>"
									+ "<td>" + item.NUTR_CONT4 + "</td>"
									+ "<td>" + item.NUTR_CONT5 + "</td>"
									+ "<td>" + item.ANIMAL_PLANT + "</td>"
								+ "</tr>"
					}
					
					$("#result3 tbody").html(value);
					
				},
				error:()=>{
					console.log("ajax 통신 실패!!");
				}
				
			})

		})
		
		
		// * xml
		$("#btn4").click(()=>{
					
					$.ajax({
						url:"foodXml.do",
						success:data=>{
							//console.log(data);
							console.log($(data).find("item"));
							
							let $table = $("<table border='1'></table>");
							
							let $thead = $("<thead></thead>");
							let headTr = "<tr>"
											+ "<th>식품이름</th>"
											+ "<th>1회 제공량(g)</th>"
											+ "<th>열량(kcal)</th>"
											+ "<th>탄수화물(g)</th>"
											+ "<th>단백질(g)</th>"
											+ "<th>지방(g)</th>"
											+ "<th>당류(g)</th>"
											+ "<th>가공업체</th>"
										+ "</tr>";
							
							// thead 안에 요놈들 넣고,
							$thead.html(headTr);
							
							let $tbody = $("<tbody></tbody>");
							let bodyTr = "";
							$(data).find("item").each((i, item)=>{
								
								bodyTr += "<tr>"
									+ "<td>" + $(item).find("DESC_KOR").text() + "</td>"
									+ "<td>" + $(item).find("SERVING_WT").text() + "</td>"
									+ "<td>" + $(item).find("NUTR_CONT1").text() + "</td>"
									+ "<td>" + $(item).find("NUTR_CONT2").text() + "</td>"
									+ "<td>" + $(item).find("NUTR_CONT3").text() + "</td>"
									+ "<td>" + $(item).find("NUTR_CONT4").text() + "</td>"
									+ "<td>" + $(item).find("NUTR_CONT5").text() + "</td>"
									+ "<td>" + $(item).find("ANIMAL_PLANT").text() + "</td>"
								+ "</tr>"
								
							})
							
							// tbody 안에 요놈들 넣고,
							$tbody.html(bodyTr);
							
							// thead 와 tbody 결합하기
							$table.append($thead, $tbody).appendTo("#result4");
							
						},
						error:()=>{
							console.log("ajax 통신 실패!!");
						}
						
					})
					
				})
		
		
		
		
		
	})
			
	</script>
	
	
</body>
</html>