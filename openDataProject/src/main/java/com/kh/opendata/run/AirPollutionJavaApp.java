package com.kh.opendata.run;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kh.opendata.model.vo.AirVO;


public class AirPollutionJavaApp {
   
   
   // 발급받은 인증키 정보 변수에 담아두기
   public static final String serviceKey = "iUlLG%2FJU5KCGVCcLjB3btCpl1o9jyXmLJvdH0hwu%2BtMyTqUDkU%2BEkPHYBStsuYoDx6Q3tBI%2BAKQUWmHSko4Yhg%3D%3D";
   
   
   
   public static void main(String[] args) throws IOException {
      
	   
      //OpenAPI 서버로 요청하고자 하는 URL 만들기
      String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
      url += "?serviceKey=" + serviceKey;
      url += "&sidoName=" + URLEncoder.encode("서울","UTF-8");	// 요청시 전달값 중 한글이 있을경우 encoding 해야됨
      url += "&returnType=json"; 								// xml(관상용) 또는 json 일 경우 여기서 바꿔줌
      
      //System.out.println(url);
      // HttpURLConnection openApi 요청절차
      
      // 1. 요청하고자 하는 url 전달하면서 java.net.url객체생성
      URL requestUrl = new URL(url);
      
      // 2. 1번과정으로 생성된 URL객체를 가지고 HttpURLConnection 객체 생성
      HttpURLConnection urlConnection = (HttpURLConnection)requestUrl.openConnection();
      
      // 3. 요청에 필요한 header 설정하기
      urlConnection.setRequestMethod("GET");
      
      // 4. 해당 OpenApi 서버로 요청보낸후 입력스트림을 통해서 응답 데이터 읽어들이기
      BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
      
      String responseText = "";
      String line;
      while((line = br.readLine()) != null) {
         //System.out.println(line);
    	 responseText += line;
      }
      
      //System.out.println(responseText);
      
      /*
       *	{
				"response":
					{
						"body":
							{
								"totalCount":40,
								"items":
									[
										{
											// 속성명 : 속성값
											"so2Grade":"1",
											"coFlag":null,
											"khaiValue":"75",
											"so2Value":"0.003",
											"coValue":"0.4",
											"pm10Flag":null,
											"o3Grade":"2",
											"pm10Value":"26",
											"khaiGrade":"2",
											"sidoName":"서울",
											"no2Flag":null,
											"no2Grade":"1",
											"o3Flag":null,
											"so2Flag":null,
											"dataTime":"2023-10-12 14:00",
											"coGrade":"1",
											"no2Value":"0.017",
											"stationName":"중구",
											"pm10Grade":"1",
											"o3Value":"0.060"
										},
       */
      
      
      // ** 열어봐서 {} => JsonObject / [] => JsonArray
      
      // JSONObject, JSONArray, JSONElement 이용해서 파싱 할 수 있음(gson 라이브러리) => 내가 원하는 데이터만을 추출할 수 있음
      // 각각의 item 정보를 => AirVO 객체에 담고 => ArrayList에 차곡차곡 쌓기
      JsonObject tatalObj = JsonParser.parseString(responseText).getAsJsonObject();
      JsonObject responseObj = tatalObj.getAsJsonObject("response");	// response 속성 접근	=> {} JsonObject
      JsonObject bodyObj = responseObj.getAsJsonObject("body");			// body 속성 접근		=> {} JsonObject
      
      int totalCount = bodyObj.get("totalCount").getAsInt();			// totalCount 속성 접근  	=> 40 int      
      JsonArray itemArr = bodyObj.getAsJsonArray("items");				// items 속성 접근			=> [] JsonArrau
      
      ArrayList<AirVO> list = new ArrayList<AirVO>();	// []    
      
      for(int i=0; i<itemArr.size(); i++) {
    	  
    	  JsonObject item = itemArr.get(i).getAsJsonObject();
    	  
    	  AirVO air = new AirVO();
    	  air.setStationName(item.get("stationName").getAsString());
    	  air.setDataTime(item.get("dataTime").getAsString());
    	  air.setKhaiValue(item.get("khaiValue").getAsString());
    	  air.setPm10Value(item.get("pm10Value").getAsString());
    	  air.setSo2Value(item.get("so2Value").getAsString());
    	  air.setCoValue(item.get("coValue").getAsString());
    	  air.setNo2Value(item.get("no2Value").getAsString());
    	  air.setO3Value(item.get("o3Value").getAsString());
    	  
    	  list.add(air);
      }
      
      //System.out.println(list);
      for(AirVO a : list) {
    	  System.out.println(a);    	  
      }
      
      
      
      // 5. 다 사용한 스트림 반납
      br.close();
      urlConnection.disconnect();
      
      
   }
}