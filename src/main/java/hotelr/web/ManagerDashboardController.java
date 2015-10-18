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
  ManagerRepository managers;

  @Autowired
  CommentRepository comments;

  @RequestMapping(method=RequestMethod.GET)
  public String index(Model model) {
    Manager manager = managers.findByName("O Chefe");
    model.addAttribute("comments", comments.findWithNoReply(manager));
    System.out.println(comments.findWithNoReply(manager));
    model.addAttribute("manager", manager);
    return "dashboards/manager/index";
  }

}
