package org.snubi.did.resolver.repository;

import java.util.Optional;

import org.snubi.did.resolver.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
	Optional<Contract> findByContractSeq(Long contractSeq);
	Optional<Contract> findByContractName(String contractName);
}