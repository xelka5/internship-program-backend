package com.tusofia.internshipprogram.entity.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.internshipprogram.entity.BaseEntity;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.InternDetails;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "trackingNumber")}, name = "intern_report")
public class InternReport extends BaseEntity {

  @NotNull
  private Date reportStartDate;

  @NotNull
  private Date reportEndDate;

  @NotEmpty
  @Column(columnDefinition = "TEXT")
  private String reportDetails;

  @NotEmpty
  private String trackingNumber;

  @ManyToOne
  @JoinColumn(name = "internship_id")
  @JsonIgnore
  private Internship internship;

  @ManyToOne
  @JoinColumn(name = "intern_id")
  @JsonIgnore
  private InternDetails internDetails;

}
