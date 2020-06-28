package com.tusofia.internshipprogram.entity.internship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.internshipprogram.entity.BaseEntity;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.entity.report.InternReport;
import com.tusofia.internshipprogram.entity.user.EmployerDetails;
import com.tusofia.internshipprogram.enumeration.Currency;
import com.tusofia.internshipprogram.enumeration.DurationUnit;
import com.tusofia.internshipprogram.enumeration.InternshipStatus;
import com.tusofia.internshipprogram.enumeration.InternshipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "trackingNumber")}, name = "internship")
public class Internship extends BaseEntity {

  @NotEmpty
  private String title;

  @NotEmpty
  private String trackingNumber;

  @NotEmpty
  @Column(columnDefinition = "TEXT")
  private String description;

  @Enumerated(EnumType.STRING)
  @NotNull
  private InternshipStatus status;

  @NotNull
  private Date startDate;

  @NotNull
  private Long duration;

  @Enumerated(EnumType.STRING)
  @NotNull
  private DurationUnit durationUnit;

  @Enumerated(EnumType.STRING)
  @NotNull
  private InternshipType type;

  private Long salary;

  @Enumerated(EnumType.STRING)
  private Currency currency;

  @NotNull
  private Integer maxNumberOfStudents;

  @ManyToOne
  @JoinColumn(name = "employer_id")
  @JsonIgnore
  private EmployerDetails employer;

  @OneToMany(mappedBy = "internship",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<InternshipApplication> applications;

  @OneToMany(mappedBy = "internship", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<InternReport> internReports;

}
