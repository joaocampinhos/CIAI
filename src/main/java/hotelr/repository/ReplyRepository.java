package hotelr.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import hotelr.model.*;

public interface ReplyRepository extends CrudRepository<Reply, Long>{

  List<Comment> findByPending(boolean pending);
}
