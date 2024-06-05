package auth_service.expenses_persistence.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import auth_service.emun.RoleEnum;
import auth_service.expenses_persistence.entity.RoleEntity;
import auth_service.expenses_persistence.repository.RoleRepository;
import auth_service.expenses_persistence.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean existByName(RoleEnum name) {
        return roleRepository.existByName(name);
    }

    @Override
    public Optional<RoleEntity> findByName(RoleEnum name) {
        return roleRepository.findByName(name);
    }
    
}
