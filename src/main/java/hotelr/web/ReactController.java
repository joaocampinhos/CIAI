package hotelr.web;

import hotelr.model.*;
import hotelr.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.LinkedList;

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
}
