package hotelr.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="MANAGER_TABLE")
public class Manager extends User {

  public Manager() {
    super();
  }

  public Manager(long id, String name, String email, String password) {
    super(id, name, email, password);
  }
}