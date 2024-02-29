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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@ToString
@Table(name = "tb_did_service")
@NoArgsConstructor
public class DidService extends BaseEntity {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "did_service_seq")
    private Long didServiceSeq;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)  
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "did_document_seq")
	@JsonIgnore
	private DidDocument didDocument;
	
	@Column(name = "service_id", nullable = false)
    private String serviceId;
	
	@Column(name = "service_type", nullable = false)
    private String serviceType;
	
	@Column(name = "public_key", columnDefinition = "TEXT", nullable = false)
    private String publicKey;
	
	@Column(name = "service_endpoint", nullable = false)
    private String serviceEndpoint;
	
	
	@Builder
    public DidService(DidDocument didDocument,String serviceId,String serviceType,String publicKey,String serviceEndpoint) {
		this.didDocument = didDocument;	
		this.serviceId = serviceId;	
		this.serviceType = serviceType;	
		this.publicKey = publicKey;	
		this.serviceEndpoint = serviceEndpoint;	
	}
	
	public void updateService(String serviceType,String publicKey,String serviceEndpoint) {
		this.serviceType = serviceType;	
		this.publicKey = publicKey;	
		this.serviceEndpoint = serviceEndpoint;	
	}

}
