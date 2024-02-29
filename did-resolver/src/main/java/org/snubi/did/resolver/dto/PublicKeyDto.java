package org.snubi.did.resolver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // @RequestBody 에 필요 
@Builder
@AllArgsConstructor // @Builder 에 필요 
@Data
public class PublicKeyDto {
	private String did;
	private String publicKeyId;
	private String publicKeyType;
	private String controller;
	private String publicKey;
}
