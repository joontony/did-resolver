package org.snubi.did.resolver.service;

import org.snubi.did.resolver.dto.AuthenticationDto;
import org.snubi.did.resolver.dto.DidDto;
import org.snubi.did.resolver.dto.DidSubDto;
import org.snubi.did.resolver.dto.PublicKeyDto;
import org.snubi.did.resolver.dto.ResponseDidDto;
import org.snubi.did.resolver.dto.ResponseSignatureDto;
import org.snubi.did.resolver.dto.ServiceDto;
import org.snubi.did.resolver.dto.SignatureDto;
import org.springframework.stereotype.Service;

@Service
public interface DidApiService {
	ResponseDidDto createDocument(DidDto didDto) throws Exception;
	boolean upsertAuthentication(AuthenticationDto authenticationDto);
	boolean upsertPublicKey(PublicKeyDto publicKeyDto);
	boolean upsertService(ServiceDto serviceDto);
	ResponseSignatureDto createSignature(SignatureDto signatureDto) throws Exception;
	AuthenticationDto selectAuthentication(String did, String authenticationId);
	PublicKeyDto selectPublicKey(String did, String publicKeyId);
	ServiceDto selectService(String did, String serviceId);
	SignatureDto selectSignature(DidSubDto didSubDto);
}
