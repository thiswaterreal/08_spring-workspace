package com.kh.ajax.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		response.getWriter().print(responseData);
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
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="text/html; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		
		String responseData = "응답문자열 : " + name + "은(는) " + age + "살 입니다.";
		return responseData;
	}
	
	
	
	
}
