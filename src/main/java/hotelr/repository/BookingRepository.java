package hotelr.repository;

import hotelr.model.*;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.sql.Timestamp;


public interface BookingRepository extends CrudRepository<Booking, Long> {

  @Query("SELECT b FROM Booking b join b.hotel where h = :hotel AND ((:arrival <= b.arrival AND b.arrival <= :departure) OR (:arrival <= b.departure AND b.departure <= :departure))")
  List<Booking> findOccupation(@Param("arrival") Timestamp arrival, @Param("departure") Timestamp departure, @Param("hotel") Hotel hotel);
}

