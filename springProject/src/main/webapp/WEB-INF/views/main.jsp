<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 헤더 -->
	<jsp:include page="common/header.jsp"/>
	
	
	<!-- 메인 화면 -->
	<div class="content">
		<br><br>
		<div class="innerOuter">
		
			<h4>게시글 TOP 5</h4>
			<br>
			
			<a href="list.bo" style="float:right;">더보기 (+) </a>
			<br><br>
			
			<table id="boardList" class="table table-hover" align="center">
                <thead>
                  <tr>
                    <th>글번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>조회수</th>
                    <th>작성일</th>
                    <th>첨부파일</th>
                  </tr>
                </thead>
                <tbody>
                	<!-- 현재 조회수가 가장 높은 상위 5개의 게시글 조회해서 뿌리기 (ajax) -->	
                </tbody>
            </table>
			
		</div>
	</div>
	
	<script>
		$(function(){
			topBoardList();	//함수호출
			
			//setInterval(topBoardList , 1000); //1초마다 함수 호출
			
			// => 이 방법으로는 동적(ajax)으로 만들어진 요소에 이벤트 부여 불가
			/*
			$("#boardList>tbody>tr").click(function(){
				// $(this) == 누른tr
				location.href = "detail.bo?bno=" + $(this).children().eq(0).text();
			})
			*/
			
			// => 동적(ajax)으로 만들어진 요소에 이벤트 부여 방법
			// C:\04_frontend-workspace\04_jQuery\12_이벤트.html => 방법3
			// $(상위요소).on(이벤트명, 진짜요소, 실행할함수)
			$(document).on("click", "#boardList>tbody>tr", function(){
				//console.log(클릭먹혔누?);
				location.href = "detail.bo?bno=" + $(this).children().eq(0).text();
			})
			
			
		})
		
		function topBoardList(){ //함수맹글기
			$.ajax({
				url:"topList.bo",
				success:function(data){
					console.log(data);
					
					let value = "";
					for(let i in data) {	//data가 array니까 i에 list 담겨 있것쥬
						value += "<tr>"
								+ "<td>" + data[i].boardNo + "</td>"
								+ "<td>" + data[i].boardTitle + "</td>"
								+ "<td>" + data[i].boardWriter + "</td>"
								+ "<td>" + data[i].count + "</td>"
								+ "<td>" + data[i].createDate + "</td>"
								+ "<td>";
								
						if(data[i].originName != null){ // 첨부파일이 존재할 경우
							value += "🖼";
						}
						
						value += "</td></tr>";
					}
					
					$("#boardList tbody").html(value);
					
					
					
				},
				error:function(){
					console.log("조회수 top5 게시글 조회용 ajax 통신 실패!");
				}
			})
		}
	</script>
	
	
	
	
	<!-- 푸터 -->
	<jsp:include page="common/footer.jsp"/>
</body>
</html>