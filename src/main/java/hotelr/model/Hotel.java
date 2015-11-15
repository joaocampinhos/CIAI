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
  @Column(name="HOTEL_ID")
  private long id;

  @Column(name="HOTEL_NAME")
  private String name;

  @Column(name="HOTEL_ADDRESS")
  private String address;

  @Column(name="HOTEL_CATEGORY")
  private String category;

  @Column(name="HOTEL_RATING")
  private int rating;

  @ManyToOne()
  @JoinColumn(name="MANAGER_ID")
  private Manager manager;

  @Column(name="HOTEL_PENDING")
  private boolean pending;

  @OneToMany(mappedBy="hotel", cascade=CascadeType.ALL, targetEntity=Room.class, fetch=FetchType.LAZY)
  public List<Room> rooms;

  @OneToMany(mappedBy="hotel", cascade=CascadeType.ALL, targetEntity=Comment.class, fetch=FetchType.LAZY)
  private List<Comment> comments;

  private int singleRooms;
  private int singlePrice;
  private int doubleRooms;
  private int doublePrice;
  private int suiteRooms;
  private int suitePrice;

  public Hotel() {
    this.rooms = new ArrayList<Room>();
    this.comments = new ArrayList<Comment>();
  }

  public Hotel(long id, String name, String address, String category, int rating, Manager manager, boolean pending) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.category = category;
    this.rating = rating;
    this.manager = manager;
    this.rooms = new ArrayList<Room>();
    this.comments = new ArrayList<Comment>();
    this.pending = pending;
  }

  public Hotel(long id, String name, String address, String category, int rating, Manager manager, int singleRooms, int singlePrice, int doubleRooms, int doublePrice, int suiteRooms, int suitePrice) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.category = category;
    this.rating = rating;
    this.manager = manager;
    this.setSingleRooms(singleRooms);
    this.setSinglePrice(singlePrice);
    this.setDoubleRooms(doubleRooms);
    this.setDoublePrice(doublePrice);
    this.setSuiteRooms(suiteRooms);
    this.setSuitePrice(suitePrice);
    this.rooms = new ArrayList<Room>();
    this.comments = new ArrayList<Comment>();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCategory(){
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

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

  public List<Comment> getComments() {
    return comments;
  }

  public void addComment(Comment comment) {
    this.comments.add(comment);
  }

  public int getSingleRooms() {
    return singleRooms;
  }

  public void setSingleRooms(int singleRooms) {
    this.singleRooms = singleRooms;
  }

  public int getSinglePrice() {
    return singlePrice;
  }

  public void setSinglePrice(int singlePrice) {
    this.singlePrice = singlePrice;
  }

  public int getDoubleRooms() {
    return doubleRooms;
  }

  public void setDoubleRooms(int doubleRooms) {
    this.doubleRooms = doubleRooms;
  }

  public int getDoublePrice() {
    return doublePrice;
  }

  public void setDoublePrice(int doublePrice) {
    this.doublePrice = doublePrice;
  }

  public int getSuiteRooms() {
    return suiteRooms;
  }

  public void setSuiteRooms(int suiteRooms) {
    this.suiteRooms = suiteRooms;
  }

  public int getSuitePrice() {
    return suitePrice;
  }

  public void setSuitePrice(int suitePrice) {
    this.suitePrice = suitePrice;
  }

  public void setRooms(ArrayList<Room> rooms){
    this.rooms = rooms;
  }

  public boolean getPending(){
    return pending;
  }

  public void setPending(boolean pending){
    this.pending = pending;
  }

  @Override
  public String toString() {
    return "Id: " + getId() + "\nName: " + getName() + "\nAddress: " + getAddress() + "\nCategory: " + getCategory() + "\nRating: " + getRating() + "\nManager: " + getManager().getName();
  }

}

