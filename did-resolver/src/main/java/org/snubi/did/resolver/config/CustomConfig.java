package org.snubi.did.resolver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class CustomConfig {
	
	
	
	public static Long chainNodeNumber;
	@Value("${blockchain.node.number}")
	public void setChainNodeNumber(Long prop) {
		CustomConfig.chainNodeNumber = prop;
	}
	
	
	public static String strEmrFileUploadPath;
	public static int strEmrFileUploadSize = 10;
	
	@Value("${cbserver.interface.home.upload}")
	public void setStrEmrFileUploadPath(String prop) {
		CustomConfig.strEmrFileUploadPath = prop;
	}
	
	
	public static String strTokenPrefix;
	@Value("${http.response.auth.token}")
	public void setStrTokenPrefix(String prop) {
		CustomConfig.strTokenPrefix = prop + " ";
	}

	public static String strSecrete;
	@Value("${security.oauth2.resource.jwt.key-value}")
	public void setStrSecrete(String prop) {
		CustomConfig.strSecrete = prop;
	}
	
	public static String strResponseAuthHeader;
	@Value("${http.response.auth.header}")
	public void setStrResponseAuthHeader(String prop) {
		CustomConfig.strResponseAuthHeader = prop;
	}

	public static String strResponseAuthClaimIssue;
	@Value("${http.response.auth.claims.issue}")
	public void setStrResponseAuthClaimIssue(String prop) {
		CustomConfig.strResponseAuthClaimIssue = prop;
	}
	
//	public static String strBlockChainServerAccount;
//	@Value("${blockchain.server.account}")
//	public void setStrBlockChainServerAccount(String prop) {
//		CustomConfig.strBlockChainServerAccount = prop;
//	}
//	
//	public static String strBlockChainServerIp;
//	@Value("${blockchain.server.ip}")
//	public void setStrBlockChainServerIp(String prop) {
//		CustomConfig.strBlockChainServerIp = prop;
//	}
//	
//	public static String strBlockChainServerPort;
//	@Value("${blockchain.server.port}")
//	public void setStrBlockChainServerPort(String prop) {
//		CustomConfig.strBlockChainServerPort = prop;
//	}
//	
//	public static String strBlockChainServerPrivateKey;
//	@Value("${blockchain.server.private.key}")
//	public void setStrBlockChainServerPrivateKey(String prop) {
//		CustomConfig.strBlockChainServerPrivateKey = prop;
//	}
//	
//	public static String strBlockChainSmartContractAddress;
//	@Value("${blockchain.smart.contract.address}")
//	public void setStrBlockChainSmartContractAddress(String prop) {
//		CustomConfig.strBlockChainSmartContractAddress = prop;
//	}
	
	public static String strKubenetesServerUrl;
	@Value("${kubernetes.server.url}")
	public void setStrKubenetesServerUrl(String prop) {
		CustomConfig.strKubenetesServerUrl = prop;
	}
	
	public static String strKubenetesServerIp;
	@Value("${kubernetes.server.ip}")
	public void setStrKubenetesServerIp(String prop) {
		CustomConfig.strKubenetesServerIp = prop;
	}
}
