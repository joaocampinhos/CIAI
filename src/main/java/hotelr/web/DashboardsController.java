package hotelr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Map;

@Controller
@RequestMapping(value="/dashboards")
public class DashboardsController {
  @RequestMapping(method=RequestMethod.GET)
  public String index(HttpServletRequest request, RedirectAttributes redirectAttrs) {
    Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
    if (flashMap != null && flashMap.containsKey("error")) {
      redirectAttrs.addFlashAttribute("error", (String) flashMap.get("error"));
    }
    if (request.isUserInRole("ROLE_GUEST")) return "redirect:/dashboards/guest";
    else if (request.isUserInRole("ROLE_MANAGER")) return "redirect:/dashboards/manager";
    else if (request.isUserInRole("ROLE_ADMIN")) return "redirect:/dashboards/admin";
    else if (request.isUserInRole("ROLE_MODERATOR")) return "redirect:/dashboards/moderator";
    return "redirect:/";
  }
}