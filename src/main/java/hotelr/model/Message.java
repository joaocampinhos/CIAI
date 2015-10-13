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
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  private String comment;
  private Timestamp creationDate;
  
  public Message() {}
  
  public Message(long id, String comment, Timestamp creationDate) {
    this.id = id;
    this.comment = comment;
    this.creationDate = creationDate;
  }
  
  @Id
  @Column(name="COMMENT_ID")
  public long getId() {
    return id;
  }
  
  public void setId(long id) {
    this.id = id;
  }
  
  @Column(name="COMMENT_COMMENT")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment){
    this.comment = comment;
  }
  
  @Column(name="COMMENT_CREATION_DATE")
  public Timestamp getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }
}
