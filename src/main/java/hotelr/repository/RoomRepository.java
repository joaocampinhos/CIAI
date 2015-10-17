package hotelr.repository;

import hotelr.model.*;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.sql.Timestamp;


public interface RoomRepository extends CrudRepository<Room, Long> {

  @Query("SELECT r FROM Room r WHERE r.hotel = :hotel and r.id NOT IN (SELECT ro.id FROM Booking b join b.hotel h join b.room ro where (:arrival <= b.arrival AND b.arrival <= :departure) OR (:arrival <= b.departure AND b.departure <= :departure) GROUP BY (h, ro.id) HAVING COUNT(b) >= ro.number)")
  List<Room> findByAvailability(@Param("arrival") Timestamp arrival, @Param("departure") Timestamp departure, @Param("hotel") Hotel hotel);

  @Query("FROM Room r WHERE r.hotel = :hotel and r.type = :roomtype and r.id NOT IN (SELECT ro.id FROM Booking b join b.hotel h join b.room ro where ro.type = :roomtype and ((:arrival <= b.arrival AND b.arrival <= :departure) OR (:arrival <= b.departure AND b.departure <= :departure)) GROUP BY (h, ro.id) HAVING COUNT(b) >= ro.number)")
  Room findRoomByAvailability(@Param("arrival") Timestamp arrival, @Param("departure") Timestamp departure, @Param("hotel") Hotel hotel, @Param("roomtype") RoomType roomType);
}

