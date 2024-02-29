package org.snubi.did.resolver.repository;

import java.util.Optional;

import org.snubi.did.resolver.entity.Signature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureRepository extends JpaRepository<Signature, Long> {
  
	Optional<Signature> findByToDidAndSignSeq(String todid, Integer signSeq); 
}
