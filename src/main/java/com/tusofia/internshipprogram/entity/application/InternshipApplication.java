package com.tusofia.internshipprogram.entity.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.internshipprogram.entity.BaseEntity;
import com.tusofia.internshipprogram.entity.finalReport.FinalReport;
import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.entity.user.InternDetails;
import com.tusofia.internshipprogram.enumeration.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InternshipApplication extends BaseEntity {

  @Enumerated(EnumType.STRING)
  private ApplicationStatus status;

  @Column(columnDefinition = "TEXT")
  private String details;

  private String trackingNumber;

  private String responseNotes;

  @ManyToOne
  @JoinColumn(name = "internship_id")
  @JsonIgnore
  private Internship internship;

  @OneToOne
  @JoinColumn(name = "intern_id")
  @JsonIgnore
  private InternDetails internDetails;

  @OneToMany(mappedBy = "internshipApplication",  cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<FinalReport> finalReports;

}
