package hotelr.repository;

import hotelr.model.*;

import org.springframework.data.repository.CrudRepository;


public interface AdminRepository extends CrudRepository<Admin, Long> {

  Admin findByName(String name);

}

