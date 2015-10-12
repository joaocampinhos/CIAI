package hotelr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ROOM_TABLE")
public class Room {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  private Hotel hotel;
  private RoomType type;
  private int number;
  
  public Room() {}

  public Room(long id, Hotel hotel, RoomType type, int number) {
    this.id = id;
    this.hotel = hotel;
    this.type = type;
    this.number = number;
  }
  
  @Id
  @Column(name="ROOM_ID")
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  @ManyToOne()
  @JoinColumn(name="HOTEL_ID")
  public Hotel getHotel() {
    return hotel;
  }
  
  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }
  
  @ManyToOne
  @JoinColumn(name="ROOM_TYPE_ID")
  public RoomType getType() {
    return type;
  }
  
  public void setType(RoomType type) {
    this.type = type;
  }
  
  @Column(name="ROOM_NUMBER")
  public int getNumber() {
    return number;
  }
  
  public void setNumber(int number) {
    this.number = number;
  }
  
  public String toString() {
    return "Id: " + id + "\nHotel: " + hotel.getName() + "\nType: " + type.getName() + "\nNumber: " + getNumber();
  }
}
