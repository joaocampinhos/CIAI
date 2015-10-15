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


  private Guest guest;
  private Hotel hotel;

  @OneToOne(mappedBy="parent", targetEntity=Reply.class, fetch=FetchType.EAGER)
  private Reply reply;

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
