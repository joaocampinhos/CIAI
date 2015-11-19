package hotelr.security;

import java.lang.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize(AllowedForBookingCreation.Condition)
public @interface AllowedForBookingCreation {
  String Condition = "@SecurityService.managerCanCreateBooking(principal,#id) or hasRole('ROLE_GUEST')";
  String Condition2 = "@SecurityService.managerCanCreateBooking(principal,#vars.hotel.id) or hasRole('ROLE_GUEST')";
}