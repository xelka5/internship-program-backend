package com.tusofia.internshipprogram.entity.finalReport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tusofia.internshipprogram.entity.BaseEntity;
import com.tusofia.internshipprogram.entity.application.InternshipApplication;
import com.tusofia.internshipprogram.enumeration.FinalReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FinalReport extends BaseEntity {

  private String reportFileLocation;

  private String reportNotes;

  private String trackingNumber;

  @Enumerated(EnumType.STRING)
  private FinalReportType finalReportType;

  @ManyToOne
  @JoinColumn(name = "application_id")
  @JsonIgnore
  private InternshipApplication internshipApplication;
}
