package hotelr.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="COMMENT_TABLE")
public class Comment extends Message{


  private Guest guest;
  private Hotel hotel;

  public Comment() {
    super();
  }

  public Comment(long id, Guest guest, String comment, Timestamp creationDate, Hotel hotel){
    super(id, comment, creationDate);
    this.guest = guest;
    this.hotel = hotel;
  }

  @ManyToOne()
  @JoinColumn(name="GUEST_ID")
  public Guest getGuest() {
    return guest;
  }

  public void setGuest(Guest guest){
    this.guest = guest;
  }

  @ManyToOne
  @JoinColumn(name="HOTEL_ID")
  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  public String toString() {
    return "Id: " + getId() + "\nGuest: " + getGuest().getName() + "\nComment: " + getComment() + "\nCreation Date: " + getCreationDate().toString() + "\nHotel: " + getHotel().getName();
  }
}
