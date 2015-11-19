package hotelr.security;

import java.lang.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize(AllowedForEditBooking.Condition)
public @interface AllowedForEditBooking {
  String Condition = "@SecurityService.canEditBooking(principal,#id)";
  String Condition2 = "@SecurityService.canEditBooking(principal, #vars.booking.id)";
}