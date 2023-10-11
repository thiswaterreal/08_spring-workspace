package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.spring.board.model.service.BoardServiceImpl;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;


@Controller
public class BoardController {

	@Autowired
	private BoardServiceImpl bService;
	
	
	// 메뉴바  클릭시 : /list.bo						(기본적으로 1번 페이지 요청) 
	// 페이징바 클릭시 : /list.bo?cpage=요청한페이지번호
	
	/*
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="cpage", defaultValue = "1") int currentPage, Model model) {
		
		//System.out.println(currentPage);
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);	// 10개버튼,게시글5개씩보이도록
		ArrayList<Board> list = bService.selectList(pi);
		
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		
		// 포워딩할 뷰 (/WEB-INF/views/	board/boardListView		.jsp)
		return "board/boardListView";
	}
	 */
	
	
	@RequestMapping("list.bo")
	public ModelAndView selectList(@RequestParam(value="cpage", defaultValue = "1") int currentPage, ModelAndView mv) {
		
		//System.out.println(currentPage);
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);	// 10개버튼,게시글5개씩보이도록
		ArrayList<Board> list = bService.selectList(pi);
		
		/*
		mv.addObject("pi", pi);
		mv.addObject("list", list);
		mv.setViewName("board/boardListView");
		*/
		
		// 위에 3줄 한줄로
		mv.addObject("pi", pi).addObject("list", list).setViewName("board/boardListView");
		
