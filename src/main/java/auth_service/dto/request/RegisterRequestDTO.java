package auth_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequestDTO {
  @NotBlank(message = "Username field not present")
  @Size(min = 3, max = 20, message = "Username must size between 3 and 20 characters")
  private String username;

  @NotBlank(message = "Password field not present")
  @Size(min = 6, max = 40,  message = "Password must size between 6 and 40 characters")
  private String password;
}