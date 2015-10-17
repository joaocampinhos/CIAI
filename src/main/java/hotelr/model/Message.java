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

  public Message() {}

  public Message(String comment, Timestamp creationDate) {
    this.comment = comment;
    this.creationDate = creationDate;
  }

  public Message(long id, String comment, Timestamp creationDate) {
    this.id = id;
    this.comment = comment;
    this.creationDate = creationDate;
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
}
