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
  @GeneratedValue(strategy=GenerationType.TABLE)
  @Column(name="ID")
  private long id;

  @Column(name="NAME")
  private String name;

  @Column(name="EMAIL")
  private String email;

  @Column(name="PASSWORD")
  private String password;
  // TODO: Add photo

  @Column(name="ROLE")
  private String role;

  public User(String role) {
    this.role = role;
  }

  public User(long id, String name, String email, String password, String role) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.role = role;
    this.password = password;//BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;//BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public boolean checkPassword(String candidate) {
    return password == candidate;//BCrypt.checkpw(candidate, this.password);
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }

  @Override
  public String toString() {
    return "Id: " + getId() + "\nName: " + getName() + "\nEmail: " + getEmail();
  }
}