package hotelr.repository;

import hotelr.model.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ManagerRepository extends UserRepository<Manager> {}