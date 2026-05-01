package com.example.constructionsite.user.entity;

import com.example.constructionsite.user.enumeration.UserRoles;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "app_user",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_app_user_email", columnNames = "email")
    }
)
@Builder
public class UserEntity implements UserDetails {
  @Id
  @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "user_id_seq",
      sequenceName = "user_id_seq",
      allocationSize = 1
  )
  private Integer id;

  @NotBlank(message = "Ime i prezime su obavezni")
  private String fullName;

  @NotBlank(message = "Email je obavezan")
  @Email(message = "Email treba da bude ispravan")
  @Column(nullable = false, unique = true)
  private String email;

  @NotBlank(message = "Lozinka je obavezna")
  @Size(min = 6, message = "Lozinka mora imati barem 6 karaktera")
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRoles userRole;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  @Override
  public String getUsername() {
    return email;
  }
}
