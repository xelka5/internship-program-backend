package com.tusofia.internshipprogram.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
public class EmployerDetails extends BaseEntity {

  @NotEmpty
  private String companyName;

  @Min(value = 1, message = "Number of workers should not be less than 1")
  @Max(value = 1000000, message = "Number of workers should not be greater than 1 milion")
  private Integer numberOfWorkers;

  @NotEmpty
  private String historyNotes;

  @NotEmpty
  private String descriptionNotes;


  @OneToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private UserEntity user;

}
