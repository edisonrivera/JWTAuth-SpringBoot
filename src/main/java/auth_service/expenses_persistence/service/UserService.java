package auth_service.expenses_persistence.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import auth_service.expenses_persistence.entity.UserEntity;

public interface UserService {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
    UserEntity save(UserEntity userEntity);
    Page<UserEntity> findAll(Pageable pageable);
}
