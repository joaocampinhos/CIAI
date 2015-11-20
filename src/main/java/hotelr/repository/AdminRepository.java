package hotelr.repository;

import hotelr.model.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminRepository extends UserRepository<Admin> {}

