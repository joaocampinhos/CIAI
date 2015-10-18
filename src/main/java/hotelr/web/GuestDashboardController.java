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

  @Autowired
  RoomRepository rooms;

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
      redirectAttrs.addFlashAttribute("error", "Booking doesn't exist!");
    }
    return "redirect:/dashboards/guest";
  }

  @RequestMapping(value="bookings/{id}/edit", method=RequestMethod.GET)
  public String edit(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (bookings.exists(id)) {
      Booking booking = bookings.findOne(id);
      model.addAttribute("booking", booking);

      return "dashboards/guest/bookings/edit";
    } else {
      redirectAttrs.addFlashAttribute("error", "Booking doesn't exist!");

      return "redirect:/dashboards/guest";
    }
  }

  @RequestMapping(value="bookings/{id}", method=RequestMethod.POST)
  public String edit(@PathVariable("id") long id, @RequestParam("arrival") String arrival, @RequestParam("departure") String departure, @RequestParam("roomtype") long roomid, Model model, RedirectAttributes redirectAttrs) throws Exception {
    if (bookings.exists(id)) {
      Booking current = bookings.findOne(id);

      if (rooms.exists(roomid)) {
        Room room = rooms.findOne(roomid);
        Hotel hotel = current.getHotel();

        try {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          Date dArrival = sdf.parse(arrival);
          Date dDeparture = sdf.parse(departure);

          Timestamp tArrival = new Timestamp(dArrival.getTime());
          Timestamp tDeparture = new Timestamp(dDeparture.getTime());

          if (tArrival.before(tDeparture)) {
            Room exists = rooms.findRoomByAvailability(tArrival, tDeparture, hotel, room.getType());

            if (exists != null) {
              current.setArrival(tArrival);
              current.setDeparture(tDeparture);
              current.setRoom(room);
              current.setRoomType(room.getType());

              redirectAttrs.addFlashAttribute("message", "Booking edited!");
            } else {
              redirectAttrs.addFlashAttribute("error", "Room not available for this time interval!");
            }
          } else {
            redirectAttrs.addFlashAttribute("error", "Arrival date has to be before departure!");
          }
        } catch (Exception e) {
          redirectAttrs.addFlashAttribute("error", "Dates are incorrect!");
        }
      } else {
        redirectAttrs.addFlashAttribute("error", "Room doesn't exist!");
      }

    } else {
      redirectAttrs.addFlashAttribute("error", "Booking doesn't exist!");
    }
    return "redirect:/dashboards/guest";
  }

}







