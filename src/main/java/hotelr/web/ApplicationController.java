package hotelr.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {

  @RequestMapping(value="/")
  public String root(Model model) {
    return "/index";
  }

  // Sign up form
  @RequestMapping(value="/register")
  public String registerform(Model model) {
    return "/register";
  }

  // Sign in form
  @RequestMapping(value="/login")
  public String loginform(Model model) {
    return "/login";
  }

}
