package Users;

import model.RoomManager;
import model.RoomModel;

import java.util.ArrayList;
import java.util.List;

public class User extends Person {
    private List<String> bookHistory;


    public User(String username, String password) {
        super(username, password);
        this.bookHistory = new ArrayList<>();

    }

    public void viewAvailableRooms(RoomManager roomManager) {
        System.out.println("Available rooms:");
        roomManager.displayAvailableRooms();
    }

    public void bookRoom(RoomManager roomManager, String roomNumber) {
        for (RoomModel room : roomManager.getAvailableRooms()) {
            if (!room.isBooked()) {
                String bookingDate = java.time.LocalDate.now().toString();
                room.bookRoom();
                bookHistory.add(room.getRoomNumber() + "," + bookingDate);
            }
        }
    }

    // View booking history
    public void viewBookingHistory() {
        System.out.println("Booking History for " + getUsername() + ":");
        if (bookHistory.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (String booking : bookHistory) {
                System.out.println(booking);
            }
        }
    }
    public List<String> getBookHistory(){
        return bookHistory;
    }


    @Override
    public void viewProfile() {
        System.out.println("User Profile:");
        System.out.println("Username: " + getUsername());
        viewBookingHistory();
    }

    @Override
    public String getRole() {
        return "USER";
    }
}
