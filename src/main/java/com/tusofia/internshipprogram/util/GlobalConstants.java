package com.tusofia.internshipprogram.util;

/**
 * Constants used across the application.
 *
 * @author DCvetkov
 * @since 2020
 */
public final class GlobalConstants {

  /** Exception Messages **/
  public static final String USER_NOT_FOUND_MESSAGE = "User does not exist";
  public static final String INTERNSHIP_NOT_FOUND_MESSAGE = "Internship does not exist";
  public static final String INTERNSHIP_FULL_MESSAGE = "Internship is full";
  public static final String REPORT_NOT_FOUND_MESSAGE = "Report does not exist";
  public static final String INSUFFICIENT_RIGHTS_REPORT_EDIT_MESSAGE = "User has no rights to edit report";
  public static final String ACTIVE_INTERNSHIPS_CAN_BE_FINISHED_MESSAGE = "Only active internships can be finished";
  public static final String INTERNSHIP_APPLICATION_NOT_FOUND_MESSAGE = "Internship application does not exist";
  public static final String FINAL_REPORT_NOT_FOUND_MESSAGE = "Final report does not exist";
  public static final String REPORT_NOT_FOUND_FOR_INTERNSHIP_MESSAGE = "Intern report not found for this internship";
  public static final String INSUFFICIENT_RIGHTS_FINAL_REPORT_MESSAGE = "User does not have rights to get final reports";
  public static final String ALREADY_APPLIED_MESSAGE = "You already applied for this internship";
  public static final String CANNOT_CHANGE_INTERNSHIP_STATUS_MESSAGE = "Employer not privileged to change application status";
  public static final String INVALID_FILE_NAME_MESSAGE = "Could not store the file. Error: invalid file name";
  public static final String STORE_FILE_GENERAL_ERROR_MESSAGE = "Could not store the file. Error: %s";
  public static final String COULD_NOT_INITIALIZE_DIRECTORIES_MESSAGE = "Could not initialize directories!";
  public static final String COULD_NOT_READ_TEMPLATE_FILE_MESSAGE = "Could not read template file: %s";
  public static final String COULD_NOT_BUILD_EMAIL_MESSAGE = "Could not build email message";
  public static final String USER_NOT_ACTIVE_MESSAGE = "User account is not active";

  /** OAuth2 Scopes **/
  public static final String READ_SCOPE = "read";
  public static final String WRITE_SCOPE = "write";

  /** Labels **/
  public static final String CLASSPATH_LABEL = "classpath:";


  private GlobalConstants() {
    throw new AssertionError();
  }

}
