package org.snubi.did.resolver.controller;

import org.snubi.did.resolver.common.CustomResponseEntity;
import org.snubi.did.resolver.dto.AuthenticationDto;
import org.snubi.did.resolver.dto.DidDto;
import org.snubi.did.resolver.dto.DidSubDto;
import org.snubi.did.resolver.dto.PublicKeyDto;
import org.snubi.did.resolver.dto.ServiceDto;
import org.snubi.did.resolver.dto.SignatureDto;
import org.snubi.did.resolver.dto.VcSignatureDto;
import org.snubi.did.resolver.service.DidApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DidController {
	
	private final DidApiService didApiService;
	
	@RequestMapping(value = "/did/create/document", method = RequestMethod.POST)
	public ResponseEntity<?> createDocument(@RequestBody DidDto didDto) throws Exception {		
		log.info("--------------------------------------------");
		log.info("[resolver-server]:DID생성 {}", didDto.toString());
		log.info("--------------------------------------------");	
		return CustomResponseEntity.succResponse(didApiService.createDocument(didDto),"");				
	}
	
	@RequestMapping(value = "/signature/create", method = RequestMethod.POST)
	public ResponseEntity<?> createSignature(@RequestBody SignatureDto signatureDto) throws Exception {		
		log.info("--------------------------------------------");
		log.info("[resolver-server]:signature생성 {}", signatureDto.toString());
		log.info("--------------------------------------------");	
		return CustomResponseEntity.succResponse(didApiService.createSignature(signatureDto),"");				
	}
	
	@RequestMapping(value = "/did/upsert/authentication", method = RequestMethod.PUT)
	public ResponseEntity<?> upsertAuthentication(@RequestBody AuthenticationDto authenticationDto) throws Exception {			
		return CustomResponseEntity.succResponse(didApiService.upsertAuthentication(authenticationDto),"");				
	}
	
	@RequestMapping(value = "/did/upsert/publickey", method = RequestMethod.PUT)
	public ResponseEntity<?> upsertPublicKey(@RequestBody PublicKeyDto publicKeyDto) throws Exception {			
		return CustomResponseEntity.succResponse(didApiService.upsertPublicKey(publicKeyDto),"");				
	}
	
	@RequestMapping(value = "/did/upsert/service", method = RequestMethod.PUT)
	public ResponseEntity<?> upsertService(@RequestBody ServiceDto serviceDto) throws Exception {			
		return CustomResponseEntity.succResponse(didApiService.upsertService(serviceDto),"");				
	}
	
	@RequestMapping(value = "/credential/avchain", method = RequestMethod.POST)
	public ResponseEntity<?> getVcSeq() throws Exception {			
		return CustomResponseEntity.succResponse( VcSignatureDto.builder().vcSignatureSeq("xxxxSeq").vcDocument("xxxxDoc").build()    ,"");				
	}
	
	@RequestMapping(value = "/did/authentications", method = RequestMethod.POST)
	public ResponseEntity<?> selectAuthentication(@RequestBody DidSubDto didSubDto) throws Exception {			
		return CustomResponseEntity.succResponse(didApiService.selectAuthentication(didSubDto.getDid(),didSubDto.getAuthenticationId()),"");
	}
	
	@RequestMapping(value = "/did/publickeys", method = RequestMethod.POST)
	public ResponseEntity<?> selectPublicKey(@RequestBody DidSubDto didSubDto) throws Exception {			
		return CustomResponseEntity.succResponse(didApiService.selectPublicKey(didSubDto.getDid(),didSubDto.getPublickeyId()),"");
	}
	
	@RequestMapping(value = "/did/services", method = RequestMethod.POST)
	public ResponseEntity<?> selectService(@RequestBody DidSubDto didSubDto) throws Exception {			
		return CustomResponseEntity.succResponse(didApiService.selectService(didSubDto.getDid(),didSubDto.getServiceId()),"");
	}
	
	@RequestMapping(value = "/signature", method = RequestMethod.POST)
	public ResponseEntity<?> selectSignature(@RequestBody DidSubDto didSubDto) throws Exception {			
		return CustomResponseEntity.succResponse(didApiService.selectSignature(didSubDto),"");
	}

}
