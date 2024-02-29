package org.snubi.did.resolver;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class BlockChainTest {
	
	//private final String url = "http://localhost:7545";
	private final String url = "http://147.46.198.104:8501";
//	private final String adminAccount = "0x81bDb37140a623CDB12d78e7E6bFDd3F9F728C7c";
//	private final String adminPassword = "snubi1004";
	private final String adminAccount = "0xfBD2Ac1848F031aa067ebF67d906C00d602714B1";
	private final String adminPassword = "snubisnubi1004";
	
	@SuppressWarnings("unchecked")
	@Test
	 void accountCreateTest() throws Exception {	
		List<String> list = new ArrayList<>();
	    JSONObject jObj = new JSONObject();		
		HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        /*********************** personal_newAccount *************************/
        
    	list.add("private-password");
	    jObj.put("jsonrpc","2.0");
	    jObj.put("method","personal_newAccount");
	    jObj.put("params", list);
	    jObj.put("id","1");
	    HttpEntity<String> request = new HttpEntity<String> (jObj.toJSONString(), headers);
	    String apiResult = restTemplate.postForObject(url, request, String.class);
	    JSONParser parser = new JSONParser();
		Object obj = parser.parse( apiResult );
		JSONObject jObjApiResult = (JSONObject) obj;
		String account = (String)(jObjApiResult.get("result"));
		System.out.println("admin account "+  account);
		/*********************** admin unlock *************************/
		if(list != null) list.clear();
		list.add(adminAccount);
		list.add(adminPassword);
		jObj.put("jsonrpc","2.0");
	    jObj.put("method","personal_unlockAccount");
	    jObj.put("params", list);
	    jObj.put("id","2");
	    request = new HttpEntity<String> (jObj.toJSONString(), headers);
	    restTemplate.postForObject(url, request, String.class);		
	    System.out.println("unlock");
	    /*********************** personal unlock *************************/
 		if(list != null) list.clear();
 		list.add(account);
 		list.add("private-password");
 		jObj.put("jsonrpc","2.0");
 	    jObj.put("method","personal_unlockAccount");
 	    jObj.put("params", list);
 	    jObj.put("id","2");
 	    request = new HttpEntity<String> (jObj.toJSONString(), headers);
 	    restTemplate.postForObject(url, request, String.class);		
 	    System.out.println("admin unlock");
		/*********************** send ether *************************/
		JSONObject jObjSendEther = new JSONObject();
		//jObjSendEther.put("from", "0x8b0aea274462397135b6B1B14124f5b6278C0ABC");
		jObjSendEther.put("from", adminAccount);
		jObjSendEther.put("to", account);
		jObjSendEther.put("value", "0x4563918244f40000");
		List<JSONObject> objList = new ArrayList<>();
		objList.add(jObjSendEther);
		jObj.put("jsonrpc","2.0");
	    jObj.put("method","eth_sendTransaction");
	    jObj.put("params", objList);
	    jObj.put("id","2");
	    request = new HttpEntity<String> (jObj.toJSONString(), headers);
	    // REST API 호출
	    String apiResultSendEther = restTemplate.postForObject(url, request, String.class);
	    System.out.println("apiResultSendEther: " + apiResultSendEther);
	    
	}
	
	

}
