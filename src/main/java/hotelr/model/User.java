package hotelr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class User {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  private String name;
  private String email;
  private String password;
  // TODO: Add photo

  public User() {}

  public User(long id, String name, String email, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = BCrypt.hashpw(password, BCrypt.gensalt());
  }

  @Id
  @Column(name="ID")
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Column(name="NAME")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name="EMAIL")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name="PASSWORD")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public boolean checkPassword(String candidate) {
    return BCrypt.checkpw(candidate, this.password);
  }

  @Override
  public String toString() {
    return "Id: " + getId() + "\nName: " + getName() + "\nEmail: " + getEmail();
  }
}