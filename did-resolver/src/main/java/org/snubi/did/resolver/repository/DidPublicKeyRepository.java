package org.snubi.did.resolver.repository;

import java.util.Optional;

import org.snubi.did.resolver.entity.DidPublicKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DidPublicKeyRepository extends JpaRepository<DidPublicKey, Long> {
	Optional<DidPublicKey> findByDidDocument_DidDocumentSeq(Long didDocumentSeq);
	Optional<DidPublicKey> findByDidDocument_DidDocumentSeqAndPkId(Long didDocumentSeq,String publickeyId);
}