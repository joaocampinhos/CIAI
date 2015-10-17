package hotelr.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="COMMENT_TABLE")
public class Comment extends Message{


  @ManyToOne
  @JoinColumn(name="GUEST_ID")
  private Guest guest;

  @ManyToOne
  @JoinColumn(name="HOTEL_ID")
  private Hotel hotel;

  @OneToOne
  @JoinColumn(name="REPLY_ID")
  private Reply reply;

  public Comment() {
    super();
  }

  public Comment(long id, Guest guest, String comment, Timestamp creationDate, Hotel hotel){
    super(id, comment, creationDate);
    this.guest = guest;
    this.hotel = hotel;
  }

  public Comment(Guest guest, String comment, Timestamp creationDate, Hotel hotel){
    super(comment, creationDate);
    this.guest = guest;
    this.hotel = hotel;
  }

  public Guest getGuest() {
    return guest;
  }

  public void setGuest(Guest guest){
    this.guest = guest;
  }

  public Hotel getHotel() {
    return hotel;
  }

  public void setHotel(Hotel hotel) {
    this.hotel = hotel;
  }

  public Reply getReply() {
    return reply;
  }

  public void setReply(Reply reply) {
    this.reply = reply;
  }

  public String toString() {
    return "Id: " + getId() + "\nGuest: " + getGuest().getName() + "\nComment: " + getComment() + "\nCreation Date: " + getCreationDate().toString() + "\nHotel: " + getHotel().getName();
  }
}
