package hotelr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value="/register")
public class RegistrationController {
  @RequestMapping(method=RequestMethod.GET)
  public String index(RedirectAttributes redirectAttrs) {
    if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
      redirectAttrs.addFlashAttribute("error", "You're already registered!");
      return "redirect:/dashboards";
    } else {
      return "register";
    }
  }
}