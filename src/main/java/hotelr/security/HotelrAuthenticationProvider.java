package hotelr.security;

public class HotelrAuthenticationProvider implements AuthenticationManager {

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {

    UserService service = new UserService();

    User user = service.login((String) auth.getPrincipal(), (String) auth.getCredentials());

    LinkedList<GrantedAuthority> authorities = new LinkedList<>();

    if (user != null) {
      authorities.add(new SimpleGrantedAuthority(user.getRole()));

      return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
    }
    return null;
  }
}