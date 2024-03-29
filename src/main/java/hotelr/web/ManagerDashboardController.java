package hotelr.web;

import hotelr.repository.*;
import hotelr.model.*;
import hotelr.exception.*;
import hotelr.security.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.security.Principal;

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
  public String index(Model model, Principal principal, RedirectAttributes redirectAttrs) {
    if(managers.exists(managers.findByEmail(principal.getName()).getId())) {
      Manager manager = managers.findByEmail(principal.getName());
      if(manager.getPending() == false) {
        model.addAttribute("comments", comments.findWithNoReply(manager));
        model.addAttribute("manager", manager);
        return "dashboards/manager/index";
      } else {
        redirectAttrs.addFlashAttribute("error", "Manager has not been approved yet!");
        return "redirect:/";
      }
    } else {
      redirectAttrs.addFlashAttribute("error", "Manager doesn't exist!");
      return "redirect:/";
    }
  }

  @AllowedForEditOrDeleteHotel
  @RequestMapping(value="hotels/{id}/occupancy",method=RequestMethod.GET, produces={"text/plain","application/json"})
  public @ResponseBody String occupancy(@PathVariable("id") long id, @RequestParam("arrival") String arrival, @RequestParam("departure") String departure) throws Exception {
    Hotel hotel = hotels.findOne(id);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date dArrival = sdf.parse(arrival);
    Date dDeparture = sdf.parse(departure);

    int occupied = bookings.countBookingsGivenDate(hotel, new Timestamp(dArrival.getTime()), new Timestamp(dDeparture.getTime()));
    int total = rooms.countRooms(hotel);

    double result = ((double) occupied / (double) total) * 100.0;

    return "{\"occupancy\":" + result + "}";
  }

  @AllowedForEditOrDeleteHotel
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
  public String newHotel(Model model, RedirectAttributes redirectAttrs, Principal principal) {
    Manager manager = managers.findByEmail(principal.getName());
    if(manager.getPending() == false){
      Hotel a = new Hotel();
      model.addAttribute("hotel", a);
      return "hotels/create";
    } else {
      redirectAttrs.addFlashAttribute("error", "Manager has not been approved yet!");
      return "redirect:/";
    }
  }

  @RequestMapping(value="hotels", method=RequestMethod.POST)
  public String createHotel(@ModelAttribute Hotel hotel, Model model, Principal principal, RedirectAttributes redirectAttrs) {
    Manager manager = managers.findByEmail(principal.getName());
    if(manager.getPending() == false){
      hotel.setManager(manager);
      hotel.setPending(true);
      hotels.save(hotel);
      redirectAttrs.addFlashAttribute("message", "Hotel created!");
      return "redirect:/dashboards/manager/hotels/"+hotel.getId()+"/rooms/new";
    } else {
      redirectAttrs.addFlashAttribute("error", "Manager has not been approved yet!");
      return "redirect:/";
    }
  }

  @RequestMapping(value="hotels/{id}",method=RequestMethod.POST)
  @AllowedForEditOrDeleteHotel
  public String update(@PathVariable("id") long id, Hotel hotel, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      if (hotel.getId() == id) {
        hotel.setPending(true);
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
  @AllowedForEditOrDeleteHotel
  public String newRoom(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      List<RoomType> listTypes = roomTypes.findTypesNotInHotel(hotels.findOne(id));

        model.addAttribute("hotel", hotels.findOne(id));
          model.addAttribute("room", new Room());
        model.addAttribute("types", listTypes);
        return "rooms/create";
      }
     else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");
      return "redirect:/dashboards/manager";
    }
  }

  @RequestMapping(value="hotels/{id}/rooms", method=RequestMethod.POST)
  @AllowedForEditOrDeleteHotel
  public String createRoom(@PathVariable("id") long id, @ModelAttribute Room room, @RequestParam("newtype") String newtype, Model model, RedirectAttributes redirectAttrs) {
    if (hotels.exists(id)) {
      room.setHotel(hotels.findOne(id));
      if (!newtype.equals("")) {
        RoomType type = new RoomType();
        type.setName(newtype);
        roomTypes.save(type);
        room.setType(type);
        rooms.save(room);
        redirectAttrs.addFlashAttribute("message", "Room created!");
      }
      else {
        if (room.getType() != null) {
          rooms.save(room);
          redirectAttrs.addFlashAttribute("message", "Room created!");
        }
        else {
          redirectAttrs.addFlashAttribute("error", "You need to provide a valid Room Type");
        }
      }
    }
    else {
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist!");
    }
    return "redirect:/dashboards/manager/hotels/"+id+"/rooms/new";
  }

  @RequestMapping(value="hotels/{id}/rooms/{roomid}/edit",method=RequestMethod.GET)
  @AllowedForEditOrDeleteHotel
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
  @AllowedForEditOrDeleteHotel
  public String updateRoom(@PathVariable("id") long id, @PathVariable("roomid") long roomid, Room room, Model model, RedirectAttributes redirectAttrs) {
      if (hotels.exists(id)) {
      if (rooms.exists(roomid)) {
        if (room.getId() == roomid) {
          Room oldRoom = rooms.findOne(roomid);

          if (oldRoom.getNumber() <= room.getNumber()) {
            room.setHotel(hotels.findOne(id));
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
  @AllowedForEditOrDeleteHotel
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
  @AllowedForEditOrDeleteHotel
  public String cancel(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (bookings.exists(id)) {
      bookings.delete(id);
      redirectAttrs.addFlashAttribute("message", "Booking deleted!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Booking doesn't exist!");
    }
    return "redirect:/dashboards/manager";
  }

  @RequestMapping(value="bookings/{id}/approve", method=RequestMethod.POST)
  @AllowedForEditOrDeleteHotel
  public String approveBooking(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (bookings.exists(id)) {
      Booking tmp = bookings.findOne(id);
      tmp.setPending(false);
      bookings.save(tmp);
      redirectAttrs.addFlashAttribute("message", "Booking approved!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Booking doesn't exist!");
    }
    return "redirect:/dashboards/manager";
  }

  @RequestMapping(value="bookings/new",method=RequestMethod.GET)
  public String newBooking(Model model, Principal principal, RedirectAttributes redirectAttrs) {
    Manager manager = managers.findByEmail(principal.getName());
    model.addAttribute("manager", manager);
    model.addAttribute("guests", guests.findAll());
    model.addAttribute("roomtype", roomTypes.findAll());
    return "dashboards/manager/bookingnew";
  }

  @RequestMapping(value="bookings",method=RequestMethod.POST)
  public String update(@RequestParam("hotel") long hotelid, @RequestParam("guest") long guestid, @RequestParam("arrival") String arrival, @RequestParam("departure") String departure, @RequestParam("roomtype") long roomType, Model model, RedirectAttributes redirectAttrs, Principal principal) {
    if (hotels.exists(hotelid) && hotels.findOne(hotelid).getManager().getEmail().equals(principal.getName())) {
      Hotel hotel = hotels.findOne(hotelid);

      if (guests.exists(guestid)) {
        Guest guest = guests.findOne(guestid);
        Room room = rooms.findOne(roomType);

        if (room != null) {
          try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dArrival = sdf.parse(arrival);
            Date dDeparture = sdf.parse(departure);

            Timestamp tArrival = new Timestamp(dArrival.getTime());
            Timestamp tDeparture = new Timestamp(dDeparture.getTime());

            if (tArrival.before(tDeparture)){
              Booking booking = new Booking(tArrival, tDeparture, room.getType(), room, hotel, guest, false);
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
      redirectAttrs.addFlashAttribute("error", "Hotel doesn't exist or you don't manage it!");
    }

    return "redirect:/dashboards/manager";
  }

}
