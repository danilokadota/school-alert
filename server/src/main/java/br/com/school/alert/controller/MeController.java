package br.com.school.alert.controller;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.school.alert.controller.dto.UserDTO;
import br.com.school.alert.exception.WebException;
import br.com.school.alert.security.UserUtils;

@RestController
@RequestMapping("/meController")
public class MeController {

  @RequestMapping(method = RequestMethod.GET)
  @ResponseBody
  public UserDTO me() {

    if (UserUtils.isUserLoggedAsHolmesUser()) {
      return new UserDTO(UserUtils.getUserLogged());
    }

    throw new WebException(HttpStatus.UNAUTHORIZED, "unauthorized");
  }

}
