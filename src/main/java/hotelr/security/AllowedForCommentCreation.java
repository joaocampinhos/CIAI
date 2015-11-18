package hotelr.security;

import java.lang.annotation.*;

import org.springframework.security.access.prepost.PreAuthorize;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize(AllowedForCommentCreation.Condition)
public @interface AllowedForCommentCreation {
  String Condition = "hasRole('ROLE_GUEST')";
}