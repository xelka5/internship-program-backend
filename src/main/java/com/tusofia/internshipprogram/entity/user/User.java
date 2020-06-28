package com.tusofia.internshipprogram.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.internshipprogram.entity.BaseEntity;
import com.tusofia.internshipprogram.enumeration.UserRole;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")}, name = "user")
public class User extends BaseEntity {

  @NotEmpty
  private String username;

  @JsonIgnore
  @ToString.Exclude
  private String password;

  @NotEmpty
  @Email
  private String email;

  @NotNull
  @Enumerated(EnumType.STRING)
  private UserRole role;

  private String profileImageName;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private InternDetails internDetails;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval=true)
  private EmployerDetails employerDetails;

}
