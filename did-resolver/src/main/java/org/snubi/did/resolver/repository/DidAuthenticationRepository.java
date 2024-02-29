package org.snubi.did.resolver.repository;

import java.util.Optional;
import org.snubi.did.resolver.entity.DidAuthentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DidAuthenticationRepository extends JpaRepository<DidAuthentication, Long> {
	Optional<DidAuthentication> findByDidDocument_DidDocumentSeq(Long didDocumentSeq);
	Optional<DidAuthentication> findByDidDocument_DidDocumentSeqAndAuthId(Long didDocumentSeq, String authenticationId);
}