		// 포워딩할 뷰 (/WEB-INF/views/	board/boardListView		.jsp)
		return mv;
	}
	
	
	
	/** (단순jsp이동) 글작성 폼
	 * 
	 */
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		// /WEB-INF/views/    board/boardEnrollForm    .jsp 로 포워딩
		return "board/boardEnrollForm";
	}

	
	
	// ***** 만약 다중 첨부파일 하고싶다면?? *******************************************************
	// boardEnrollForm.jsp (45번째줄 여러개 복사)
	// 여러개의 input type="file" 요소에 다 동일한 키값(name)으로 부여 ex)upfile
	// MultipartFile[] upfile 로 받으면 됨 (0번 인덱스부터 차곡차곡 첨부파일 담겨있음)
	// public String insertBoard(MultipartFile[] upfile, ..) {
	//		upfile[0].. for문 돌려서
	// }
	
	
	/** 게시글 작성하기 (첨부파일 포함)
	 * 
	 */
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, Model model, HttpSession session) {
		
		//System.out.println(b);
		//System.out.println(upfile);	// 첨부파일을 선택했든 안했든 생성되는 객체임 (다만, filename에 원본명이 있냐, ""이냐)

		// 전달된 파일이 있을 경우 => 파일명 수정 작업 후 서버 업로드 => 원본명, 서버업로드된 경로를 b에 이어서 담기
		if(!upfile.getOriginalFilename().equals("")) {
			// 파일명 수정 작업 후 서버에 업로드 시키기("flower.png" => "2023100412153012345.png")
			/*
			String originName = upfile.getOriginalFilename();	// "flower.png"
			
			// "2023100412153012345.png"
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // "20231004154607" (년월일시분초)
			int ranNum = (int)(Math.random() * 90000 + 10000);	// 21318(5자리 랜덤값)
			String ext = originName.substring(originName.lastIndexOf(".")); // .png (확장자명)
		
			String changeName = currentTime + ranNum + ext;
			
			// 업로드 시키고자 하는 폴더의 물리적인 경로를 알아내기
			String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/"); // 안에 '/'
			
			// *** (서버에 업로드 하기 위한 작업)
			try {
				upfile.transferTo(new File(savePath + changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			*/
			
			String changeName = saveFile(upfile, session);
			
			// *** 원본명, 서버업로드된 경로를 Board b에 이어서 담기 (db를 위한 작업)
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);		// changeName : resources/uploadFiles/2023101106080716329.jpg
			
		}
		
		
		// 넘어온 첨부파일 있을 경우 b : 제목, 작성자, 내용, 파일원본명, 파일저장경로
		// 넘어온 첨부파일 없을 경우 b : 제목, 작성자, 내용
		
		int result = bService.insertBoard(b);
		
		if(result > 0) { // 성공 => 게시글 리스트 페이지(list.bo url재요청)
			session.setAttribute("alertMsg", "성공적으로 게시글이 등록되었습니다.");
			return "redirect:list.bo";
		}else { // 실패 => 에러페이지 포워딩
			model.addAttribute("errorMsg", "게시글 등록 실패!");
			return "common/errorPage";
		}
		
		
	}
	
	
	/** 현재 넘어온 첨부파일 그 자체를 서버의 폴더에 저장시키는 역할
	 * 
	 */
	public String saveFile(MultipartFile upfile, HttpSession session) {
		String originName = upfile.getOriginalFilename();	// "flower.png"
		
		// "2023100412153012345.png"
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()); // "20231004154607" (년월일시분초)
		int ranNum = (int)(Math.random() * 90000 + 10000);	// 21318(5자리 랜덤값)
		String ext = originName.substring(originName.lastIndexOf(".")); // .png (확장자명)
	
		String changeName = currentTime + ranNum + ext;
		
		// 업로드 시키고자 하는 폴더의 물리적인 경로를 알아내기
		String savePath = session.getServletContext().getRealPath("/resources/uploadFiles/"); // 안에 '/'
		
		// *** (서버에 업로드 하기 위한 작업)
		try {
			upfile.transferTo(new File(savePath + changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return changeName;
		
	}
	
	
	/** 게시글 상세페이지 조회 (+ 조회수 증가)
	 * 
	 */
	@RequestMapping("detail.bo")
	public String selectBoard(int bno, Model model, HttpSession session) {
		//(@RequestParam(value="bno") int boardNo, .. )
		//System.out.println("bno : " + bno);
		
		// bno에는 상세조회하고자 하는 해당 게시글 번호 담겨있음
		//int boardNo = Integer.parseInt(request.getParameter("bno")); <= X
		
		
		// 해당 게시글 조회수 증가 서비스 호출 결과 받기 (update 하고 옴. count +1)
		int result = bService.increaseCount(bno);
		
		
		// >> 성공적으로 조회수 증가
		//			>> boardDetailView.jsp 상에 필요한 데이터 조회 (게시글 상세정보 조회 서비스 호출)
		//			>> 조회된 데이터 담아서
		//			>> board/boardDetailView.jsp로 포워딩
		// >> 조회수 증가 실패
		//			>> 에러문구 담아서 에러페이지 포워딩
		if(result > 0) {
			Board b = bService.selectBoard(bno);
		
			//session.setAttribute("b", b);
			model.addAttribute("b", b);
			return "board/boardDetailView";
			//mv.addObject("b", b).setViewName("board/boardDetailView");
		}else {
			model.addAttribute("errorMsg", "상세조회 실패!");
			return "common/errorPage";
			//mv.addObject("errorMsg", "상세조회실패").setViewName("common/errorPage");
		}
			//return mv (위 반환형도 ModelAndView로 바꾸)

	}
	
	
	/** 게시글 상세페이지에서 삭제
	 * 
	 */
	@RequestMapping("delete.bo")
	public String deleteBoard(int bno, String filePath, Model model, HttpSession session) { // jsp에서 boardNo, changeName숨겨서 넘겼음
														  
		int result = bService.deleteBoard(bno);
		
		if(result > 0) { // 삭제 성공 
			// 첨부파일 있다면 파일까지 삭제 **
			if(!filePath.equals("")) {
				// filePath = "resources/uploadFiles/xxxx.jpg" | ""
				
				// resources안에 uploadFiles안에 xxxx.jpg 파일을 지워라
				new File(session.getServletContext().getRealPath(filePath)).delete();
			}
			
			// 게시판 리스트 페이지 list.bo url 재요청
			session.setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다.");
			return "redirect:list.bo";
		}else { // 삭제 실패 => 포워딩
			model.addAttribute("errorMsg", "게시글 삭제 실패!");
			return "common/errorPage";
		}
	}
	
	
	/** (단순jsp이동) 글수정 폼
	 * 
	 */
	@RequestMapping("updateForm.bo")
	public String updateForm(int bno, Model model) {
		
		//Board b = bService.selectBoard(bno);
		
		model.addAttribute("b", bService.selectBoard(bno));
		return "board/boardUpdateForm";
	}
	
	
	/** 게시글 수정하기 (첨부파일 포함)
	 *
	 */
	@RequestMapping("update.bo")
	public String updateBoard(@ModelAttribute Board b, MultipartFile reupfile, Model model, HttpSession session) { // 글 주렁주렁, 첨부파일
		
		// 새로 넘어온 첨부파일이 '있'을 경우
		if(!reupfile.getOriginalFilename().equals("")) {
			
			// 기존에 첨부파일이 있었을 경우 => 기존의 첨부파일 지우기
			if(b.getOriginName() != null) {
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
			
			// 새로 넘어온 첨부파일 서버 업로드 시키기 (위에 saveFile 만들어놓은거 호출 : 새로 이름 바꿔서 업로드해줌)
			String changeName = saveFile(reupfile, session);
			
			// b에 새로 넘어온 첨부파일에 대한 원본명, 저장경로 담기
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + changeName);
		}
		
		/*
		 * 	** 위에 한 작업 :
		 *	
		 *  b에 boardNo, boardTitle, boardContent 무조건 담겨 있음!! 
		 *
		 *	1. 기존 첨부 파일 X, 새로 첨부된 파일 X
		 *		=> originName : null		|	changeName : null
		 *
		 *	2. 기존 첨부 파일 O, 새로 첨부된 파일 X
		 *		=> originName : 기존첨파원본명	| 	changeName : 기존첨파저장경로
		 *
		 *	3. 기존 첨부 파일 X, 새로 첨부된 파일 O
		 *		=> 새로 첨부된 파일 서버에 업로드(saveFile 호출해서)
		 *		=> originName : 새로운첨파원본명	|	changeName : 새로운첨파저장경로
		 *
		 *	4. 기존 첨부 파일 O, 새로 첨부된 파일 O
		 *		=> 기존의 파일 삭제
		 *		=> 새로 첨부된 파일 서버에 업로드
		 *		=> originName : 새로운첨파원본명	|	changeName : 새로운첨파저장경로
		 *
		 */
		
		int result = bService.updateBoard(b);
		
		if(result > 0) { // 수정 성공 => 상세페이지(detail.bo) + bno=? 가지고 재요청
			session.setAttribute("alertMsg", "성공적으로 게시글이 수정되었습니다.");
			return "redirect:detail.bo?bno=" + b.getBoardNo();
		}else { // 수정 실패 => 포워딩
			model.addAttribute("errorMsg", "게시글 수정 실패!");
			return "common/errorPage";
		}
		
	}

	
	/** 댓글 리스트 전체 조회
	 * 
	 */
	@ResponseBody
	@RequestMapping(value="rlist.bo", produces="application/json; charset=utf-8")
	public String ajaxSelectReplyList(int bno) {
		
		ArrayList<Reply> rlist = bService.selectReplyList(bno);
		
		return new Gson().toJson(rlist);
		
	}
	
	
	/**
	 * 댓글 작성
	 */
	@ResponseBody
	@RequestMapping(value="rinsert.bo")
	public String ajaxInsertReply(Reply r) { // * 커맨드방식(키값 필드명과 동일시켜놔야함) r:3개 담겨있음
		int result = bService.insertReply(r);
		return result>0 ? "success" : "fail";
	}
	
	
	
}
