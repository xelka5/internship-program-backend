package com.tusofia.internshipprogram.controller;

import com.tusofia.internshipprogram.dto.BaseResponseDto;
import com.tusofia.internshipprogram.dto.internship.AssignedInternDto;
import com.tusofia.internshipprogram.dto.internship.FinishInternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipDto;
import com.tusofia.internshipprogram.dto.internship.InternshipExtendedDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * Api description of internship operations controller.
 *
 * @author DCvetkov
 * @since 2020
 */
@Api(tags={"Internship Endpoints"}, description = "Internship operations")
public interface InternshipController {

  @ApiOperation(value = "Get All Internships By Status",
          notes = "Retrieves all internships by status: [ACTIVE, FINISHED, CANCELLED]")
  List<InternshipExtendedDto> getAllInternshipsByStatus(@RequestParam("status") String status);

  @ApiOperation(value = "Search Active Internships",
          notes = "Searches active internships by search term contained in title and description")
  List<InternshipExtendedDto> searchActiveInternships(@RequestParam("searchTerm") String searchTerm);

  @ApiOperation(value = "Get Internship Report Info Employer",
          notes = "Retrieves all assigned interns by internship status: [ACTIVE, FINISHED, CANCELLED]")
  List<InternshipExtendedDto> getAssignedInternInternshipsByStatus(@RequestParam("status") String status,
                                                                   @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Get Employer Internships By Status",
          notes = "Retrieves all internships for single employer by status: [ACTIVE, FINISHED, CANCELLED]")
  List<InternshipDto> getEmployerInternshipsByStatus(@RequestParam("status") String status,
                                                     @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Get Internship By Tracking Number",
          notes = "Retrieves internship information for given internship tracking number")
  InternshipDto getInternshipByTrackingNumber(@PathVariable("trackingNumber") String trackingNumber,
                                              @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Get Assigned Interns",
          notes = "Retrieves all assigned interns for single internship by tracking number")
  List<AssignedInternDto> getAssignedInterns(@PathVariable("trackingNumber") String trackingNumber,
                                             @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Add New Internship",
          notes = "Creates new internship")
  BaseResponseDto addNewInternship(@Valid @RequestBody InternshipDto internshipDto,
                                   @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Edit Internship",
          notes = "Edits already existing internship")
  BaseResponseDto editInternship(@Valid @RequestBody InternshipDto internshipDto,
                                 @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Finish Internship",
          notes = "Sets internship status to FINISHED")
  BaseResponseDto finishInternship(@Valid @RequestBody FinishInternshipDto finishInternshipDto,
                                   @ApiIgnore Authentication authentication);

  @ApiOperation(value = "Delete Internship",
          notes = "Deletes active internship and sends email to all assigned interns")
  BaseResponseDto deleteInternship(@PathVariable("trackingNumber") String trackingNumber,
                                   @ApiIgnore Authentication authentication);
}
