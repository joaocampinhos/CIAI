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
@RequestMapping(value="/dashboards/manager")
public class ManagerDashboardController {

  @Autowired
  HotelRepository hotels;

  @Autowired
  ManagerRepository managers;

  @Autowired
  CommentRepository comments;

  @RequestMapping(method=RequestMethod.GET)
  public String index(Model model) {
    Manager manager = managers.findByName("O Chefe");
    model.addAttribute("comments", comments.findWithNoReply(manager));
    model.addAttribute("manager", manager);
    return "dashboards/manager/index";
  }

  @RequestMapping(value="hotels/{id}/edit",method=RequestMethod.GET)
  public String edit(@PathVariable("id") long id, Hotel hotel, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      model.addAttribute("hotel", hotels.findOne(id));

      return "dashboards/manager/hotels/edit";
    } else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");

      return "redirect:/dashboards/manager/index";
    }
  }

  @RequestMapping(value="hotels/{id}",method=RequestMethod.POST)
  public String update(@PathVariable("id") long id, Hotel hotel, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      if (hotel.getId() == id) {
        hotels.save(hotel);
        redirectAttrs.addFlashAttribute("message", "Hotel edited!");
      } else {
        redirectAttrs.addFlashAttribute("error", "Id doesn't match with the given hotel!");
      }
    } else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");
    }
    return "redirect:/dashboards/manager/index";
  }

  @RequestMapping(value="hotels/{id}", method=RequestMethod.DELETE)
  public String cancel(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      hotels.delete(id);
      redirectAttrs.addFlashAttribute("message", "Hotel deleted!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");
    }
    return "redirect:/hotels";
  }

}
