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
@RequestMapping(value="/hotels")
public class HotelController {

  @Autowired
  HotelRepository hotels;

  @Autowired
  ManagerRepository managers;

  @Autowired
  RoomRepository rooms;

  // GET  /hotels             - the list of hotels
  @RequestMapping(method=RequestMethod.GET)
  public String index(@RequestParam(value="arrival", required=false) String arrival, @RequestParam(value="departure", required=false) String departure, Model model) throws Exception {
    if (arrival == null || departure == null) model.addAttribute("hotels", hotels.findAll());
    else {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date dArrival = sdf.parse(arrival);
      Date dDeparture = sdf.parse(departure);

      List<Hotel> filtered = hotels.findByAvailability(new Timestamp(dArrival.getTime()), new Timestamp(dDeparture.getTime()));
      model.addAttribute("hotels", filtered);
    }

    return "hotels/index";
  }

  // GET  /hotels.json            - the list of hotels
  @RequestMapping(method=RequestMethod.GET, produces={"text/plain","application/json"})
  public @ResponseBody Iterable<Hotel> indexJSON(Model model) {
    return hotels.findAll();
  }

  // GET  /hotels/new         - the form to fill the data for a new hotel
  @RequestMapping(value="/new", method=RequestMethod.GET)
  public String newHotel(Model model) {
    model.addAttribute("hotel", new Hotel());
    return "hotels/create";
  }

  // POST /hotels             - creates a new hotel
  @RequestMapping(method=RequestMethod.POST)
  public String saveIt(@ModelAttribute Hotel hotel, Model model) {
    // TODO: set to the manager current logged in instead of manually assigning this
    hotel.setManager(managers.findByName("O Chefe"));
    hotels.save(hotel);
    model.addAttribute("hotel", hotel);
    return "redirect:/hotels";
  }

  // GET  /hotels/{id}        - the hotel with identifier {id}
  @RequestMapping(value="{id}", method=RequestMethod.GET)
  public String show(@PathVariable("id") long id, @RequestParam(value="arrival", required=false) String arrival, @RequestParam(value="departure", required=false) String departure, Model model) throws Exception {
    Hotel hotel = hotels.findOne(id);
    if( hotel == null )
      throw new HotelNotFoundException();

    if (arrival != null && departure != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date dArrival = sdf.parse(arrival);
      Date dDeparture = sdf.parse(departure);

      List<Room> filtered = rooms.findByAvailability(new Timestamp(dArrival.getTime()), new Timestamp(dDeparture.getTime()), hotel);
      model.addAttribute("rooms", filtered);
    }

    model.addAttribute("hotel", hotel);
    return "hotels/show";
  }

  // GET  /hotels/{id}.json       - the hotel with identifier {id}
  @RequestMapping(value="{id}", method=RequestMethod.GET, produces={"text/plain","application/json"})
  public @ResponseBody Hotel showJSON(@PathVariable("id") long id, Model model) {
    Hotel hotel = hotels.findOne(id);
    if( hotel == null )
      throw new HotelNotFoundException();
    return hotel;
  }

  // GET  /hotels/{id}/edit   - the form to edit the hotel with identifier {id}
  @RequestMapping(value="{id}/edit", method=RequestMethod.GET)
  public String edit(@PathVariable("id") long id, Model model) {
    model.addAttribute("hotel", hotels.findOne(id));
    return "hotels/edit";
  }

  // POST /hotels/{id}        - update the hotel with identifier {id}
  @RequestMapping(value="{id}", method=RequestMethod.POST)
  public String editSave(@PathVariable("id") long id, Hotel hotel, Model model) {
    hotels.save(hotel);
    return "redirect:/";
  }

}







