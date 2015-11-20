package hotelr.repository;

import hotelr.model.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository<T extends User> extends CrudRepository<T, Long> {

  T findByName(String name);
  T findByEmail(String email);

}

