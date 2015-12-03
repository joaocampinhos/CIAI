package hotelr.web;

import hotelr.model.*;
import hotelr.repository.*;

import org.springframework.beans.factory.annotation.Autowired;

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
@RequestMapping(value="/login")
public class LoginController {
  @Autowired
  GuestRepository guests;

  @RequestMapping(method=RequestMethod.POST)
  public @ResponseBody String login(HttpServletResponse response, @RequestParam("login") String login, @RequestParam("password") String password) throws Exception {
    User user = guests.findByEmail(login);
    if (user == null) {
      return "{ message: { value: \"User doesn't exist.\" , type: \"error\" }}";
    }

    PasswordEncoder encoder = new BCryptPasswordEncoder();
    System.out.println(password + ";" + user.getPassword()   + ";" + encoder.encode(password));
    if (encoder.matches(password, user.getPassword())) {
      Cookie cookie = new Cookie("session", login);
      response.addCookie(cookie);

      return "{ cookie: { name: \"" + cookie.getName() + "\", value: \"" + cookie.getValue() + "\" }, message: { value: \"Login successful.\", type: \"success\"}}";
    } else {
      return "{ message: { value: \"Wrong password.\" , type: \"error\" }}";
    }
  }
}