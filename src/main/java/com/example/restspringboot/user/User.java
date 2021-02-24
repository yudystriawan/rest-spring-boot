package com.example.restspringboot.user;

import com.example.restspringboot.security.ApplicationUserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@SuppressWarnings("ALL")
@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

  /** */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(unique = true)
  private String username;

  @Column(unique = true)
  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  private ApplicationUserRole role;

  private Boolean locked = false;

  private Boolean enabled = false;

  @CreationTimestamp
  @Column(name = "created_at")
  private Timestamp createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private Timestamp updatedAt;

  public User(
      String name, String username, String email, String password, ApplicationUserRole role) {
    this.name = name;
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getGrantedAuthorities();
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !locked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
