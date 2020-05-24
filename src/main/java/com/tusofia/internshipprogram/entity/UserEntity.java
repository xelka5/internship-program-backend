package com.tusofia.internshipprogram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.internshipprogram.entity.enumeration.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotEmpty
  private String username;

  @JsonIgnore
  @ToString.Exclude
  @NotEmpty
  private String password;

  @NotEmpty
  @Email
  private String email;

  @NotNull
  @Enumerated(EnumType.STRING)
  private Role role;

  public UserEntity(@NotEmpty String username, @NotEmpty String password,
                    @NotEmpty @Email String email, @NotNull Role role) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
  }
}
