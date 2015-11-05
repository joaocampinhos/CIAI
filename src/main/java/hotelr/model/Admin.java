package hotelr.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ADMIN_TABLE")
public class Admin extends User {

  public Admin() {
    super("ADMIN");
  }

  public Admin(long id, String name, String email, String password) {
    super(id, name, email, password, "ADMIN");
  }
}