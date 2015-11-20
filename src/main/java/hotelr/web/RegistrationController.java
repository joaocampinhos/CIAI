package hotelr.web;

import hotelr.repository.*;
import hotelr.model.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping(value="/register")
public class RegistrationController {

  @Autowired
  ManagerRepository managers;

  @Autowired
  GuestRepository guests;

  @Autowired
  AdminRepository admins;

  @Autowired
  ModeratorRepository moderators;

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

  @RequestMapping(method=RequestMethod.POST)
  public String save(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("type") String type, RedirectAttributes redirectAttrs) {
    if (
      guests.findByEmail(email) != null ||
      managers.findByEmail(email) != null ||
      moderators.findByEmail(email) != null ||
      admins.findByEmail(email) != null) {
      redirectAttrs.addFlashAttribute("error", "That email is already registered!");
      return "redirect:/register";
    }

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    if (type.equals("manager")) {
      Manager manager = new Manager();
      manager.setName(name);
      manager.setEmail(email);
      manager.setPassword(encoder.encode(password));
      manager.setPending(true);
      managers.save(manager);
    } else if (type.equals("guest")) {
      Guest guest = new Guest();
      guest.setName(name);
      guest.setEmail(email);
      guest.setPassword(encoder.encode(password));
      guests.save(guest);
    }

    redirectAttrs.addFlashAttribute("message", "Account created successfully!");
    return "redirect:/login";
  }
}