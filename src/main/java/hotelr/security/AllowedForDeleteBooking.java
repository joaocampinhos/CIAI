package hotelr.security;

import java.lang.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize(AllowedForDeleteBooking.Condition)
public @interface AllowedForDeleteBooking {
  String Condition = "@SecurityService.canDeleteBooking(principal,#id)";
  String Condition2 = "@SecurityService.canDeleteBooking(principal,#vars.booking.id)";
}