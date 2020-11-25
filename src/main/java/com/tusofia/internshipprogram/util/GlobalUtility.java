package com.tusofia.internshipprogram.util;

import com.tusofia.internshipprogram.exception.MailSendingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static com.tusofia.internshipprogram.util.GlobalConstants.CLASSPATH_LABEL;
import static com.tusofia.internshipprogram.util.GlobalConstants.COULD_NOT_READ_TEMPLATE_FILE_MESSAGE;

@Slf4j
public final class GlobalUtility {

  private GlobalUtility() {
    throw new AssertionError();
  }

  public static String getEmailBody(String fileLocation) {
    try {
      var resourceFile = ResourceUtils.getURL(CLASSPATH_LABEL.concat(fileLocation));

      return new BufferedReader(new InputStreamReader(resourceFile.openStream()))
              .lines()
              .collect(Collectors.joining());

    } catch (Exception e) {
      String errorMessage = String.format(COULD_NOT_READ_TEMPLATE_FILE_MESSAGE, fileLocation);

      LOGGER.error(errorMessage, e);
      throw new MailSendingException(errorMessage, e);
    }
  }
}
