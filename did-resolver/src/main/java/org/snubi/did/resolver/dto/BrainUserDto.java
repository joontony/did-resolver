package org.snubi.did.resolver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor // @RequestBody 에 필요 
@Builder
@AllArgsConstructor // @Builder 에 필요 
@Data
public class BrainUserDto {
	
	private String userId;
	private String name;
	private String organization;
	private String accountAddr;
	private String accountPwd;
	private String created;
	private String updated;	
}
