package hotelr.repository;

import hotelr.model.*;

import org.springframework.data.repository.CrudRepository;


public interface ManagerRepository extends CrudRepository<Manager, Long> {

  Manager findByName(String name);

}

