package Users;

import model.RoomManager;
import model.RoomModel;
import model.RoomType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User extends Person {
    private final String BOOKING_FILE_PATH = "D:\\JavaProjects\\HotelRoomReservetioSystem\\src\\Data\\BookHistory";
    private final String id;
    private List<RoomModel> bookHistory;


    public User(String username, String password, String id) {
        super(username, password, "USER");
        this.id = id;
        this.bookHistory = new ArrayList<>();
        loadFromFile();

    }

    public String getId() {
        return id;
    }

    public void viewAvailableRooms(RoomManager roomManager) {
        System.out.println("Available rooms:");
        roomManager.displayAvailableRooms();
    }

    public void cancelBooking(RoomManager roomManager, String roomNumber) {
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
            saveBookToFile(roomManager);
            System.out.println("Booking for room " + roomNumber + " successfully canceled.");
            roomManager.saveToFile();
        } else {
            System.out.println("Room is not currently booked.");
        }
        roomManager.saveToFile();
    }

    public void bookRoom(RoomManager roomManager, String roomNumber) {
        for (RoomModel room : roomManager.getAllRooms()) {
            if (room.getRoomNumber().equals(roomNumber) && !room.isBooked()) {
                room.bookRoom();
                roomManager.saveToFile();
                bookHistory.add(room);
                saveBookToFile(roomManager);
                System.out.println("Room " + room.getRoomNumber() + " is booked successfully!");
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

    public List<RoomModel> getBookHistory() {
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

    public void saveBookToFile(RoomManager roomManager) {
        Map<String, List<RoomModel>> userBookings = new HashMap<>();
        //We use the reader to prevent overwriting the existing data,about the booked room of the user
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKING_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String userId = parts[0];
                RoomModel room = new RoomModel.RoomBuilder().setNumber(parts[1])
                        .setType(RoomType.valueOf(parts[2].toUpperCase()))
                        .setPrice(Double.parseDouble(parts[3]))
                        .setBooking(Boolean.parseBoolean(parts[4])).build();

                //check if we have user with this id, if we don't have we create ArrayList and add the room in it
                //if we have the user id in the map,the computeIfAbsent will return the List, and we will just add the other room
                userBookings.computeIfAbsent(userId, e -> new ArrayList<>()).add(room);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Add or update the current user bookHistory in the map
        userBookings.put(id, new ArrayList<>(bookHistory));


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKING_FILE_PATH))) {
            for (Map.Entry<String, List<RoomModel>> entry : userBookings.entrySet()) {
                String userId = entry.getKey();
                for (RoomModel room : entry.getValue()) {
                    String line = userId + "," + room.getData();
//                if (person instanceof User user && !user.getBookHistory().isEmpty()) {
//                    line += "," + String.join(";", user.getBookHistory());
//                }
                    writer.write(line);
                    writer.newLine();
                }

            }
            System.out.println("Save to file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKING_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(",");
                String userId = parts[0];

                if (userId.equals(id)) {
                    String roomNumber = parts[1];
                    RoomType type = RoomType.valueOf(parts[2].toUpperCase());
                    double pricePerNight = Double.parseDouble(parts[3]);
                    boolean isItBooked;
                    if (parts[4].equals("true")) {
                        isItBooked = true;
                    } else {
                        isItBooked = false;
                    }


                    bookHistory.add(new RoomModel.RoomBuilder().setNumber(roomNumber)
                            .setType(type)
                            .setPrice(pricePerNight)
                            .setBooking(isItBooked).build());
                }
            }
//            System.out.println("Load from the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
