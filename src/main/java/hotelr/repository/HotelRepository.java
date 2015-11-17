package hotelr.repository;

import hotelr.model.*;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.sql.Timestamp;


public interface HotelRepository extends CrudRepository<Hotel, Long> {

  Hotel findByName(String name);

  @Query("SELECT h FROM Hotel h WHERE h.pending = 'false' AND h.id NOT IN (SELECT ho.id FROM Booking b join b.hotel ho join b.room r where (:arrival <= b.arrival AND b.arrival <= :departure) OR (:arrival <= b.departure AND b.departure <= :departure) GROUP BY ho HAVING COUNT(b) >= (SELECT SUM(r.number) FROM ho.rooms r))")
  List<Hotel> findByAvailability(@Param("arrival") Timestamp arrival, @Param("departure") Timestamp departure);

  @Query("SELECT h FROM Hotel h join h.rooms r WHERE h.pending = 'false' AND r.type = :roomtype and h.id NOT IN (SELECT ho.id FROM Booking b join b.hotel ho join b.room r where r.type = :roomtype and ((:arrival <= b.arrival AND b.arrival <= :departure) OR (:arrival <= b.departure AND b.departure <= :departure)) GROUP BY ho HAVING COUNT(b) >= r.number)")
  List<Hotel> findByAvailabilityWithRoomType(@Param("arrival") Timestamp arrival, @Param("departure") Timestamp departure, @Param("roomtype") RoomType roomType);
  
  List<Hotel> findByPending(boolean pending);
}
