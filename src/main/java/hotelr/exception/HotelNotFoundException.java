package hotelr.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Hotel")  // 404
public class HotelNotFoundException extends RuntimeException {
  /**
   *
   */
  private static final long serialVersionUID = 1L;

}
