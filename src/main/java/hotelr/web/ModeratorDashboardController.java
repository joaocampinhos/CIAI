package hotelr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import hotelr.repository.AdminRepository;
import hotelr.repository.CommentRepository;
import hotelr.repository.GuestRepository;
import hotelr.repository.ReplyRepository;

@Controller
@RequestMapping(value="/dashboards/moderator")
public class ModeratorDashboardController {

  @Autowired
  AdminRepository moderators;

  @Autowired
  CommentRepository comments;

  @Autowired
  ReplyRepository replies;

  @RequestMapping(method=RequestMethod.GET)
  public String index(Model model) {
    model.addAttribute("pendingComments", comments.findByPending(true));
    model.addAttribute("pendingReplies", replies.findByPending(true));
    return "dashboards/moderator/index";
  }
}
