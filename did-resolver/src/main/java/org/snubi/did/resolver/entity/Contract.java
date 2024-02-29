package org.snubi.did.resolver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(name = "tb_contract")
public class Contract extends BaseEntity {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contract_seq")
    private Long contractSeq;

//	
//	@NotNull
//	@ManyToOne(fetch = FetchType.LAZY, optional = false)  
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	@JoinColumn(name = "user_seq")
//	@JsonIgnore
//	private User user;
//	
	
	@Column(name = "address", nullable = false)
    private String address;
	
	@Column(name = "contract_name", nullable = false)
    private String contractName;
	
	
}
