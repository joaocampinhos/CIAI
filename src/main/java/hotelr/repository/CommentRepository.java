package hotelr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hotelr.model.*;

public interface CommentRepository extends CrudRepository<Comment, Long>{

  @Query("SELECT r FROM Comment r where r.hotel = ?1")
  List<Comment> findByHotel(Hotel id);
}
