package hotelr.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Manager")  // 404
public class ManagerNotFoundException extends RuntimeException {
  /**
   *
   */
  private static final long serialVersionUID = 2L;

}
