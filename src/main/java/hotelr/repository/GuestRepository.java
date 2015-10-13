package hotelr.repository;

import hotelr.model.*;

import org.springframework.data.repository.CrudRepository;


public interface GuestRepository extends CrudRepository<Guest, Long> {

  Guest findByName(String name);

}

