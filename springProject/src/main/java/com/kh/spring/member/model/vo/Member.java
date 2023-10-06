package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/*
 * * Lombok(롬복) <= mvnrepository에서 lombok 검색 1.18.12 버전 코드 복붙
 * 1. 라이브러리 다운 후 적용 (Maven pom.xml)
 * 2. 다운로드된 jar 찾아서 설치 (작업할 IDE 선택해서 설치)
 * 3. IDE 재실행
 */

@NoArgsConstructor	//기본생성자
@AllArgsConstructor	//매개변수생성자(전체)
@Setter				//setter메소드
@Getter				//getter메소드
@ToString			//toString메소드
public class Member {

	 private String userId;
	 private String userPwd;
	 private String userName;
	 private String email;
	 private String gender;
	 //private int age; 입력할때 String형이니까! 근데 String으로 써도 db에는 알아서 int로 잘 들어감
	 private String age;
	 private String phone;
	 private String address;
	 private Date enrollDate;
	 private Date modifyDate;
	 private String status;
	 
	
	 //private String uName; => userName
	 //단, 롬복을 쓸 때는 필드명 작성시 적어도 소문자 두글자 이상으로 시작할 것
	 
	
	 
	 
}
