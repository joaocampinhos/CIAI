package hotelr.model;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinTable;

@Entity
@Table(name="HOTEL_TABLE")
public class Hotel {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  private String name;
  private String address;
  private String category;
  private int rating;
  private Manager manager;

  //@OneToMany(mappedBy="hotel", targetEntity=Room.class, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
  @OneToMany(cascade=CascadeType.ALL)
  @JoinTable(name = "HOTEL_ROOMS",
    joinColumns = @JoinColumn(name = "HOTEL_ID"),
    inverseJoinColumns = @JoinColumn(name = "ROOM_ID"))
  public List<Room> rooms;

  public Hotel() {
    this.rooms = new ArrayList<Room>();
  }

  public Hotel(long id, String name, String address, String category, int rating, Manager manager) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.category = category;
    this.rating = rating;
    this.manager = manager;
    this.rooms = new ArrayList<Room>();
  }

  @Id
  @Column(name="HOTEL_ID")
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Column(name="HOTEL_NAME")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Column(name="HOTEL_ADDRESS")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name="HOTEL_CATEGORY")
  public String getCategory(){
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  @Column(name="HOTEL_RATING")
  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  @ManyToOne()
  @JoinColumn(name="MANAGER_ID")
  public Manager getManager() {
    return manager;
  }

  public void setManager(Manager manager) {
    this.manager = manager;
  }

  public List<Room> getRooms() {
    return this.rooms;
  }

  public void addRoom(Room room) {
    this.rooms.add(room);
  }

  @Override
  public String toString() {
    return "Id: " + getId() + "\nName: " + getName() + "\nAddress: " + getAddress() + "\nCategory: " + getCategory() + "\nRating: " + getRating() + "\nManager: " + getManager().getName();
  }

}

