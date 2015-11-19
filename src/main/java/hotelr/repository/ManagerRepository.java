package hotelr.repository;

import hotelr.model.*;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface ManagerRepository extends CrudRepository<Manager, Long> {

  Manager findByName(String name);

  List<Manager> findByPending(boolean pending);
}

