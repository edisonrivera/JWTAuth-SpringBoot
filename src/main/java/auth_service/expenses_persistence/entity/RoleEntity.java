package auth_service.expenses_persistence.entity;

import auth_service.emun.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class RoleEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(name = "name")
  private RoleEnum name;
}