package auth_service.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import auth_service.dto.request.RegisterAdminRequestDTO;
import auth_service.dto.response.UserListResponse;
import auth_service.model.ResponseData;
import auth_service.service.AdminServiceController;


@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin :: Controller", description = "Operations for admin users")
public class AdminController {
    private final AdminServiceController adminServiceController;

    public AdminController(AdminServiceController adminServiceController) {
        this.adminServiceController = adminServiceController;
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseData<String>> register(@Valid @RequestBody RegisterAdminRequestDTO request) {
        return adminServiceController.adminRegister(request);
    }

    @Operation(summary = "List all registered users")
    @GetMapping("/users/{pageNo}/{pageSize}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseData<UserListResponse>> getAllUsers(@PathVariable Integer pageNo,
                                                                    @PathVariable @Max(10) @Min(1) Integer pageSize) {
        return adminServiceController.getAllUsers(pageNo, pageSize);
    }
}
