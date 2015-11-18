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


  @OneToOne
  @JoinColumn(name="PARENT_ID")
  private Comment parent;

  @ManyToOne
  @JoinColumn(name="MANAGER_ID")
  private Manager manager;

  public Reply() {
    super();
  }

  public Reply(long id, Comment parent, String comment, Timestamp creationDate, Manager manager, boolean pending) {
    super(id, comment, creationDate, pending);
    this.parent = parent;
    this.manager = manager;
  }

  public Reply(Comment parent, String comment, Timestamp creationDate, Manager manager, boolean pending) {
    super(comment, creationDate, pending);
    this.parent = parent;
    this.manager = manager;
  }

  public Comment getParent() {
    return parent;
  }

  public void setParent(Comment parent) {
    this.parent = parent;
  }

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
