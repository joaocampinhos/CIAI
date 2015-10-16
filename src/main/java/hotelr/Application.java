package hotelr;

import hotelr.model.*;
import hotelr.repository.*;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;


@SpringBootApplication
public class Application implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

  /**
   * The main() method uses Spring Boot’s SpringApplication.run() method to launch an application.
   * The run() method returns an ApplicationContext where all the beans that were created
   * either by your app or automatically added thanks to Spring Boot are.
   * @param args
   */
  public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
  }

  @Autowired
  HotelRepository hotels;

  @Autowired
  ManagerRepository managers;

  @Autowired
  BookingRepository bookings;

  @Autowired
  GuestRepository guests;

  @Autowired
  RoomTypeRepository roomTypes;

  @Autowired
  RoomRepository rooms;

  @Override
  public void run(String... strings) throws Exception {
    log.info("Setting up seed data");

    managers.deleteAll();
    Manager boss = new Manager(1, "O Chefe", "boss@hotelr.com", "boss123");
    managers.save(boss);

    guests.deleteAll();
    Guest guest = new Guest(2, "Harvey Specter", "harvey@pearsonspecterlitt.com", "imthebest");
    guests.save(guest);

    hotels.deleteAll();
    Hotel myHotels[] = {new Hotel(1,"Marriot", "address", "category", 5, boss),
                        new Hotel(2,"Intercontinental", "address", "category", 3, boss),
                        new Hotel(3,"Trip", "address", "category", 3, boss),
                        new Hotel(4,"Holiday Inn", "address", "category", 4, boss),
                        new Hotel(5,"Tulip", "address", "category", 4, boss),
                        new Hotel(6,"Hostel da Costa", "address", "category", 3, boss)};

    RoomType myRoomTypes[] = {
      new RoomType(1, "Suite"),
      new RoomType(2, "Single"),
      new RoomType(3, "Double")
    };

    for(RoomType roomType : myRoomTypes) roomTypes.save(roomType);

    int i = 1;
    for(Hotel hotel : myHotels) {
      hotels.save(hotel);
      Room myRooms[] = {
        new Room(i++, hotel, myRoomTypes[0], 50, 170),
        new Room(i++, hotel, myRoomTypes[1], 50, 100),
        new Room(i++, hotel, myRoomTypes[2], 50, 150)
      };

      for (Room room : myRooms) {
        rooms.save(room);
        hotel.addRoom(room);
      }
    }

    // Merdas para verificar o search
    // TODO: mais vale fazer um teste para isto mesmo

/*    Hotel h = new Hotel(7, "Pearson Specter Hotels", "address", "category", 5, boss);
    Room r = new Room(i++, h, myRoomTypes[0], 1, 5000);
    Room r2 = new Room(i++, h, myRoomTypes[1], 1, 5000);

    hotels.save(h);
    rooms.save(r);
    rooms.save(r2);
    h.addRoom(r);
    h.addRoom(r2);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date arrival = sdf.parse("2015-10-05");
    Date departure = sdf.parse("2015-10-06");

    bookings.deleteAll();
    Booking b = new Booking(1, new Timestamp(arrival.getTime()), new Timestamp(departure.getTime()), r.getType(), r, h, guest);
    bookings.save(b);
    Booking b2 = new Booking(2, new Timestamp(arrival.getTime()), new Timestamp(departure.getTime()), r2.getType(), r2, h, guest);
    //bookings.save(b2);*/

  }

}


