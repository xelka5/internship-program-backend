package com.tusofia.internshipprogram.entity.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.internshipprogram.entity.BaseEntity;
import com.tusofia.internshipprogram.entity.internship.Internship;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Entity
public class EmployerDetails extends BaseEntity {

  @NotEmpty
  private String companyName;

  @Min(value = 1, message = "Number of workers should not be less than 1")
  @Max(value = 1000000, message = "Number of workers should not be greater than 1 million")
  private Integer numberOfWorkers;

  @NotEmpty
  private String historyNotes;

  @NotEmpty
  private String descriptionNotes;


  @OneToOne
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;

  @OneToMany(mappedBy="employer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Internship> internships;

}
