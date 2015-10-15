package hotelr.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name="BOOKING_TABLE")
public class Booking {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name="BOOKING_ID")
  private long id;

  @Column(name="BOOKING_ARRIVAL")
  private Timestamp arrival;

  @Column(name="BOOKING_DEPARTURE")
  private Timestamp departure;

  @ManyToOne
  @JoinColumn(name="BOOKING_ROOM_TYPE")
  private RoomType roomType;

  @ManyToOne
  @JoinColumn(name="BOOKING_ROOM")
  private Room room;

  @ManyToOne
  @JoinColumn(name="BOOKING_HOTEL")
  private Hotel hotel;

  @ManyToOne
  @JoinColumn(name="BOOKING_GUEST")
  private Guest guest;

  public Booking() {}

  public Booking(long id, Timestamp arrival, Timestamp departure, RoomType roomType, Room room, Hotel hotel, Guest guest) {
    this.id = id;
    this.arrival = arrival;
    this.departure = departure;
    this.roomType = roomType;
    this.room = room;
    this.hotel = hotel;
    this.guest = guest;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Timestamp getArrival() {
    return arrival;
  }

  public void setArrival(Timestamp arrival) {
    this.arrival = arrival;
  }

  public Timestamp getDeparture() {
    return departure;
  }

  public void setDeparture(Timestamp departure) {
    this.departure = departure;
  }

  public RoomType getRoomType() {
    return roomType;
  }

  public void setRoomType(RoomType roomType) {
    this.roomType = roomType;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  public Guest getGuest() {
    return guest;
  }

  public void setGuest(Guest guest) {
    this.guest = guest;
  }
}
