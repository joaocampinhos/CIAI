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
@RequestMapping(value="/dashboards/admin")
public class AdminDashboardController {
  @Autowired
  AdminRepository admins;

  @Autowired
  ManagerRepository managers;

  @Autowired
  BookingRepository bookings;

  @RequestMapping(method=RequestMethod.GET)
  public String index(Model model) {
    // Admin admin = admins.findByName("Jessica Pearson");
    model.addAttribute("managers", managers.findAll());
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

  @RequestMapping(value="managers/{id}/hotels", method=RequestMethod.GET)
  public String managerHotels(@PathVariable("id") long id, Model model) {
    // Admin admin = admins.findByName("Jessica Pearson");
    model.addAttribute("manager", managers.findOne(id));
    model.addAttribute("hotels", managers.findOne(id).getHotels());
    return "dashboards/admin/managers/hotels/index";
  }

}