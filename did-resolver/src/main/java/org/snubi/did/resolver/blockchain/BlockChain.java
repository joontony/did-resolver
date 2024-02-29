package org.snubi.did.resolver.blockchain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.snubi.did.resolver.config.CustomConfig;
import org.snubi.did.resolver.dto.BrainUserDto;
import org.snubi.did.resolver.entity.ChainNode;
import org.snubi.did.resolver.repository.ChainNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockChain {
	

//	
//	@SuppressWarnings("unchecked")
//	public static String CreateAccount(BrainUserDto brainUserDto) {
//		
//		
//		
//		
//		// 여기서 블록체인 연결 
//    	String account = "";
//    	List<String> list = new ArrayList<>();
// 	    JSONObject jObj = new JSONObject();		
// 		HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        RestTemplate restTemplate = new RestTemplate();
//        /*********************** personal_newAccount *************************/
//        
//        if("avchain".equals(brainUserDto.getOrganization())) {
//        	log.info("Web : brainUserDto.getAccountPwd() "+  brainUserDto.getAccountPwd());      	
//            list.add(brainUserDto.getAccountPwd());  
//        }else {
//        	log.info("DNet : brainUserDto.getUserId() "+  brainUserDto.getUserId());      	
//            list.add(brainUserDto.getUserId());  
//        }
//        
//        
//        
//        
// 	    jObj.put("jsonrpc","2.0");
// 	    jObj.put("method","personal_newAccount");
// 	    jObj.put("params", list);
// 	    jObj.put("id","1");
// 	    HttpEntity<String> request = new HttpEntity<String> (jObj.toJSONString(), headers);
// 	    String apiResult = restTemplate.postForObject(CustomConfig.strBlockChainServerIp+":"+CustomConfig.strBlockChainServerPort, request, String.class);
// 	    JSONParser parser = new JSONParser();
// 		Object obj;
//		try {
//			obj = parser.parse( apiResult );
//			JSONObject jObjApiResult = (JSONObject) obj;
//	 		account = (String)(jObjApiResult.get("result"));
//	 		log.info("account "+  account);
//	 		/*********************** personal unlock *************************/
//	 		if(list != null) list.clear();
//	 		list.add(CustomConfig.strBlockChainServerAccount);
//	 		list.add(CustomConfig.strBlockChainServerPrivateKey);
//	 		jObj.put("jsonrpc","2.0");
//	 	    jObj.put("method","personal_unlockAccount");
//	 	    jObj.put("params", list);
//	 	    jObj.put("id","2");
//	 	    request = new HttpEntity<String> (jObj.toJSONString(), headers);
//	 	    restTemplate.postForObject(CustomConfig.strBlockChainServerIp+":"+CustomConfig.strBlockChainServerPort, request, String.class);		
//	 	    log.info("personal unlock");
//	 	   /*********************** admin unlock *************************/
//	 		if(list != null) list.clear();
//	 		list.add(account);
//	 		list.add(brainUserDto.getAccountPwd());
//	 		jObj.put("jsonrpc","2.0");
//	 	    jObj.put("method","personal_unlockAccount");
//	 	    jObj.put("params", list);
//	 	    jObj.put("id","2");
//	 	    request = new HttpEntity<String> (jObj.toJSONString(), headers);
//	 	    restTemplate.postForObject(CustomConfig.strBlockChainServerIp+":"+CustomConfig.strBlockChainServerPort, request, String.class);		
//	 	    log.info("admin unlock");
//	 		/*********************** send ether 16진수 *************************/
//	 		JSONObject jObjSendEther = new JSONObject();
//	 		jObjSendEther.put("from", CustomConfig.strBlockChainServerAccount);
//	 		jObjSendEther.put("to", account);
//	 		jObjSendEther.put("value", "0x4563918244f40000"); 
//	 		List<JSONObject> objList = new ArrayList<>();
//	 		objList.add(jObjSendEther);
//	 		jObj.put("jsonrpc","2.0");
//	 	    jObj.put("method","eth_sendTransaction");
//	 	    jObj.put("params", objList);
//	 	    jObj.put("id","2");
//	 	    request = new HttpEntity<String> (jObj.toJSONString(), headers);
//	 	    // REST API 호출
//	 	    String apiResultSendEther = restTemplate.postForObject(CustomConfig.strBlockChainServerIp+":"+CustomConfig.strBlockChainServerPort, request, String.class);
//	 	    log.info("apiResultSendEther: " + apiResultSendEther);
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			log.error("ParseException: " + e.getMessage());
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			log.error("Exception: " + e.getLocalizedMessage());
//			e.printStackTrace();
//		}
//		
//		return account;
//	}

}
