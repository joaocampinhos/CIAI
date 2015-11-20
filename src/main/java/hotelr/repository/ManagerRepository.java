package hotelr.repository;

import hotelr.model.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface ManagerRepository extends UserRepository<Manager> {

  Manager findByName(String name);

  List<Manager> findByPending(boolean pending);

}