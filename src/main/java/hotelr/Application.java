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
  RoomTypeRepository roomTypes;

  @Autowired

  RoomRepository rooms;

  @Override
  public void run(String... strings) {
    log.info("Setting up seed data");

    managers.deleteAll();
    Manager boss = new Manager(1, "O Chefe", "boss@hotelr.com", "boss123");
    managers.save(boss);

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
  }

}


