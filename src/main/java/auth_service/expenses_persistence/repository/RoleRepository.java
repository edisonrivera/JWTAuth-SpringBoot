package auth_service.expenses_persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import auth_service.emun.RoleEnum;
import auth_service.expenses_persistence.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  @Query("SELECT EXISTS(SELECT 1 FROM RoleEntity r WHERE r.name = :name)")
  boolean existByName(@Param("name") RoleEnum name);

  @Query("SELECT r FROM RoleEntity r WHERE r.name = :name")
  Optional<RoleEntity> findByName(@Param("name") RoleEnum name);
}
