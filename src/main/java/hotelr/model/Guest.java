package hotelr.model;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;

@Entity
@Table(name="GUEST_TABLE")
public class Guest extends User {

  @OneToMany(mappedBy="guest", targetEntity=Booking.class, fetch=FetchType.LAZY)
  public List<Booking> bookings;

  public Guest() {
    super();
    this.bookings = new ArrayList<Booking>();
  }

  public Guest(long id, String name, String email, String password) {
    super(id, name, email, password);
    this.bookings = new ArrayList<Booking>();
  }

  public List<Booking> getBookings() {
    return this.bookings;
  }
}
