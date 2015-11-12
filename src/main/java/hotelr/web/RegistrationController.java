package hotelr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping(value="/register")
public class RegistrationController {
  @RequestMapping(method=RequestMethod.GET)
  public String index() {
    if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
      // TODO: show error, already logged in

      return "";
    } else {
      // TODO: render form
      return "";
    }
  }
}