package com.example.restspringboot.auth.token;

import com.example.restspringboot.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@SuppressWarnings("ALL")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String token;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "expired_at")
  private LocalDateTime expiresAt;

  @Column(name = "confirmed_at")
  private LocalDateTime confirmedAt;

  @ManyToOne
  @JoinColumn(nullable = false)
  private User user;

  public ConfirmationToken(
      String token, LocalDateTime createdAt, LocalDateTime expiresAt, User user) {
    this.token = token;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
    this.user = user;
  }
}
