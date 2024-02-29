package org.snubi.did.resolver.repository;

import java.util.Optional;

import org.snubi.did.resolver.entity.DidDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DidDocumentRepository extends JpaRepository<DidDocument, Long> {
	Optional<DidDocument> findByDid_DidSeq(Long didSeq);
}