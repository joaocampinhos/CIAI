package hotelr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ROOM_TYPE_TABLE")
public class RoomType {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="ROOM_TYPE_ID")
  private long id;

  @Column(name="ROOM_TYPE_NAME")
  private String name;

  public RoomType() {}

  public RoomType(long id, String name) {
    this.id = id;
    this.name = name;
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

  @Override
  public String toString() {
    return "Id: " + getId() + "\nName: " + getName();
  }

  public String toJSON() {
    return "{ \"id\": " + this.id + ", \"name\": \"" + this.name + "\" }";
  }
}


