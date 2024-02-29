package org.snubi.did.resolver.repository;

import java.util.Optional;
import org.snubi.did.resolver.entity.DidService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DidServiceRepository extends JpaRepository<DidService, Long> {
	Optional<DidService> findByDidDocument_DidDocumentSeqAndServiceId(Long didDocumentSeq, String serviceId);
	Optional<DidService> findByDidDocument_DidDocumentSeq(Long didDocumentSeq);
}