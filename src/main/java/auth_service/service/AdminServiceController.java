package auth_service.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import auth_service.constant.ResponseConstant;
import auth_service.convert.UserConvert;
import auth_service.dto.request.RegisterAdminRequestDTO;
import auth_service.dto.response.UserListResponse;
import auth_service.emun.RoleEnum;
import auth_service.expenses_persistence.entity.RoleEntity;
import auth_service.expenses_persistence.entity.UserEntity;
import auth_service.expenses_persistence.service.RoleService;
import auth_service.expenses_persistence.service.UserService;
import auth_service.model.ResponseData;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class AdminServiceController {
    private final PasswordEncoder passwordEncoder;
    private final UserConvert userConvert;
    private final UserService userService;
    private final RoleService roleService;

    public AdminServiceController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder
    , UserConvert userConvert) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.userConvert = userConvert;
    }

    public ResponseEntity<ResponseData<String>> adminRegister(RegisterAdminRequestDTO request){
        ResponseData<String> response = new ResponseData<>(ResponseConstant.EMPTY);
        try {
            String username = request.getUsername().strip();
            if(userService.existsByUsername(username)){
                response.setMessage("Username already exists");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            Set<RoleEnum> parsedRoles = request.getRoles().stream().map(RoleEnum::valueOf).collect(Collectors.toSet());
            Set<RoleEntity> roleEntities = parsedRoles.stream()
                    .map(roleService::findByName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            String password = passwordEncoder.encode(request.getPassword());

            UserEntity userToSave = new UserEntity();
            userToSave.setUsername(username);
            userToSave.setPassword(password);
            userToSave.setRoleEntities(roleEntities);

            UserEntity userSaved = userService.save(userToSave);
            response.setMessage("User " +  userSaved.getUsername() + " registered successfully");

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setMessage("Error to register an user");
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseData<UserListResponse>> getAllUsers(Integer pageNo, Integer pageSize) {
        ResponseData<UserListResponse> response = new ResponseData<>(ResponseConstant.EMPTY);

        try{
            Page<UserEntity> users = userService.findAll(PageRequest.of(pageNo, pageSize));
            UserListResponse userListResponse = new UserListResponse();
            userListResponse.setUsers(users.stream().parallel().map(userConvert).toList());
            userListResponse.setTotalUsers(users.getTotalElements());

            response.setData(userListResponse);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            response.setMessage("Error to register an user");
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
