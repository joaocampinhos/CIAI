package hotelr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hotelr.model.*;

public interface CommentRepository extends CrudRepository<Comment, Long>{

  @Query("SELECT c FROM Comment c LEFT JOIN c.reply r WHERE c.hotel.manager = :manager AND r is null AND c.pending = 'false'")
  List<Comment> findWithNoReply(@Param("manager") Manager manager);

  List<Comment> findByPending(boolean pending);

  List<Comment> findByHotelAndPending(Hotel hotel, boolean pending);
}
