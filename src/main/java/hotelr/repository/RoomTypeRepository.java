package hotelr.repository;

import hotelr.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoomTypeRepository extends CrudRepository<RoomType, Long> {

  RoomType findByName(String name);

  @Query("SELECT rt FROM RoomType rt WHERE rt.id NOT IN (SELECT t.id FROM Hotel h JOIN h.rooms r JOIN r.type t WHERE h = :hotel)")
  List<RoomType> findTypesNotInHotel(@Param("hotel") Hotel hotel);
}

