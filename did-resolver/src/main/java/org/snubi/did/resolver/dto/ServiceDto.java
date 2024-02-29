package org.snubi.did.resolver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // @RequestBody 에 필요 
@Builder
@AllArgsConstructor // @Builder 에 필요 
@Data
public class ServiceDto {
	private String did;
	private String serviceId;
	private String serviceEndpoint;
	private String serviceType;
	private String publicKey;
}
