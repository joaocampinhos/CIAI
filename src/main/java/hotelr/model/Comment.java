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
import javax.persistence.CascadeType;
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

  @OneToOne(mappedBy="parent", cascade=CascadeType.ALL, targetEntity=Reply.class, fetch=FetchType.LAZY)
  private Reply reply;

  public Comment() {
    super();
  }

  public Comment(long id, Guest guest, String comment, Timestamp creationDate, Hotel hotel, boolean pending){
    super(id, comment, creationDate, pending);
    this.guest = guest;
    this.hotel = hotel;
  }

  public Comment(Guest guest, String comment, Timestamp creationDate, Hotel hotel, boolean pending){
    super(comment, creationDate, pending);
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

  public String toJSON() {
    return "{ \"hotel_id\": " + this.hotel.getId() + ", \"guest\": " + this.guest.toJSON() + ", \"comment\": \"" + this.getComment() + "\", \"date\": \"" + this.getCreationDate().toString() + "\", \"reply\": " + this.replyToJSON() + " }";
  }

  private String replyToJSON() {
    if (reply != null) return reply.toJSON();
    else return "\"\"";
  }
}
