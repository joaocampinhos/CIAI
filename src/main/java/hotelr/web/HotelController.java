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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping(value="/hotels")
public class HotelController {

  @Autowired
  HotelRepository hotels;

  @Autowired
  ManagerRepository managers;

  @Autowired
  GuestRepository guests;

  @Autowired
  RoomRepository rooms;

  @Autowired
  BookingRepository bookings;

  @Autowired
  RoomTypeRepository roomTypes;

  @Autowired
  CommentRepository comments;

  @Autowired
  ReplyRepository replies;

  // GET  /hotels             - the list of hotels
  @RequestMapping(method=RequestMethod.GET)
  public String index(@RequestParam(value="arrival", required=false) String arrival, @RequestParam(value="departure", required=false) String departure, @RequestParam(value="roomtype", required=false) String roomType, Model model) throws Exception {
    if (arrival == null || departure == null) model.addAttribute("hotels", hotels.findAll());
    else {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date dArrival = sdf.parse(arrival);
      Date dDeparture = sdf.parse(departure);

      List<Hotel> filtered;
      if (roomType != null) {
        filtered = hotels.findByAvailabilityWithRoomType(new Timestamp(dArrival.getTime()), new Timestamp(dDeparture.getTime()), roomTypes.findByName(roomType));
      } else {
        filtered = hotels.findByAvailability(new Timestamp(dArrival.getTime()), new Timestamp(dDeparture.getTime()));
      }

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
  public String show(@PathVariable("id") long id, @RequestParam(value="arrival", required=false) String arrival, @RequestParam(value="departure", required=false) String departure, @RequestParam(value="roomtype", required=false) String roomType, Model model) throws Exception {
    Hotel hotel = hotels.findOne(id);
    if( hotel == null )
      throw new HotelNotFoundException();

    if (arrival != null && departure != null) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date dArrival = sdf.parse(arrival);
      Date dDeparture = sdf.parse(departure);

      if (roomType != null) {
        Room filtered = rooms.findRoomByAvailability(new Timestamp(dArrival.getTime()), new Timestamp(dDeparture.getTime()), hotel, roomTypes.findByName(roomType));
        model.addAttribute("rooms", filtered);
      } else {
        List<Room> filtered = rooms.findByAvailability(new Timestamp(dArrival.getTime()), new Timestamp(dDeparture.getTime()), hotel);
        model.addAttribute("rooms", filtered);
      }
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

  // POST /hotels/{id}/bookings    - creates a new booking
  @RequestMapping(value="{id}/bookings", method=RequestMethod.POST)
  public String bookIt(@PathVariable("id") long id, @RequestParam("arrival") String arrival, @RequestParam("departure") String departure, @RequestParam("roomtype") Long roomid, Model model) throws Exception {
    Guest guest = guests.findByName("Harvey Specter");
    Hotel hotel = hotels.findOne(id);
    Room room = rooms.findOne(roomid);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date dArrival = sdf.parse(arrival);
    Date dDeparture = sdf.parse(departure);

    Booking booking = new Booking(new Timestamp(dArrival.getTime()), new Timestamp(dDeparture.getTime()), room.getType(), room, hotel, guest);;
    bookings.save(booking);

    return "redirect:/hotels/" + id;
  }

  // POST /hotels/{id}/comments   - creates a new comment for the hotel
  @RequestMapping(value="{id}/comments", method=RequestMethod.POST)
  public String saveComment(@PathVariable("id") long id, @RequestParam("comment") String comment, Model model) {
    //É sempre o Toni a postar
    Guest guest = guests.findByName("Harvey Specter");
    Hotel hotel = hotels.findOne(id);
    Comment commentObj = new Comment(guest, "cenas", new Timestamp(System.currentTimeMillis()), hotel);
    comments.save(commentObj);
    return "redirect:/hotels/{id}";
  }

  // GET /hotels/{id}/comments              - returns list of comments in the hotel
  @RequestMapping(value="{id}/comments", method=RequestMethod.GET, produces={"text/plain","application/json"})
  public @ResponseBody Iterable<String> commentsJSON(@PathVariable("id") long id, Model model) {
    LinkedList<String> tmp = new LinkedList<String>();
      Iterator<Comment> it= comments.findByHotel(hotels.findOne(id)).iterator();
      while(it.hasNext()){
        tmp.add(it.next().getComment());
      }
    return tmp;
  }


  // POST /hotels/{id}/comments/{commentId} - creates a new reply for the comment
  @RequestMapping(value="{id}/comments/{commentId}")
  public String saveReply(@PathVariable("id") long id, @PathVariable("commentId") long commentId, Reply reply, Model model){
    //É sempre O Chefe a responder
    reply.setManager(managers.findByName("O Chefe"));
    reply.setParent(comments.findOne(commentId));
    replies.save(reply);
    model.addAttribute("reply", reply);
    return "redirect:/hotels/{id}";
  }

}







