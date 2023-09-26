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
	 
	
	 public Member() {}


	public Member(String userId, String userPwd, String userName, String email, String gender, String age, String phone,
			String address, Date enrollDate, Date modifyDate, String status) {
		super();
		this.userId = userId;
		this.userPwd = userPwd;
		this.userName = userName;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.phone = phone;
		this.address = address;
		this.enrollDate = enrollDate;
		this.modifyDate = modifyDate;
		this.status = status;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserPwd() {
		return userPwd;
	}


	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Date getEnrollDate() {
		return enrollDate;
	}


	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}


	public Date getModifyDate() {
		return modifyDate;
	}


	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Member [userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName + ", email=" + email
				+ ", gender=" + gender + ", age=" + age + ", phone=" + phone + ", address=" + address + ", enrollDate="
				+ enrollDate + ", modifyDate=" + modifyDate + ", status=" + status + "]";
	}
	 
	 
}
