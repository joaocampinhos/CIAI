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
  @Column(name="ROOM_ID")
  private long id;

  @ManyToOne
  @JoinColumn(name="HOTEL_ID")
  private Hotel hotel;

  @ManyToOne
  @JoinColumn(name="ROOM_TYPE_ID")
  private RoomType type;

  @Column(name="ROOM_PRICE")
  private int price;

  @Column(name="ROOM_NUMBER")
  private int number;

  public Room() {}

  public Room(Hotel hotel, RoomType type, int number, int price) {
    this.hotel = hotel;
    this.type = type;
    this.number = number;
    this.price = price;
  }

  public Room(long id, Hotel hotel, RoomType type, int number, int price) {
    this.id = id;
    this.hotel = hotel;
    this.type = type;
    this.number = number;
    this.price = price;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  public RoomType getType() {
    return type;
  }

  public void setType(RoomType type) {
    this.type = type;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

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
