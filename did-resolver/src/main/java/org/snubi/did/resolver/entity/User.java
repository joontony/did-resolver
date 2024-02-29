package org.snubi.did.resolver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.jetbrains.annotations.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(name = "tb_user")
public class User extends BaseEntity {

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_seq")
    private Long userSeq;
	
	@Column(name = "user_address", nullable = false)
    private String userAddress;
	
	@Column(name = "user_id", nullable = false)
    private String userId;
	
	@Column(name = "admin_flag", columnDefinition = "boolean default false")
    private boolean adminFlag;
	
	public User() {}
	
	@Builder
    public User(String userId,String userAddress) {
		this.userId = userId;	
		this.userAddress = userAddress;			
	} 
}
