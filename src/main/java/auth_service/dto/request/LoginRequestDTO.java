package auth_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
	@NotBlank(message = "Username field not present")
  	private String username;

	@NotBlank(message = "Password field not present")
	private String password;
}
