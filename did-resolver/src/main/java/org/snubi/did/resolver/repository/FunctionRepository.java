package org.snubi.did.resolver.repository;

import org.snubi.did.resolver.entity.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FunctionRepository extends JpaRepository<Function, Long> {
  
	
}
