package hotelr.security;

import java.lang.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize(AllowedForReplyCreation.Condition)
public @interface AllowedForReplyCreation {
  String Condition = "@SecurityService.canCreateReply(principal,#id)";
  String Condition2 = "@SecurityService.canCreateReply(principal, #vars.hotel.id)";
}