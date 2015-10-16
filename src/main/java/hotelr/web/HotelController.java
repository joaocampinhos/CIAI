package hotelr.web;

import hotelr.repository.*;
import hotelr.model.*;
import hotelr.exception.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.beans.factory.annotation.Autowired;

/*
 * Mapping
 * GET  /hotels                           - the list of hotels
 * GET  /hotels/new                       - the form to fill the data for a new hotel
 * POST /hotels                           - creates a new hotel
 * GET  /hotels/{id}                      - the hotel with identifier {id}
 * GET  /hotels/{id}/edit                 - the form to edit the hotel with identifier {id}
 * POST /hotels/{id}                      - update the hotel with identifier {id}
 * POST /hotels/{id}/comments             - creates a new comment for the hotel
 * GET /hotels/{id}/comments              - returns list of comments in the hotel
 * POST /hotels/{id}/comments/{commentId} - creates a new reply for the comment
 * GET /hotels/{id}/comments/{commentId}  - return the reply to the comment
 */

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
  CommentRepository comments;

  @Autowired
  ReplyRepository replies;

  // GET  /hotels             - the list of hotels
  @RequestMapping(method=RequestMethod.GET)
  public String index(Model model) {
    model.addAttribute("hotels", hotels.findAll());
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
  public String show(@PathVariable("id") long id, Model model) {
    Hotel hotel = hotels.findOne(id);
    if( hotel == null )
      throw new HotelNotFoundException();
    model.addAttribute("hotel", hotel );
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

  // POST /hotels/{id}/comments   - creates a new comment for the hotel
  @RequestMapping(value="{id}/comments", method=RequestMethod.POST)
  public String saveComment(@PathVariable("id") long id, @ModelAttribute Comment comment, Model model) {
    //É sempre o Toni a postar
    comment.setGuest(guests.findByName("Toni"));
    comment.setHotel(hotels.findOne(id));
    comments.save(comment);
    model.addAttribute("comment", comment);
    return "redirect:/hotels/{id}";
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







