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

@Controller
@RequestMapping(value="/dashboards/guest")
public class GuestDashboardController {
  @Autowired
  GuestRepository guests;

  @Autowired
  BookingRepository bookings;

  @RequestMapping(method=RequestMethod.GET)
  public String index(Model model) {
    Guest guest = guests.findByName("Harvey Specter");
    model.addAttribute("bookings", guest.getBookings());
    return "dashboards/guest/index";
  }

  @RequestMapping(value="bookings", method=RequestMethod.GET)
  public String bookings(Model model) {
    Guest guest = guests.findByName("Harvey Specter");
    model.addAttribute("bookings", guest.getBookings());
    return "dashboards/guest/bookings";
  }

  @RequestMapping(value="bookings/{id}", method=RequestMethod.DELETE)
  public String cancel(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (bookings.exists(id)) {
      bookings.delete(id);
      redirectAttrs.addFlashAttribute("message", "Booking deleted!");
    } else {
      redirectAttrs.addFlashAttribute("message", "Booking doesn't exist!");
    }
    return "redirect:dashboards/guest/bookings";
  }

}







