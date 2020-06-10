package com.tusofia.internshipprogram.entity.internship;

import com.tusofia.internshipprogram.entity.BaseEntity;
import com.tusofia.internshipprogram.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Internship extends BaseEntity {

  @NotEmpty
  private String title;

  @NotEmpty
  private String trackingNumber;

  @NotEmpty
  @Column(columnDefinition = "TEXT")
  private String description;

  @NotNull
  private Date startDate;

  @NotEmpty
  private String salary;

  @NotNull
  private Integer maxNumberOfStudents;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}
