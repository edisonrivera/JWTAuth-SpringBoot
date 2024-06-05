package auth_service.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import auth_service.constant.ResponseConstant;
import auth_service.dto.request.LoginRequestDTO;
import auth_service.dto.request.RegisterRequestDTO;
import auth_service.dto.response.JwtResponse;
import auth_service.emun.RoleEnum;
import auth_service.expenses_persistence.entity.RoleEntity;
import auth_service.expenses_persistence.entity.UserEntity;
import auth_service.expenses_persistence.service.RoleService;
import auth_service.expenses_persistence.service.UserService;
import auth_service.model.ResponseData;
import auth_service.security.jwt.JwtUtils;
import auth_service.security.services.impl.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleService roleService;
    private final PasswordEncoder encoder;


    public AuthServiceController(UserService userService, AuthenticationManager authenticationManager,
            JwtUtils jwtUtils, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.roleService = roleService;
        this.encoder = passwordEncoder;
    }

    public ResponseEntity<ResponseData<JwtResponse>> login(LoginRequestDTO request) {
        ResponseData<JwtResponse> response = new ResponseData<>(ResponseConstant.EMPTY);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            response.setData(new JwtResponse(jwtUtils.generateJwtToken(authentication), userDetails.getUsername()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Invalid username or password");
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

    }

    public ResponseEntity<ResponseData<String>> register(RegisterRequestDTO request){
        ResponseData<String> response = new ResponseData<>(ResponseConstant.EMPTY);
        try {
            String username = request.getUsername().strip();

            if (userService.existsByUsername(username)) {
                response.setMessage("Username is already in use");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            String password = encoder.encode(request.getPassword());

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(username);
            userEntity.setPassword(password);

            Optional<RoleEntity> rolePredefined = roleService.findByName(RoleEnum.ROLE_USER);
            rolePredefined.ifPresent(roleEntity -> userEntity.setRoleEntities(Set.of(roleEntity)));

            UserEntity userSaved = userService.save(userEntity);
            response.setData("User " + userSaved.getUsername() + " has been registered successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e){
            response.setMessage("Verify all data");
            log.error(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
