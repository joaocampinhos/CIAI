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
  RoomRepository rooms;

  @Autowired
  GuestRepository guests;

  @Autowired
  BookingRepository bookings;

  @Autowired
  ManagerRepository managers;

  @Autowired
  CommentRepository comments;

  @Autowired
  RoomTypeRepository roomTypes;

  @RequestMapping(method=RequestMethod.GET)
  public String index(Model model) {
    Manager manager = managers.findByName("O Chefe");
    model.addAttribute("comments", comments.findWithNoReply(manager));
    model.addAttribute("manager", manager);
    return "dashboards/manager/index";
  }

  @RequestMapping(value="hotels/{id}/edit",method=RequestMethod.GET)
  public String edit(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      model.addAttribute("hotel", hotels.findOne(id));

      return "hotels/edit";
    } else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");

      return "redirect:/dashboards/manager";
    }
  }

  @RequestMapping(value="hotels/new", method=RequestMethod.GET)
  public String newHotel(Model model) {
    model.addAttribute("hotel", new Hotel());
    return "hotels/create";
  }

  @RequestMapping(value="hotels", method=RequestMethod.POST)
  public String createHotel(@ModelAttribute Hotel hotel, Model model, RedirectAttributes redirectAttrs) {
    hotel.setManager(managers.findByName("O Chefe"));
    hotels.save(hotel);
    redirectAttrs.addFlashAttribute("message", "Hotel created!");
    return "redirect:/dashboards/manager";
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
    return "redirect:/dashboards/manager";
  }

  @RequestMapping(value="hotels/{id}/rooms/new", method=RequestMethod.GET)
  public String newRoom(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      List<RoomType> listTypes = roomTypes.findTypesNotInHotel(hotels.findOne(id));

      if (!listTypes.isEmpty()) {
        model.addAttribute("hotel", hotels.findOne(id));
        model.addAttribute("room", new Room());
        model.addAttribute("types", listTypes);
        return "rooms/create";
      } else {
        redirectAttrs.addFlashAttribute("error", "Hotel already has room collections for all the room types!");
        return "redirect:/dashboards/manager";
      }
    } else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");
      return "redirect:/dashboards/manager";
    }
  }

  @RequestMapping(value="hotels/{id}/rooms", method=RequestMethod.POST)
  public String createRoom(@PathVariable("id") long id, @ModelAttribute Room room, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      room.setHotel(hotels.findOne(id));
      rooms.save(room);
      System.out.println(room.getType());
      redirectAttrs.addFlashAttribute("message", "Hotel created!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");
    }
    return "redirect:/dashboards/manager";
  }

  @RequestMapping(value="hotels/{id}/rooms/{roomid}/edit",method=RequestMethod.GET)
  public String editRoom(@PathVariable("id") long id, @PathVariable("roomid") long roomid, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      if (rooms.exists(roomid)) {
        Room room = rooms.findOne(roomid);
        if (room.getHotel().getId() == id) {
          model.addAttribute("room", room);

          return "rooms/edit";
        } else {
          redirectAttrs.addFlashAttribute("error", "Room doesn't belong to this hotel!");
        }
      } else {
        redirectAttrs.addFlashAttribute("error", "Room doesn't exist!");
      }
    } else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");
    }

    return "redirect:/dashboards/manager";
  }

  @RequestMapping(value="hotels/{id}/rooms/{roomid}",method=RequestMethod.POST)
  public String updateRoom(@PathVariable("id") long id, @PathVariable("roomid") long roomid, Room room, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      if (rooms.exists(roomid)) {
        if (room.getId() == roomid) {
          Room oldRoom = rooms.findOne(roomid);

          if (oldRoom.getNumber() <= room.getNumber()) {
            rooms.save(room);
            redirectAttrs.addFlashAttribute("message", "Room edited!");
          } else {
            redirectAttrs.addFlashAttribute("error", "You cannot lower the number of rooms!");
          }
        } else {
          redirectAttrs.addFlashAttribute("error", "Id doesn't match with the given room!");
        }
      } else {
        redirectAttrs.addFlashAttribute("error", "Room doesn't exist!");
      }
    } else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");
    }
    return "redirect:/dashboards/manager";
  }

  @RequestMapping(value="hotels/{id}", method=RequestMethod.DELETE)
  public String deleteHotel(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      hotels.delete(id);
      redirectAttrs.addFlashAttribute("message", "Hotel deleted!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");
    }
    return "redirect:/dashboards/manager";
  }

  @RequestMapping(value="bookings/{id}", method=RequestMethod.DELETE)
  public String cancel(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (bookings.exists(id)) {
      bookings.delete(id);
      redirectAttrs.addFlashAttribute("message", "Booking deleted!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Booking doesn't exist!");
    }
    return "redirect:/dashboards/manager";
  }

  @RequestMapping(value="bookings/new",method=RequestMethod.GET)
  public String newBooking(Model model, RedirectAttributes redirectAttrs) {
    Manager manager = managers.findByName("O Chefe");
    model.addAttribute("manager", manager);
    model.addAttribute("guests", guests.findAll());
    model.addAttribute("roomtype", roomTypes.findAll());
    return "dashboards/manager/bookingnew";
  }

  @RequestMapping(value="bookings",method=RequestMethod.POST)
  public String update(@RequestParam("hotel") long hotelid, @RequestParam("guest") long guestid, @RequestParam("arrival") String arrival, @RequestParam("departure") String departure, @RequestParam("roomtype") RoomType roomType, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(hotelid)) {
      Hotel hotel = hotels.findOne(hotelid);

      if (guests.exists(guestid)) {
        Guest guest = guests.findOne(guestid);
        Room room = rooms.findByHotelAndType(hotel, roomType);

        if (room != null) {
          try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dArrival = sdf.parse(arrival);
            Date dDeparture = sdf.parse(departure);

            Timestamp tArrival = new Timestamp(dArrival.getTime());
            Timestamp tDeparture = new Timestamp(dDeparture.getTime());

            if (tArrival.before(tDeparture)){
              Booking booking = new Booking(tArrival, tDeparture, room.getType(), room, hotel, guest);
              bookings.save(booking);

              redirectAttrs.addFlashAttribute("message", "Booking created!");
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
        redirectAttrs.addFlashAttribute("error", "Guest doesn't exist!");
      }

    } else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");
    }

    return "redirect:/dashboards/manager";
  }

}
