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
@Table(name = "tb_did_public_key")
@NoArgsConstructor
public class DidPublicKey extends BaseEntity {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "did_public_key_seq")
    private Long didPublicKeySeq;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)  
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "did_document_seq")
	@JsonIgnore
	private DidDocument didDocument;
	
	@Column(name = "pk_id", nullable = false)
    private String pkId;
	
	@Column(name = "public_key_type", nullable = false)
    private String publicKeyType;
	
	@Column(name = "public_key", columnDefinition = "TEXT", nullable = false)
    private String publicKey;
	
	@Column(name = "controller", nullable = false)
    private String controller;
	
	@Builder
    public DidPublicKey(DidDocument didDocument,String pkId,String publicKeyType,String publicKey,String controller) {
		this.didDocument = didDocument;
		this.pkId = pkId;
		this.publicKeyType = publicKeyType;
		this.publicKey = publicKey;
		this.controller = controller;
	}
	
	public void updatePublicKey(String pkId,String publicKeyType,String publicKey,String controller) {
		this.pkId = pkId;
		this.publicKeyType = publicKeyType;
		this.publicKey = publicKey;
		this.controller = controller;
	}

}
