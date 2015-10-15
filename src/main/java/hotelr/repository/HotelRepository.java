package hotelr.repository;

import hotelr.model.*;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.sql.Timestamp;


public interface HotelRepository extends CrudRepository<Hotel, Long> {

  Hotel findByName(String name);

  //@Query("select h.id from Booking b join b.roomType rt join b.hotel h where b.arrival >= :arrival and b.departure <= :departure and r.number - count(*) > 0 group by (h.id, rt.id)")
  @Query("select h from Booking b join b.hotel h join b.roomType rt join h.rooms r where b.arrival >= :arrival and b.departure <= :departure and r.number - count(b) > 0 group by (h.id, rt.id, r.id)")
  List<Hotel> search(@Param("arrival") Timestamp arrival, @Param("departure") Timestamp departure);

}

