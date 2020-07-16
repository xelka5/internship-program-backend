package com.tusofia.internshipprogram.repository;

import com.tusofia.internshipprogram.entity.internship.Internship;
import com.tusofia.internshipprogram.enumeration.InternshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {

  Optional<Internship> findByTrackingNumber(String trackingNumber);

  List<Internship> findByStatusOrderByCreateDateDesc(InternshipStatus status);

  @Query("SELECT i FROM Internship i WHERE i.status = ?1 AND (i.title LIKE %?2% OR i.description LIKE %?3%)")
  List<Internship> findByStatusAndTitleContainingOrDescriptionContaining(InternshipStatus status, String title, String description);

  @Transactional
  @Modifying
  @Query("DELETE FROM Internship i WHERE i.id = ?1")
  void deleteByIdentifier(Long id);

}
