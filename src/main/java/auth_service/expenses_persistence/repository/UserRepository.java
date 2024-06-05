package auth_service.expenses_persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import auth_service.expenses_persistence.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
  Optional<UserEntity> findByUsername(String username);

  @Query("SELECT EXISTS(SELECT 1 FROM UserEntity u WHERE u.username = :username)")
  boolean existsByUsername(String username);
}
