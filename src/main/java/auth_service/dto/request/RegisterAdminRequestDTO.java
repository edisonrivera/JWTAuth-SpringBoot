package auth_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

@Data
public class RegisterAdminRequestDTO {
    @NotBlank(message = "Username field not present")
    @Size(min = 3, max = 20, message = "Username must size between 3 and 20 characters")
    private String username;

    @NotEmpty(message = "At least 1 rol is required")
    private Set<String> roles;

    @NotBlank(message = "Password field not present")
    @Size(min = 6, max = 40,  message = "Password must size between 6 and 40 characters")
    private String password;
}
