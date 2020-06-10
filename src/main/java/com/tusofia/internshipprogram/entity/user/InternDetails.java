package com.tusofia.internshipprogram.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.internshipprogram.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class InternDetails extends BaseEntity {

  @NotEmpty
  private String firstName;

  @NotEmpty
  private String lastName;

  @Min(value = 16, message = "Age should not be less than 16")
  @Max(value = 65, message = "Age should not be greater than 65")
  private Integer age;

  @NotEmpty
  private String university;

  @NotEmpty
  private String course;

  private Date startDate;

  @NotEmpty
  private String previousEducation;

  @ElementCollection
  @CollectionTable(name="skills", joinColumns=@JoinColumn(name="intern_id"))
  @Column(name="skill")
  private List<String> skills;

  @OneToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;
}
