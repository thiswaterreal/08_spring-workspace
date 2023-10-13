package com.kh.opendata.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class APIController {

	
	private static final String serviceKey = "iUlLG%2FJU5KCGVCcLjB3btCpl1o9jyXmLJvdH0hwu%2BtMyTqUDkU%2BEkPHYBStsuYoDx6Q3tBI%2BAKQUWmHSko4Yhg%3D%3D";
	
	// 1. json 형식으로 응답 받을 때! (produces확인)
	/*
	@ResponseBody
	@RequestMapping(value="air.do", produces = "application/json; charset=utf-8")
	public String airPollution(String location) throws IOException {	// (throws IOException) 앞에서 받아온 속성값과 변수명 동일
		
		// * 요청변수(Request Parameter)
		String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + serviceKey;
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8");
		url += "&returnType=json";
		url += "&numOfRows=50";
		
		// System.out.println(url);

		// 순서1,2,3,4
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText="";
		String line;
		
		while((line=br.readLine()) != null) {
			responseText += line;
		}
	
		br.close();
		urlConnection.disconnect();
	
		//System.out.println(responseText);
		// 응답뷰가 아닌 데이터 보내는 거니까 반환형 String, @ResponseBody 추가
		// json 방식이니까 produces = "application/json; charset=utf-8"
		return responseText;

	}
	*/
	
	
	
	// 2. xml 형식으로 응답 받을 때! (produces확인)
	@ResponseBody
	@RequestMapping(value="air.do", produces = "text/xml; charset=utf-8")
	public String airPollution(String location) throws IOException {	// (throws IOException) 앞에서 받아온 속성값과 변수명 동일
		
		// * 요청변수(Request Parameter)
		String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		url += "?serviceKey=" + serviceKey;
		url += "&sidoName=" + URLEncoder.encode(location, "UTF-8");
		url += "&returnType=xml";
		url += "&numOfRows=50";
		
		// System.out.println(url);

		
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText="";
		String line;
		
		while((line=br.readLine()) != null) {
			responseText += line;
		}
	
		br.close();
		urlConnection.disconnect();
	
		return responseText;
		
		
	}
	
	
	
	
	// 1. xml 형식 : 지진해일대피소
	@ResponseBody
	@RequestMapping(value="disaster.do", produces = "text/xml; charset=utf-8")
	public String disasterShelter() throws IOException {
		
		String url = "https://apis.data.go.kr/1741000/TsunamiShelter3/getTsunamiShelter1List";
		url += "?serviceKey=" + serviceKey;
		url += "&numOfRows=20";
		url += "&type=xml";
		
		//System.out.println(url);
		
		// 순서1,2,3,4
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText="";
		String line;
		
		while((line=br.readLine()) != null) {
			responseText += line;
		}
	
		br.close();
		urlConnection.disconnect();
	
		return responseText;
		
	}
	
	
	/*--------------------------실습 기기!!--------------------------*/
	// json
	@ResponseBody
	@RequestMapping(value="foodJson.do", produces = "application/json; charset=utf-8")
	public String foodNutrient1() throws IOException {
		
		String url = "https://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1";
		url += "?serviceKey=" + serviceKey;
		url += "&type=json";
		
		System.out.println(url);
		
	
		// 순서1,2,3,4
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText="";
		String line;
		
		while((line=br.readLine()) != null) {
			responseText += line;
		}
	
		br.close();
		urlConnection.disconnect();

		return responseText;
	
	}
	
	
	// xml
	@ResponseBody
	@RequestMapping(value="foodXml.do", produces = "text/xml; charset=utf-8")
	public String foodNutrient2() throws IOException {
		
		String url = "https://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1";
		url += "?serviceKey=" + serviceKey;
		url += "&type=xml";
		
		//System.out.println(url);
		
		// 순서1,2,3,4
		URL requestUrl = new URL(url);
		HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
		urlConnection.setRequestMethod("GET");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		String responseText="";
		String line;
		
		while((line=br.readLine()) != null) {
			responseText += line;
		}
	
		br.close();
		urlConnection.disconnect();
	
		return responseText;
		
	}
	
	
}
