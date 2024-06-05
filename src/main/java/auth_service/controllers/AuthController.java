package auth_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import auth_service.dto.request.LoginRequestDTO;
import auth_service.dto.request.RegisterRequestDTO;
import auth_service.dto.response.JwtResponse;
import auth_service.model.ResponseData;
import auth_service.service.AuthServiceController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth :: Controller", description = "Operations for login and register")
public class AuthController {

  private final AuthServiceController authServiceController;

  public AuthController(AuthServiceController authServiceController) {
    this.authServiceController = authServiceController;
  }

  @Operation(summary = "User login with username and password")
  @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseData<JwtResponse>> authenticateUser(@Valid @RequestBody LoginRequestDTO request) {
    return authServiceController.login(request);
  }

  @Operation(summary = "Register user")
  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResponseData<String>> registerUser(@Valid @RequestBody RegisterRequestDTO request) {
    return authServiceController.register(request);
  }
}
