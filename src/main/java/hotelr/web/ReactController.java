package hotelr.web;

import hotelr.model.*;
import hotelr.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
@RequestMapping(value="/")
public class ReactController {
  @Autowired
  GuestRepository guests;

  @Autowired
  HotelRepository hotels;

  @Autowired
  BookingRepository bookings;

  @Autowired
  RoomTypeRepository roomTypes;

  List<String> cookies = new LinkedList();

  @RequestMapping(value="/login", method=RequestMethod.POST)
  public @ResponseBody String login(@RequestParam("login") String login, @RequestParam("password") String password) throws Exception {
    User user = guests.findByEmail(login);
    if (user == null) {
      return "{ \"message\": { \"value\": \"User doesn't exist.\" , \"type\": \"error\" }}";
    }

    PasswordEncoder encoder = new BCryptPasswordEncoder();
    if (encoder.matches(password, user.getPassword())) {
      cookies.add(login);

      System.out.println(cookies);
      return "{ \"cookie\": { \"name\": \"session\", \"value\": \"" + login + "\" }, \"message\": { \"value\": \"Login successful.\", \"type\": \"success\"}}";
    } else {
      return "{ \"message\": { \"value\": \"Wrong password.\" , \"type\": \"error\" }}";
    }
  }

  @RequestMapping(value="/logoff", method=RequestMethod.POST)
  public @ResponseBody String logoff(@RequestParam("cookie") String cookieValue) throws Exception {
    System.out.println(cookies);

    if (cookies.remove(cookieValue)) return "{ \"message\": \"Logout successful.\", \"type\": \"success\" }";
    else return "{ \"message\": \"Invalid cookie, could not logout successfully.\", \"type\": \"error\" }";
  }

  @RequestMapping(value="/hotels", method=RequestMethod.GET)
  public @ResponseBody String listHotels(@RequestParam(value="arrival", required=false) String arrival, @RequestParam(value="departure", required=false) String departure, @RequestParam(value="roomtype", required=false) String roomType) throws Exception {

    Iterator<Hotel> hot;
    if (arrival == null || departure == null) {
      hot = hotels.findByPending(false).iterator();
    } else {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      Date dArrival = sdf.parse(arrival);
      Date dDeparture = sdf.parse(departure);

      if (roomType != null) {
        hot = hotels.findByAvailabilityWithRoomType(new Timestamp(dArrival.getTime()), new Timestamp(dDeparture.getTime()), roomTypes.findByName(roomType)).iterator();
      } else {
        hot = hotels.findByAvailability(new Timestamp(dArrival.getTime()), new Timestamp(dDeparture.getTime())).iterator();
      }
    }

    return hotelsToJSON(hot);
  }

  private String hotelsToJSON(Iterator<Hotel> hot) {
    String json = "{ \"hotels\": [";
    while (hot.hasNext()) {
      Hotel h = hot.next();
      if (hot.hasNext()) json += h.toJSON() + ",";
      else json += h.toJSON();
    }

    json += "]}";
    return json;
  }

}
