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

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(name = "tb_signature")
public class Signature  extends BaseEntity {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "signature_seq")
    private Long signatureSeq;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)  
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "contract_seq")
	@JsonIgnore
	private Contract contract;
	
	@Column(name = "sign_seq", nullable = false)
    private Integer signSeq;
	
	@Column(name = "service_id", nullable = false)
    private String serviceId;
	
	@Column(name = "from_did", nullable = false)
    private String fromDid;
	
	@Column(name = "to_did", nullable = false)
    private String toDid;
	
	@Column(name = "signature",columnDefinition = "TEXT")
    private String signature;
	
	public Signature() {}
	
	@Builder
    public Signature(Contract contract, Integer signSeq,String serviceId,String fromDid,String toDid,String signature) {	
		this.contract = contract;
		this.signSeq = signSeq;
		this.serviceId = serviceId;
		this.fromDid = fromDid;
		this.toDid = toDid;
		this.signature = signature;		
	} 
}
