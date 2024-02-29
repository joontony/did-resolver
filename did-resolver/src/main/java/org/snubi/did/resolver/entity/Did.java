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
@Table(name = "tb_did")
public class Did  extends BaseEntity {
	
	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "did_seq")
    private Long didSeq;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)  
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "contract_seq")
	@JsonIgnore
	private Contract contract;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)  
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "user_seq")
	@JsonIgnore
	private User user;	
	
	@Column(name = "did", nullable = false)
    private String did;
	
	@Column(name = "method", nullable = false)
    private String method;
	
	@Column(name = "identifier", nullable = false)
    private String identifier;
	
	public Did() {}
	
	@Builder
    public Did(Contract contract, User user,String did,String method,String identifier) {	
		this.contract = contract;
		this.user = user;
		this.did = did;
		this.method = method;
		this.identifier = identifier;		
	} 

}
