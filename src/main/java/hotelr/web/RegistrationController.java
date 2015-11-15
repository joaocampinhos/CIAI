package hotelr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping(value="/register")
public class RegistrationController {
  @RequestMapping(method=RequestMethod.GET)
  public String index(RedirectAttributes redirectAttrs) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth.isAuthenticated() && auth.getPrincipal().toString() != "anonymousUser") {
      redirectAttrs.addFlashAttribute("error", "You're already registered!");
      return "redirect:/dashboards";
    } else {
      return "register";
    }
  }
}