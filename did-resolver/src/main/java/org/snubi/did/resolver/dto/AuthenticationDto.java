package org.snubi.did.resolver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // @RequestBody 에 필요 
@Builder
@AllArgsConstructor // @Builder 에 필요 
@Data
public class AuthenticationDto {
	private String did;
	private String authId;
	private String authType;
	private String controller;
	private String publicKey;
}
