package com.kh.spring.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.service.MemberServiceImpl;
import com.kh.spring.member.model.vo.Member;

@Controller //    어노테이션(annotation) 붙여주기 = spring이 Controller관리하도록 bean 등록 (servlet-context.xml파일)
			// 1) Controller 타입의 어노테이션을 붙여주면 빈스캐닝을 통해 자동으로 빈 등록
public class MemberController {
	
	
	// 전역으로
	@Autowired // DI(Dependency Injection) 특징 : 알아서 생성해주고, 필요없으면 소멸시킴 (더이상 new 어쩌구 안씀)
	private MemberServiceImpl mService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	/*
	@RequestMapping(value="login.me") // 2) RequestMapping 타입의 어노테이션을 붙여줌으로써 HandlerMapping 등록
	public void loginMember() {
		
	}
	
	@RequestMapping(value="insert.me")
	public void insertMember() {
		
	}
	*/
	
	
	
	/* 방법1)
	 * 
	 * * 파라미터(요청시 전달값)를 받는 방법 (요청시 전달되는 값들 처리 방법)
	 * 
	 * 1. HttpServletRequest 를 이용해서 전달 받기 (기존의 jsp/servlet 방식)
	 * 	  해당 메소드의 매개변수로 HttpServletRequest 를 작성해두면
	 *    스프링컨테이너가 해당 메소드 호출시(실행시) 자동으로 해당 객체를 생성해서 인자로 주입해줌
	@RequestMapping("login.me")
	public String loginMember(HttpServletRequest request) {
		String userId = request.getParameter("id");
		String userPwd = request.getParameter("pwd");
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
		return "main";
	}
	*/
	
	
	
	/* 방법2)
	 * 
	 * 2. @RequestParam 어노테이션을 이용하는 방법
	 *    request.getParameter("키") : 벨류의 역할을 대신해주는 어노테이션
	 *    (장점 : defaultValue="" 을 지정해줄 수 있음)
	 *
	@RequestMapping("login.me")
	public String loginMember(@RequestParam(value="id", defaultValue="aaaa") String userId,
							  @RequestParam(value="pwd") String userPwd) {
		
		System.out.println("ID : " + userId);
		System.out.println("PWD : " + userPwd);
		
		return "main";
	}
	*/
	
	
	
	/* 방법3)
	 * 3. @RequestParam 어노테이션을 생략하는 방법
	 * 	  ** 단, 매개변수명 name값(요청시 전달값의 키값)과 동일하게 세팅해둬야 자동으로 값이 주입됨
	 * 		 즉, header.jsp 에서 넘긴 키값(name값 id,pwd) == 여기 밑에 매개변수명 id,pwd
	 *
	@RequestMapping("login.me")
	public String loginMember(String id, String pwd) {
		
		System.out.println("ID : " + id);
		System.out.println("PWD : " + pwd);
		
		Member m = new Member();
		m.setUserId(id);
		m.setUserPwd(pwd);
		
		// Service쪽 메소드에 m을 전달하며 조회
		
		return "main";
	}
	*/
	
	
	
	/* 방법4)
	 * 4. 커맨드 객체 방식
	 * 	  해당 메소드 매개변수로
	 * 	  요청시 전달값을 담고자하는 vo클래스 타입을 셋팅 후
	 * 	  요청시 전달값의 키값(name값)을 vo클래스에 담고자 하는 필드명으로 작성
	 * 
	 * 	  스프링컨테이너가 해당 객체를 기본생성자로 생성 후 setter 메소드 찾아서
	 * 	  요청시 전달값을 해당 필드에 담아주는 내부적인 원리
	 * 
	 * 	  ** 반드시 name속성값(키값)과 담고자하는 필드명 동일해야함!!
	 * 
	 * 	  (즉, 매개변수에 (Member m) 셋팅 후 
	 * 	  	   Member 필드명과 키값(name값)을 똑같이 지정해주면 알아서 뽑힘)
	 */
	
	/*
	 * * 요청 처리 후 응답페이지로 포워딩 또는 url 재요청, 응답데이터 담는 방법
	 * 
	 * 4_1. 스프링에서 제공하는 Model 객체를 사용하는 방법
	 * 	  포워딩할 view로 전달하고자 하는 데이터를 맵형식(key-value)으로 담을 수 있는 영역
	 * 	  Model 객체는 requestScope 이다.
	 *    단, setAttribute가 아닌 addAttribute 메소드 이용
	 * 
	@RequestMapping("login.me")
	public String loginMember(Member m, Model model, HttpSession session) {
	
		Member loginMember = mService.loginMember(m);
		
		if(loginMember == null) {
			
			// 로그인 실패 => 에러메시지 requestScope에 담아서, 에러페이지(/WEB-INF/views/common/errorPage.jsp)로 포워딩
			// 매개변수로 (Model model) 추가해주고,
			model.addAttribute("errorMsg", "로그인샐패");
			return "common/errorPage"; //  /WEB-INF/views/ + ? + .jsp    //servlet-context.xml 에서 확인 가능
									   //  (머리)			   + ? + (꼬리)
			
		}else {
			
			// 로그인 성공 => loginMember를 sessionScope에 담고, 메인페이지 url재요청
			// 매개변수로 (HttpSession session) 추가해주고,
			session.setAttribute("loginMember", loginMember);
			return "redirect:/";
			
		}
		
	}
	*/
	
	
	/*
	 * 4_2. 스프링에서 제공하는 ModelAndView 객체를 이용하는 방법
	 * 
	 *  Model은 데이터를 key-value 세트로 담을 수 있는 공간이라고 한다면
	 *  View는 응답뷰에 대한 정보를 담을 수 있는 공간
	 */
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
	
