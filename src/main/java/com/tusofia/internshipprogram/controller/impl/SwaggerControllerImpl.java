package com.tusofia.internshipprogram.controller.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Base url redirection to swagger documentation user interface.
 *
 * @author DCvetkov
 * @since 2020
 */
@RestController
public class SwaggerControllerImpl {

  @RequestMapping(value = "/")
  public void redirectToSwagger(HttpServletResponse response) throws IOException {
    response.sendRedirect("/swagger-ui.html");
  }

}
