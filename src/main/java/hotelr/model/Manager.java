package hotelr.model;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name="MANAGER_TABLE")
public class Manager extends User {

  @Column(name="MANAGER_PENDING")
  private boolean pending;

  @OneToMany(mappedBy="manager", cascade=CascadeType.ALL, targetEntity=Hotel.class, fetch=FetchType.LAZY)
  public List<Hotel> hotels;

  @OneToMany(mappedBy="manager", cascade=CascadeType.ALL, targetEntity=Reply.class, fetch=FetchType.LAZY)
  public List<Reply> replies;

  public Manager() {
    super("ROLE_MANAGER");
    this.hotels = new ArrayList<Hotel>();
    this.replies = new ArrayList<Reply>();
  }

  public Manager(long id, String name, String email, String password, boolean pending) {
    super(id, name, email, password, "ROLE_MANAGER");
    this.pending = pending;
    this.hotels = new ArrayList<Hotel>();
    this.replies = new ArrayList<Reply>();
  }

  public List<Hotel> getHotels() {
    return hotels;
  }

  public boolean getPending(){
    return pending;
  }

  public void setPending(boolean pending){
    this.pending = pending;
  }
}