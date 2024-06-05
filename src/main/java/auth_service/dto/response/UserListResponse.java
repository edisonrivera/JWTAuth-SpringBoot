package auth_service.dto.response;


import java.util.List;

import auth_service.UserRecord;
import lombok.Data;


@Data
public class UserListResponse {
  private List<UserRecord> users;
  private Long totalUsers;
}
