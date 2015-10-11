package hotelr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Table(name="MANAGER_TABLE")
public class Manager {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  private String name;
  private String email;
  private String password;
  // TODO: Add photo

  public Manager() {}

  public Manager(long id, String name, String email, String password) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = BCrypt.hashpw(password, BCrypt.gensalt());
  }

  @Id
  @Column(name="MANAGER_ID")
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Column(name="MANAGER_NAME")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name="MANAGER_EMAIL")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name="MANAGER_PASSWORD")
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