package hotelr.repository;

import hotelr.model.*;

import org.springframework.data.repository.CrudRepository;


public interface ModeratorRepository extends CrudRepository<Moderator, Long> {

  Moderator findByName(String name);

}

