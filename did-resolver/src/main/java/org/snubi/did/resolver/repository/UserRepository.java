package org.snubi.did.resolver.repository;

import java.util.Optional;

import org.snubi.did.resolver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUserSeq(Long userSeq);
  Optional<User> findByUserId(String userId);
}