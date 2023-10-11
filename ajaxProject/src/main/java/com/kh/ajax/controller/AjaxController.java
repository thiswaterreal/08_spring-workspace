package com.kh.ajax.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.kh.ajax.model.vo.Member;

@Controller
public class AjaxController {
	
	/*
	 * 1. HttpServletResponse 객체로 응답데이터 응답하기 (기존의 jsp, servlet 배울 때 했던 Stream 이용한 방식)
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException { // jsp키값과 변수명 동일하게
		System.out.println(name);
		System.out.println(age);
		
		// 요청 처리를 위해 서비스 호출
		
		// 요청 처리가 다 됐다는 가정하에 요청한 그 페이지에 응답할 데이터가 있을 경우 
		String responseData = "응답문자열 : " + name + "은(는) " + age + "살 입니다.";
		
		// 응답데이터 돌려주기
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(responseData);	// 예외발생 throws로 던지기
	}
	*/
	
	
	/*
	 * 2. 응답할 데이터로 문자열로 리턴
	 * 		=> HttpServletResponse 를 안써도 됨
	 * 		단, 문자열을 리턴하면 원래는 포워딩 방식이었음(머리,꼬리) => 응답뷰로 인식해서 해당 뷰 페이지를 찾고 있어서 에러남
	 * 		    따라서 내가 리턴하는 문자열이 응답뷰가 아니라 응답데이터야 라는것을 선언하는 구문 필요함
	 * 			어노테이션 @ResponseBody 를 붙여야 됨
	 * 			(+ 인코딩 처리도 해야 됨 : produces="text/html; charset=UTF-8")
	 */
	
	//	*****	응답데이터 1개일 경우
	/* 
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="text/html; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		
		String responseData = "응답문자열 : " + name + "은(는) " + age + "살 입니다.";
		return responseData;
	}
	*/
	
	// 	*****	응답데이터가 다수일 경우
	/*
	// --------------- 1번 방식으로 해봄(HttpServletResponse response) 
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		
		// 요청처리가 다 됐다는 가정하에 데이터 응답
		 
		
		
		//response.setContentType("text/html; charset=UTF-8");
		//response.getWriter().print(name);	
		//response.getWriter().print(age);
		// 연이어서 출력하는 데이터가 하나의 문자열로 연이어져 있어서 단점	
		
		
		// 따라서, JSON(JavaScript Object Notation) 형태로 담아서 응답
		// * JSONArray	=> [값, 값, ..]		=> 자바에서의 ArrayList와 유사  (list에 추가할때 add)  :인덱스 개념으로 값 뽑음
		// * JSONObject	=> {키:값, 키:값, ..}	=> 자바에서의 HashMap과 유사	   (map에 추가할때 put)
		// MavenRepository JSON을 pom.xml에 등록
		
		
		// * 첫번째 방법 : JSONArray로 담아서 응답
		//JSONArray jArr =  new JSONArray();	// []
		//jArr.add(name);	// ["차은우"]
		//jArr.add(age);	// ["차은우", 20]
		
		
		// * 두번째 방법 : JSONObject로 담아서 응답 (다만, 순서유지는 안됨. 막 담김)
		//				.put("키", 벨류);
		JSONObject jObj = new JSONObject();	// {}
		jObj.put("name", name);	// {name:'차은우'}
		jObj.put("age", age);	// {name:'차은우', age:20}
		
		response.setContentType("application/json; charset=UTF-8");		// json response일때 인코딩처리
		response.getWriter().print(jObj);
		
		
		
	}
	*/
	
	// --------------- 2번 방식으로 해봄 
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="application/json; charset=UTF-8")	// 여기서 인코딩처리
	public String ajaxMethod1(String name, int age) {
		
		JSONObject jObj = new JSONObject();	// {}
		jObj.put("name", name);	// {name:'차은우'}
		jObj.put("age", age);	// {name:'차은우', age:10}
		
		return jObj.toJSONString();	// 자료형을 JSON형에서 String형으로 바꿈	// "{name:'차은우', age:10}"
		
	}
	
	
	
	
	// --------------- 2. 조회 요청 후 조회된 한 회원 객체를 응답 받아서 출력 해보기 ------------------
	/*
	@ResponseBody
	@RequestMapping(value="ajax2.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod2(int num) {
		
		// Member m = mService.selectMember(num);
		// 요렇게 db 갔다 온 것 마냥 Member 객체 하나 맹글어서 테스트
		
		Member m = new Member("user01", "pass01", "차은우", 20, "01011112222");
		
		// JSON형태로 만들어서 응답
		JSONObject jObj = new JSONObject();		// {}
		jObj.put("userId", m.getUserId());		// {userId:'user01'}
		jObj.put("userName", m.getUserName());	// {userId:'user01', name:'차은우'}	
		jObj.put("age", m.getAge());			// {userId:'user01', name:'차은우', age:10}
		jObj.put("phone", m.getPhone());		// ..
		
		return jObj.toJSONString();
		
	}
	*/
	
	@ResponseBody
	@RequestMapping(value="ajax2.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod2(int num) {
		
		// Member m = mService.selectMember(num);
		// 요렇게 db 갔다 온 것 마냥 Member 객체 하나 맹글어서 테스트
		
		Member m = new Member("user01", "pass01", "차은우", 20, "01011112222");
		
		return new Gson().toJson(m);
		// 한방에 처리해주는 Gson
		// MavenRepository GSON을 pom.xml에 등록
		// 내부적으로 객체 몇개인지 판단하여 알아서 array 또는 object 형태로 만들어줌
		// object일 경우 '키:벨류' 형식으로 만들어지는데 이때 키값은 필드명과 동일하게 만들어줌
		// {userId: 'user01', userPwd: 'pass01', userName: '차은우', age: 20, phone: '01011112222'}
		
	}
	
	
	// --------------- 3. 조회요청 후 조회된 회원리스트 응답받아서 출력해보기 ------------------
	@ResponseBody
	@RequestMapping(value="ajax3.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod3() {
		
		// ArrayList<Member> list = mService.selectList();
		// 요렇게 db 갔다 온 것 마냥 list 하나 맹글어서 테스트
		
		ArrayList<Member> list = new ArrayList<Member>();	// []
		list.add(new Member("user01", "pass01", "차은우", 20, "01011112222"));	// [{차은우객체}]
		list.add(new Member("user02", "pass02", "손흥민", 30, "01033334444"));	// [{차은우객체}, {손흥민객체}]
		list.add(new Member("user03", "pass03", "이강인", 25, "01055556666"));	// [{차은우객체}, {손흥민객체}, {이강인객체}]
		
		return new Gson().toJson(list);
		
	}
	
	
}
