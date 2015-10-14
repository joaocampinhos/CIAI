package hotelr.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="MODERATOR_TABLE")
public class Moderator extends User {

  public Moderator() {
    super();
  }

  public Moderator(long id, String name, String email, String password) {
    super(id, name, email, password);
  }
}