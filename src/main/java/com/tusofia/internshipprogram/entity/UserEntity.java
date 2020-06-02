package com.tusofia.internshipprogram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tusofia.internshipprogram.entity.enumeration.Role;
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
public class UserEntity extends BaseEntity{

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

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private InternDetails internDetails;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private EmployerDetails employerDetails;
}
