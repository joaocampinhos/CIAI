package hotelr.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

@Entity
@Table(name="GUEST_TABLE")
public class Guest extends User {

  @OneToMany(mappedBy="guest", cascade=CascadeType.ALL, targetEntity=Booking.class, fetch=FetchType.LAZY)
  public List<Booking> bookings;

  @OneToMany(mappedBy="guest", cascade=CascadeType.ALL, targetEntity=Comment.class, fetch=FetchType.LAZY)
  public List<Comment> comments;

  public Guest() {
    super("ROLE_GUEST");
    this.bookings = new ArrayList<Booking>();
    this.comments = new ArrayList<Comment>();
  }

  public Guest(long id, String name, String email, String password) {
    super(id, name, email, password, "ROLE_GUEST");
    this.bookings = new ArrayList<Booking>();
    this.comments = new ArrayList<Comment>();
  }

  public List<Booking> getBookings() {
    return this.bookings;
  }

  public String bookingsToJSON() {
    String json = "[";

    Iterator<Booking> boo = bookings.iterator();
    while (boo.hasNext()) {
      Booking b = boo.next();
      json += "{ \"id\": " + b.getId() + ", \"arrival\": \"" + b.getArrival() + "\", \"departure\": \"" + b.getDeparture() + "\", \"price\": " + b.getRoom().getPrice() + ", \"roomtype\": " + b.getRoomType().toJSON() + ", \"hotel\": " + b.getHotel().toJSON() + "}";
      if (boo.hasNext()) json += ",";
    }

    json += "]";
    return json;
  }
}
