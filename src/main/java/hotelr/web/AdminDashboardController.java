package hotelr.web;

import hotelr.repository.*;
import hotelr.model.*;
import hotelr.exception.*;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping(value="/dashboards/admin")
public class AdminDashboardController {
  @Autowired
  AdminRepository admins;

  @Autowired
  ManagerRepository managers;

  @Autowired
  ModeratorRepository moderators;

  @Autowired
  GuestRepository guests;

  @Autowired
  BookingRepository bookings;

  @Autowired
  HotelRepository hotels;

  @RequestMapping(method=RequestMethod.GET)
  public String index(Model model) {
    // Admin admin = admins.findByName("Jessica Pearson");
    model.addAttribute("managers", managers.findByPending(false));
    model.addAttribute("moderators", moderators.findAll());
    model.addAttribute("guests", guests.findAll());
    model.addAttribute("pendingHotels", hotels.findByPending(true));
    model.addAttribute("pendingManagers", managers.findByPending(true));
    return "dashboards/admin/index";
  }

  @RequestMapping(value="managers", method=RequestMethod.GET)
  public String managers(Model model) {
    //Admin admin = admins.findByName("Jessica Pearson");
    model.addAttribute("managers", managers.findAll());
    return "dashboards/admin/managers/index";
  }

  @RequestMapping(value="managers/{id}", method=RequestMethod.GET)
  public String manager(@PathVariable("id") long id, Model model) {
    // Admin admin = admins.findByName("Jessica Pearson");
    model.addAttribute("manager", managers.findOne(id));
    return "dashboards/admin/managers/show";
  }

  @RequestMapping(value="managers/{id}", method=RequestMethod.DELETE)
  public String deleteManager(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    // Admin admin = admins.findByName("Jessica Pearson");

    if (managers.exists(id)) {
      managers.delete(id);
      redirectAttrs.addFlashAttribute("message", "Manager deleted!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Manager doesn't exist!");
    }
    return "redirect:/dashboards/admin/";
  }

  @RequestMapping(value="managers/{id}/hotels", method=RequestMethod.GET)
  public String managerHotels(@PathVariable("id") long id, Model model) {
    // Admin admin = admins.findByName("Jessica Pearson");
    model.addAttribute("manager", managers.findOne(id));
    model.addAttribute("hotels", managers.findOne(id).getHotels());
    return "dashboards/admin/managers/hotels/index";
  }

    @RequestMapping(value="guests", method=RequestMethod.GET)
  public String guests(Model model) {
    //Admin admin = admins.findByName("Jessica Pearson");
    model.addAttribute("guests", guests.findAll());
    return "dashboards/admin/guests/index";
  }

  @RequestMapping(value="guests/{id}", method=RequestMethod.GET)
  public String guest(@PathVariable("id") long id, Model model) {
    // Admin admin = admins.findByName("Jessica Pearson");
    model.addAttribute("guest", managers.findOne(id));
    return "dashboards/admin/guests/show";
  }

  @RequestMapping(value="guests/{id}", method=RequestMethod.DELETE)
  public String deleteGuest(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    // Admin admin = admins.findByName("Jessica Pearson");

    if (guests.exists(id)) {
      guests.delete(id);
      redirectAttrs.addFlashAttribute("message", "Guest deleted!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Guest doesn't exist!");
    }
    return "redirect:/dashboards/admin/";
  }

  @RequestMapping(value="hotels/{id}/approve", method=RequestMethod.POST)
  public String approveHotel(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      Hotel tmp = hotels.findOne(id);
      tmp.setPending(false);
      hotels.save(tmp);
      redirectAttrs.addFlashAttribute("message", "Hotel approved!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");
    }
    return "redirect:/dashboards/admin";
  }

  @RequestMapping(value="moderators/{id}", method=RequestMethod.DELETE)
  public String deleteModerator(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (moderators.exists(id)) {
      moderators.delete(id);
      redirectAttrs.addFlashAttribute("message", "Moderator deleted!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Moderator doesn't exist!");
    }
    return "redirect:/dashboards/admin/";
  }

  @RequestMapping(value="moderators/{id}/edit",method=RequestMethod.GET)
  public String edit(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (moderators.exists(id)) {
      model.addAttribute("moderator", moderators.findOne(id));

      return "/dashboards/admin/moderatorEdit";
    } else {
      redirectAttrs.addFlashAttribute("error", "Moderator doesn't exist!");

      return "redirect:/dashboards/admin";
    }
  }

  @RequestMapping(value="moderators/{id}",method=RequestMethod.POST)
  public String update(@PathVariable("id") long id, Moderator moderator, Model model, RedirectAttributes redirectAttrs) {
    if (moderators.exists(id)) {
      if (moderator.getId() == id) {
        moderators.save(moderator);
        redirectAttrs.addFlashAttribute("message", "Moderator edited!");
      } else {
        redirectAttrs.addFlashAttribute("error", "Id doesn't match with the given Moderator!");
      }
    } else {
      redirectAttrs.addFlashAttribute("error", "Moderator doesn't exist!");
    }
    return "redirect:/dashboards/admin";
  }

  @RequestMapping(value="moderators/new", method=RequestMethod.GET)
  public String newModerator(Model model) {
    Moderator a = new Moderator();
    model.addAttribute("moderator", a);
    return "/dashboards/admin/moderatorNew";
  }

  @RequestMapping(value="moderators", method=RequestMethod.POST)
  public String createModerator(@ModelAttribute Moderator moderator, Model model, RedirectAttributes redirectAttrs) {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    moderator.setPassword(encoder.encode(moderator.getPassword()));
    moderators.save(moderator);
    redirectAttrs.addFlashAttribute("message", "Moderator created!");
    return "redirect:/dashboards/admin";
  }

  @RequestMapping(value="managers/{id}/approve", method=RequestMethod.POST)
  public String approveManager(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (managers.exists(id)) {
      Manager tmp = managers.findOne(id);
      tmp.setPending(false);
      managers.save(tmp);
      redirectAttrs.addFlashAttribute("message", "Manager approved!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Manager doesn't exist!");
    }
    return "redirect:/dashboards/admin";
  }

}
