package org.snubi.did.resolver.repository;

import org.snubi.did.resolver.entity.SignatureClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureClaimRepository extends JpaRepository<SignatureClaim, Long> {
  
	
}
