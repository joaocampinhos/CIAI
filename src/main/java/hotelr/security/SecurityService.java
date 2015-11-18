package hotelr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import hotelr.model.*;
import hotelr.repository.*;

@Component("SecurityService")
public class SecurityService {
  @Autowired
  HotelRepository hotels;

  @Autowired
  BookingRepository bookings;

  // checks if a User manages a hotel
  public boolean managesHotel(User user, long hotelId) {
    Hotel hotel = hotels.findOne(hotelId);
    return hotel != null && hotel.getManager() != null && user.getUsername().equals(hotel.getManager().getEmail());
  }

  // hotels

  public boolean canEditOrDeleteHotel(User user, long hotelId) {
    managesHotel(user, hotelId);
  }

  // bookings

  public boolean managerCanCreateBooking(User user, long hotelId) {
    managesHotel(user, hotelId);
  }

  public boolean canEditBooking(User user, long bookingId) {
    Booking booking = bookings.findOne(bookingId);
    return booking != null && booking.getGuest() != null && user.getUsername().equals(booking.getGuest().getEmail());
  }

  public boolean canDeleteBooking(User user, long bookingId) {
    Booking booking = bookings.findOne(bookingId);
    return booking != null && (
      (booking.getGuest() != null && user.getUsername().equals(booking.getGuest().getEmail())) ||
      (booking.getHotel() != null && booking.getHotel().getManager() != null && user.getUsername().equals(booking.getHotel().getManager().getEmail()))
    );
  }

  // replies

  public boolean canCreateReply(User user, long hotelId) {
    managesHotel(user, hotelId);
  }
}