package org.snubi.did.resolver.repository;

import java.util.Optional;

import org.snubi.did.resolver.entity.Did;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DidRepository extends JpaRepository<Did, Long> {
  Optional<Did> findByDid(String did);
}