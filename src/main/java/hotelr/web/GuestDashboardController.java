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

import org.springframework.beans.factory.annotation.Autowired;

/*
 * Mapping
 * GET  /hotels             - the list of hotels
 * GET  /hotels/new         - the form to fill the data for a new hotel
 * POST /hotels             - creates a new hotel
 * GET  /hotels/{id}        - the hotel with identifier {id}
 * GET  /hotels/{id}/edit   - the form to edit the hotel with identifier {id}
 * POST /hotels/{id}        - update the hotel with identifier {id}
 */

@Controller
@RequestMapping(value="/dashboards/guest")
public class GuestDashboardController {
  @Autowired
  GuestRepository guests;

  @RequestMapping(value="bookings", method=RequestMethod.GET)
  public String bookings(Model model) {
    Guest guest = guests.findByName("Harvey Specter");
    model.addAttribute("bookings", guest.getBookings());
    return "dashboards/guest/bookings";
  }

}







