package hotelr.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="GUEST_TABLE")
public class Guest extends User{

  public Guest() {
    super();
  }
  
  public Guest(long id, String name, String email, String password) {
    super(id, name, email, password);
  }
}
