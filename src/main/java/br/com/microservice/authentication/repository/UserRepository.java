package br.com.microservice.authentication.repository;

import br.com.microservice.authentication.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findById(String userId);
    Optional<UserEntity> findByUsername(String username);
}
