package auth_service.expenses_persistence.service;

import java.util.Optional;


import auth_service.emun.RoleEnum;
import auth_service.expenses_persistence.entity.RoleEntity;

public interface RoleService {
    boolean existByName(RoleEnum name);
    Optional<RoleEntity> findByName(RoleEnum name);
}
