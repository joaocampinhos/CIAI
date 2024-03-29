package hotelr.repository;

import hotelr.model.*;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.sql.Timestamp;


public interface BookingRepository extends CrudRepository<Booking, Long> {

  @Query("SELECT b FROM Booking b join b.hotel where b.hotel = :hotel AND ((:arrival <= b.arrival AND b.arrival <= :departure) OR (:arrival <= b.departure AND b.departure <= :departure))")
  List<Booking> findOccupation(@Param("arrival") Timestamp arrival, @Param("departure") Timestamp departure, @Param("hotel") Hotel hotel);

  @Query("SELECT b FROM Booking b join b.hotel where b.hotel = :hotel AND b.roomType = :roomtype AND ((:arrival <= b.arrival AND b.arrival <= :departure) OR (:arrival <= b.departure AND b.departure <= :departure))")
  List<Booking> findOccupationWithType(@Param("arrival") Timestamp arrival, @Param("departure") Timestamp departure, @Param("hotel") Hotel hotel, @Param("roomtype") RoomType roomType);

  @Query("SELECT COUNT(b) FROM Booking b join b.hotel where b.hotel = :hotel AND b.pending = false AND ((:begin <= b.arrival AND b.arrival < :end) OR (:begin < b.departure AND b.departure <= :end))")
  int countBookingsGivenDate(@Param("hotel") Hotel hotel, @Param("begin") Timestamp begin, @Param("end") Timestamp end);
}

