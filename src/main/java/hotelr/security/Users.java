package hotelr.security;

import java.util.List;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.stereotype.Service;

import hotelr.model.*;
import hotelr.repository.*;

@Service
class Users implements UserDetailsService {

  private GuestRepository guests;
  private ManagerRepository managers;
  private AdminRepository admins;
  private ModeratorRepository moderators;

  @Autowired
  public Users(GuestRepository guests, ManagerRepository managers, AdminRepository admins,  ModeratorRepository moderators) {
    this.guests = guests;
    this.managers = managers;
    this.admins = admins;
    this.moderators = moderators;
  }

  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    User user = guests.findByEmail(username);
    if (user == null) {
      user = managers.findByEmail(username);
      if (user == null) {
        user = admins.findByEmail(username);
        if (user == null) {
          user = moderators.findByEmail(username);
          if (user == null) {
            return null;
          }
        }
      }
    }

    List <GrantedAuthority> auth = new LinkedList<GrantedAuthority>();
    auth.add(new SimpleGrantedAuthority(user.getRole()));
    String password = user.getPassword();

    return new org.springframework.security.core.userdetails.User(username, password, auth);
  }

}