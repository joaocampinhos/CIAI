package hotelr.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="REPLY_TABLE")
public class Reply extends Message{

  private Comment parent;
  private Manager manager;
  
  public Reply() {
    super();
  }
  
  public Reply(long id, Comment parent, String comment, Timestamp creationDate, Manager manager) {
    super(id, comment, creationDate);
    this.parent = parent;
    this.manager = manager;
  }
  
  @OneToOne()
  @JoinColumn(name="PARENT_ID")
  public Comment getParent() {
    return parent;
  }
  
  public void setParent(Comment parent) {
    this.parent = parent;
  }
  
  @ManyToOne()
  @JoinColumn(name="MANAGER_ID")
  public Manager getManager() {
    return manager;
  }
  
  public void setManager(Manager manager) {
    this.manager = manager;
  }
  
  public String toString() {
    return "Id: " + getId() + "\nParent: " + getParent().getId() + "\nComment: " + getComment() + "\nCreation Date: " + getCreationDate().toString() + "\n Manager: " + getManager().getName();
  }
}
