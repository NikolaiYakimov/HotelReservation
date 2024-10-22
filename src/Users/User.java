package Users;

import model.RoomManager;
import model.RoomModel;

import java.util.ArrayList;
import java.util.List;

public class User extends Person {
    private List<RoomModel> bookHistory;
//    RoomManager roomManager;


    public User(String username, String password) {
        super(username, password,"USER");
        this.bookHistory = new ArrayList<>();
//        roomManager=new RoomManager();

    }

    public void viewAvailableRooms(RoomManager roomManager) {
        System.out.println("Available rooms:");
        roomManager.displayAvailableRooms();
    }

    public void cancelBooking(RoomManager roomManager,String roomNumber){
//
        RoomModel roomToCancel = null;
        // Find the room to cancel
        for (RoomModel room : roomManager.getAllRooms()) {
            if (room.getRoomNumber().equalsIgnoreCase(roomNumber)) {
                roomManager.cancelBooking(room);
                roomToCancel = room;
                break;
            }
        }

        if (roomToCancel != null) {
            // Remove the room from bookHistory
            bookHistory.remove(roomToCancel);  // Will work if equals() is properly overridden
            System.out.println("Booking for room " + roomNumber + " successfully canceled.");
            roomManager.saveToFile();
        } else {
            System.out.println("Room is not currently booked.");
        }
        roomManager.saveToFile();
    }

    public void bookRoom(RoomManager roomManager, String roomNumber) {
        for (RoomModel room : roomManager.getAllRooms()) {
            if (room.getRoomNumber().equals(roomNumber)&&!room.isBooked()) {
                String bookingDate = java.time.LocalDate.now().toString();
                room.bookRoom();
                roomManager.saveToFile();
                bookHistory.add(room);
                System.out.println("Room " +room.getRoomNumber() +" is booked successfully!");
                return;
            }
        }

    }

    // View booking history
    public void viewBookingHistory() {
        System.out.println("Booking History for " + getUsername() + ":");
        if (bookHistory.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (RoomModel room : bookHistory) {
                System.out.println(room);
            }
        }
    }
    public List<RoomModel> getBookHistory(){
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
