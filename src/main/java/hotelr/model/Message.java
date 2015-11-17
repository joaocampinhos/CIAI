package hotelr.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Message {

  @Id
  @GeneratedValue(strategy=GenerationType.TABLE)
  @Column(name="COMMENT_ID")
  private long id;

  @Column(name="COMMENT_COMMENT")
  private String comment;

  @Column(name="COMMENT_CREATION_DATE")
  private Timestamp creationDate;

  @Column(name="COMMENT_PENDING")
  private boolean pending;

  public Message() {}

  public Message(String comment, Timestamp creationDate, boolean pending) {
    this.comment = comment;
    this.creationDate = creationDate;
    this.pending = pending;
  }

  public Message(long id, String comment, Timestamp creationDate, boolean pending) {
    this.id = id;
    this.comment = comment;
    this.creationDate = creationDate;
    this.pending = pending;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment){
    this.comment = comment;
  }

  public Timestamp getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }

  public boolean getPending(){
    return pending;
  }

  public void setPending(boolean pending){
    this.pending = pending;
  }
}
