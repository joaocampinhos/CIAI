package hotelr.repository;

import hotelr.model.*;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.sql.Timestamp;


public interface HotelRepository extends CrudRepository<Hotel, Long> {

  Hotel findByName(String name);

  @Query("SELECT h FROM Hotel h WHERE h.id NOT IN (SELECT ho.id FROM Booking b join b.hotel ho join b.room r where b.arrival >= :arrival and b.departure <= :departure GROUP BY ho HAVING COUNT(b) >= r.number )")
  List<Hotel> findByAvailability(@Param("arrival") Timestamp arrival, @Param("departure") Timestamp departure);

}

