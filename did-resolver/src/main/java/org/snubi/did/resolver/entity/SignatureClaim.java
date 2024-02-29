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
@Table(name = "tb_signature_claim")
public class SignatureClaim  extends BaseEntity {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "signature_claim_seq")
    private Long signatureClaimSeq;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)  
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "signature_seq")
	@JsonIgnore
	private Signature signature;
	
	
	@Column(name = "claim_id", nullable = false)
    private String claimId;
	
	public SignatureClaim() {}
	
	@Builder
    public SignatureClaim(Signature signature,String claimId) {
		this.signature = signature;	
		this.claimId = claimId;			
	} 
	
}
