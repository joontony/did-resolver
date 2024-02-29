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
@Table(name = "tb_did_authentication")
@NoArgsConstructor
public class DidAuthentication extends BaseEntity {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "did_authentication_seq")
    private Long didAuthenticationSeq;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)  
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "did_document_seq")
	@JsonIgnore
	private DidDocument didDocument;
	
	@Column(name = "auth_id", nullable = false)
    private String authId;
	
	@Column(name = "auth_type", nullable = false)
    private String authType;
	
	@Column(name = "public_key", columnDefinition = "TEXT", nullable = false)
    private String publicKey;
	
	@Column(name = "controller", nullable = false)
    private String controller;	
	
	@Builder
    public DidAuthentication(DidDocument didDocument, String authId,String authType,String publicKey,String controller) {	
		this.didDocument = didDocument;
		this.authId = authId;
		this.authType = authType;
		this.publicKey = publicKey;
		this.controller = controller;		
	} 	
	
	public void updateAuthentication(String authId,String authType,String publicKey,String controller) {
		this.authId = authId;
		this.authType = authType;
		this.publicKey = publicKey;
		this.controller = controller;
	}

}