		/* 암호화 작업 전에 했던 과정
		Member loginMember = mService.loginMember(m);
		
		if(loginMember == null) {
			
			// 로그인 실패 => 에러메시지 requestScope에 담아서, 에러페이지(/WEB-INF/views/common/errorPage.jsp)로 포워딩
			// 매개변수로 (ModelAndView mv) 추가해주고,
			// return 대신 .setViewName
			mv.addObject("errorMsg", "로그인샐패");
			mv.setViewName("common/errorPage");
			
		}else {
			
			// 로그인 성공 => loginMember를 sessionScope에 담고, 메인페이지 url재요청
			// 매개변수로 (HttpSession session) 추가해주고,
			// return 대신 .setViewName
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
			
		}
		return mv; // 자료형:ModelAndView
		*/
		
		
		
		// * 암호화 작업 후에 해야되는 과정
		// Member m userId  필드	: 사용자가 입력한 아이디
		//			userPwd 필드	: 사용자가 입력한 비번(평문)
		Member loginMember = mService.loginMember(m);
		// loginMember : 오로지 아이디만을 가지고 조회된 회원
		// loginMember userPwd 필드 : db에 기록된 비번(암호문)
		
		if(loginMember != null && bcryptPasswordEncoder.matches(m.getUserPwd(), loginMember.getUserPwd())) {
			// 회원이고, 평문과 암호화문이 같은 경우 (true)
			// 로그인 성공
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
		}else {
			// 로그인 실패
			mv.addObject("errorMsg", "로그인샐패");
			mv.setViewName("common/errorPage");
		}
		
		return mv;
		
		
		
	}
	
	//-------------------------------------------------------------------
	
	
	/** 로그아웃
	 * @param session
	 * @return
	 */
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		session.invalidate();
		return "redirect:/"; 	// 메인으로.. 자료형:String
	}
	
	
	/** (단순jsp이동) 회원가입 폼
	 * @return
	 */
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		// WEB-INF/views/    member/memberEnrollForm    .jsp 로 포워딩
		return "member/memberEnrollForm";
	}
	
	
	/** 회원가입
	 * @param m
	 */
	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model, HttpSession session) {
		
		//System.out.println(m);
		// 1. 한글 깨짐 => 스프링에서 제공하는 인코딩 필터 등록 (web.xml에서등록)
		// 2. 나이를 입력하지 않았을 경우 "" 빈문자열이 넘어오는데 int형 필드에 담을 수 없어서 400 error 발생!!
		//	  => Member 클래스에 age 필드를 int형 --> String형으로 변경
		// 3. 비밀번호가 사용자가 입력한 있는 그대로의 평문으로 보임
		// 	  => Bcrypt 방식의 암호화를 통해서 암호문으로 변경
		//	     1) 스프링 시큐리티 모듈에서 제공하는(라이브러리 추가 pom.xml)
		//	     2) BcryptPassWordEncoder 라는 클래스를 빈으로 등록 (spring-security.xml 파일에)
		//		 3) web.xml에 spring-security.xml 파일을 pre-loading 할 수 있도록 작성
		//System.out.println("평문 : " + m.getUserPwd());
	
		// 암호화 작업 (암호문을 만들어내는 과정)
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd());
		//System.out.println("암호문 : " + encPwd);
		
		
		
		m.setUserPwd(encPwd); // Member 객체에 userPwd에 평문 => 암호문으로 변경
		
		int result = mService.insertMember(m);
		
		if(result > 0) { // 성공 => 메인페이지 url 재요청
			session.setAttribute("alertMsg", "성공적으로 회원가입 되었습니다.");
			return "redirect:/";
		}else { // 실패 => 에러페이지 포워딩 (머리)(꼬리)
			model.addAttribute("errorMsg", "회원가입 실패!");
			return "common/errorPage";
		}
		
		
	}
	
	
	/** (단순jsp이동) 마이페이지 폼
	 * @return
	 */
	@RequestMapping("myPage.me")
	public String myPage() {
		return "member/myPage";
	}
	
	
	/** 회원정보 수정
	 * @param m
	 */
	@RequestMapping("update.me")
	public void updateMember(Member m, Model model, HttpSession session) {
		
//		Member updateMem = mService.updateMember(m);
//		
//		if(updateMem != null) {
//			session.setAttribute("loginMember", updateMem);
//			session.setAttribute("alertMsg", "성공적으로 회원정보 수정되었습니다");
//		}else {
//			model.addAttribute("errorMsg", "회원정보 수정 실패!");
//			return "common/errorPage";
//		}
		
	}
	
	
}
