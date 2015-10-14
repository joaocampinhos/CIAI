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
  private long id;
  private Timestamp arrival;
  private Timestamp departure;
  private RoomType roomType;
  private Hotel hotel;
  private Guest guest;

  public Booking() {}

  public Booking(long id, Timestamp arrival, Timestamp departure, RoomType roomType, Hotel hotel, Guest guest) {
    this.id = id;
    this.arrival = arrival;
    this.departure = departure;
    this.roomType = roomType;
    this.hotel = hotel;
    this.guest = guest;
  }

  @Id
  @Column(name="BOOKING_ID")
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Column(name="BOOKING_ARRIVAL")
  public Timestamp getArrival() {
    return arrival;
  }

  public void setArrival(Timestamp arrival) {
    this.arrival = arrival;
  }

  @Column(name="BOOKING_DEPARTURE")
  public Timestamp getDeparture() {
    return departure;
  }

  public void setDeparture(Timestamp departure) {
    this.departure = departure;
  }

  @ManyToOne
  @JoinColumn(name="BOOKING_ROOM_TYPE")
  public RoomType getRoomType() {
    return roomType;
  }

  public void setRoomType(RoomType roomType) {
    this.roomType = roomType;
  }

  @ManyToOne
  @JoinColumn(name="BOOKING_HOTEL")
  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  @ManyToOne
  @JoinColumn(name="BOOKING_GUEST")
  public Guest getGuest() {
    return guest;
  }

  public void setGuest(Guest guest) {
    this.guest = guest;
  }
}
