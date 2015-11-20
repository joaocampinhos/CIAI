package hotelr.security;

import java.lang.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize(AllowedForEditOrDeleteHotel.Condition)
public @interface AllowedForEditOrDeleteHotel {
  String Condition = "@SecurityService.canEditOrDeleteHotel(principal,#id)";
  String Condition2 = "@SecurityService.canEditOrDeleteHotel(principal,#vars.hotel.id)";
}