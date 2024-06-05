package auth_service.expenses_persistence.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario")
public class UserEntity {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "role_usuario",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id"))
  private Set<RoleEntity> roleEntities = new HashSet<>();

}
