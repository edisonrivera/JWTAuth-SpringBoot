package auth_service.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import auth_service.model.ResponseData;
import auth_service.security.jwt.JwtUtils;
import auth_service.service.TestServiceController;

@RestController
@RequestMapping("/api/v1/test")
@Tag(name = "Test :: Controller", description = "Test Controller for testing roles")
public class TestController {
  private final JwtUtils jwtUtils;
  private final TestServiceController testServiceController;

  public TestController(JwtUtils jwtUtils, TestServiceController testServiceController) {
    this.testServiceController = testServiceController;
    this.jwtUtils = jwtUtils;
  }

  @GetMapping("/all")
  public ResponseEntity<ResponseData<String>> allAccess() {
    return testServiceController.publicAccess();
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public ResponseEntity<ResponseData<String>> userAccess(@RequestHeader("Authorization") String authHeader) {
    return testServiceController.user(jwtUtils.getIdFromJwtToken(authHeader));
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<ResponseData<String>> adminAccess() {
    return testServiceController.admin();
  }
}
