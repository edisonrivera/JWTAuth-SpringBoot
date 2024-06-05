package auth_service.expenses_persistence.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import auth_service.expenses_persistence.entity.UserEntity;
import auth_service.expenses_persistence.repository.UserRepository;
import auth_service.expenses_persistence.service.UserService;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }
    
}
