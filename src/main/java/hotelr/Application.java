package hotelr;

import hotelr.model.*;
import hotelr.repository.*;

import java.sql.Timestamp;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;


@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true)
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
  AdminRepository admins;

  @Autowired
  HotelRepository hotels;

  @Autowired
  ManagerRepository managers;

  @Autowired
  GuestRepository guests;

  @Autowired
  ModeratorRepository moderators;

  @Autowired
  RoomTypeRepository roomTypes;

  @Autowired
  BookingRepository bookings;

  @Autowired
  RoomRepository rooms;

  @Autowired
  CommentRepository comments;

  @Autowired
  ReplyRepository replies;

  @Override
  public void run(String... strings) throws Exception {
    log.info("Setting up seed data");

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    managers.deleteAll();
    Manager boss = new Manager(1, "O Chefe", "boss@hotelr.com", encoder.encode("boss123"), false);
    managers.save(boss);

    guests.deleteAll();
    Guest myGuests[] = {
        new Guest(2, "Harvey Specter", "harvey@pearsonspecterlitt.com", encoder.encode("imthebest")),
        new Guest(3, "Toni", "toni@vitominas.pt", encoder.encode("12345"))
    };
    for(Guest guest: myGuests) guests.save(guest);

    admins.deleteAll();
    Admin admin = new Admin(4, "Jessica Pearson", "jessica@pearsonspecterlitt.com", encoder.encode("god"));
    admins.save(admin);

    moderators.deleteAll();
    Moderator myModerators[] = {
        new Moderator(5, "Jose Rodrigues", "zerodrigues@rtp1.pt", encoder.encode("mod"))
    };
    for(Moderator moderator: myModerators) moderators.save(moderator);

    Manager boss2 = new Manager(6, "O Chefe2", "boss2@hotelr.com", encoder.encode("boss2123"), true);
    managers.save(boss2);

    hotels.deleteAll();
    Hotel myHotels[] = {new Hotel(1,"Marriot", "address", "category", 5, boss, false),
                        new Hotel(2,"Intercontinental", "address", "category", 3, boss, false),
                        new Hotel(3,"Trip", "address", "category", 3, boss, false),
                        new Hotel(4,"Holiday Inn", "address", "category", 4, boss, false),
                        new Hotel(5,"Tulip", "address", "category", 4, boss, false),
                        new Hotel(6,"Hostel da Costa", "address", "category", 3, boss, false)};

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
        // hotel.addRoom(room);
      }
    }

    Comment myComments[] = {
        new Comment(1, myGuests[0], "mlg 420 blaze it", new Timestamp(System.currentTimeMillis()), myHotels[0], false),
        new Comment(2, myGuests[1], "OMG!", new Timestamp(System.currentTimeMillis()), myHotels[0], false),
        new Comment(3, myGuests[1], "WoW!", new Timestamp(System.currentTimeMillis()), myHotels[1], false)
    };

    for(Comment comment: myComments) comments.save(comment);

    Reply myReplies[] = {
        new Reply(myComments[0], "noScope", new Timestamp(System.currentTimeMillis()), boss, false),
        new Reply(myComments[1], "Thank you, very nice!", new Timestamp(System.currentTimeMillis()), boss, false)
    };

    for(Reply reply: myReplies) replies.save(reply);
  }

}


