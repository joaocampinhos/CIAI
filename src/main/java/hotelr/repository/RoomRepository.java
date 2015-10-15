package hotelr.repository;

import hotelr.model.*;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.sql.Timestamp;


public interface RoomRepository extends CrudRepository<Room, Long> {

  @Query("SELECT r FROM Room r WHERE r.hotel = :hotel and r.id NOT IN (SELECT ro.id FROM Booking b join b.hotel h join b.room ro where b.arrival >= :arrival and b.departure <= :departure GROUP BY h HAVING COUNT(b) >= r.number )")
  List<Room> findByAvailability(@Param("arrival") Timestamp arrival, @Param("departure") Timestamp departure, @Param("hotel") Hotel hotel);
}

