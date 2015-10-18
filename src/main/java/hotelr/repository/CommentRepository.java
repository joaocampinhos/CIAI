package hotelr.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hotelr.model.*;

public interface CommentRepository extends CrudRepository<Comment, Long>{

  @Query("SELECT c from Comment c where c.reply is NULL and c.hotel.manager = :manager")
  List<Comment> findWithNoReply(@Param("manager") Manager manager);

}
