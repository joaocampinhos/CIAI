package hotelr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hotelr.model.Comment;
import hotelr.model.Hotel;
import hotelr.model.Reply;
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

  @RequestMapping(value="comments/{id}/approve", method=RequestMethod.POST)
  public String approveComment(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (comments.exists(id)) {
      Comment tmp = comments.findOne(id);
      tmp.setPending(false);
      comments.save(tmp);
      redirectAttrs.addFlashAttribute("message", "Comment approved!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Comment doesn't exist!");
    }
    return "redirect:/dashboards/moderator";
  }

  @RequestMapping(value="comments/{id}", method=RequestMethod.DELETE)
  public String deleteComment(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (comments.exists(id)) {
      comments.delete(id);
      redirectAttrs.addFlashAttribute("message", "Comment deleted!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Comment doesn't exist!");
    }
    return "redirect:/dashboards/moderator/";
  }

  @RequestMapping(value="replies/{id}/approve", method=RequestMethod.POST)
  public String approveReply(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (replies.exists(id)) {
      Reply tmp = replies.findOne(id);
      tmp.setPending(false);
      replies.save(tmp);
      redirectAttrs.addFlashAttribute("message", "Reply approved!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Reply doesn't exist!");
    }
    return "redirect:/dashboards/moderator";
  }

  @RequestMapping(value="replies/{id}", method=RequestMethod.DELETE)
  public String deleteReply(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttrs) {
    if (replies.exists(id)) {
      replies.delete(id);
      redirectAttrs.addFlashAttribute("message", "Reply deleted!");
    } else {
      redirectAttrs.addFlashAttribute("error", "Reply doesn't exist!");
    }
    return "redirect:/dashboards/moderator/";
  }
}
