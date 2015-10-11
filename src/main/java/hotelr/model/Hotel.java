package hotelr.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

  public Hotel() {}

  public Hotel(long id, String name, String address, String category, int rating, Manager manager) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.category = category;
    this.rating = rating;
    this.manager = manager;
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

  @Override
  public String toString() {
    return "Id: " + getId() + "\nName: " + getName() + "\nAddress: " + getAddress() + "\nCategory: " + getCategory() + "\nRating: " + getRating() + "\nManager: " + getManager().getName();
  }

}

