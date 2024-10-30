package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RoomManager implements StoreableAndLoadable {
    private final String FILE_PATH = "D:\\JavaProjects\\HotelRoomReservetioSystem\\src\\Data\\RoomStorageFile";
    private List<RoomModel> rooms;

    public RoomManager() {
        rooms = new ArrayList<>();
        loadFromFile();
    }

    @Override
    public void loadFromFile() {
        try (BufferedReader bf = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] parts = line.split(",");
                String roomNumber = parts[0];
                RoomType type = RoomType.valueOf(parts[1].toUpperCase());
                double pricePerNight = Double.parseDouble(parts[2]);
                double cancellationFee = Double.parseDouble(parts[3]);
                boolean isItBooked;
                if (parts[4].equals("true")) {
                    isItBooked = true;
                } else {
                    isItBooked = false;
                }


                rooms.add(new RoomModel.RoomBuilder()
                        .setNumber(roomNumber)
                        .setType(type)
                        .setPrice(pricePerNight)
                        .setCancellationFee(cancellationFee)
                        .setBooking(isItBooked).build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (RoomModel room : rooms) {
                writer.println(room.getRoomNumber() + "," + room.getType().name() + ',' + room.getPrice() + "," + room.getCancellationFee() + "," + room.isBooked());
            }

        } catch (IOException e) {
            System.err.println("Error saving rooms: " + e.getMessage());
        }

    }

    public List<RoomModel> getAvailableRooms() {
        List<RoomModel> availableRooms = new ArrayList<>();
        rooms.clear();
        loadFromFile();
        for (RoomModel room : rooms) {
            if (!room.isBooked()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void displayAvailableRooms() {
        for (RoomModel room : getAvailableRooms()) {
            System.out.println(room);
        }
    }

    public RoomModel findRoomByNumber(String roomNumber) {
        for (RoomModel room : rooms) {
            if (room.getRoomNumber().equalsIgnoreCase(roomNumber))
                return room;
        }
        return null;
    }

    public void bookRoam(RoomModel room) {
        room.bookRoom();
    }

    public RoomModel cancelBooking(RoomModel room) {
        room.cancelBooking();
        return room;
    }

    public List<RoomModel> getAllRooms() {
        return rooms;
    }

    public void addRoom(Scanner scanner) {
        System.out.println("Select room number: ");
        String roomNumber = scanner.nextLine();

        for (RoomType roomType : RoomType.values()) {
            System.out.println((roomType.ordinal() + 1) + ". " + roomType);
        }

        // select a room type by number
        System.out.print("Enter the number corresponding to the room type: ");
        int roomTypeSelection = Integer.parseInt(scanner.nextLine());

        // Get the selected RoomType from the user's input
        RoomType selectedRoomType = RoomType.values()[roomTypeSelection - 1];

        System.out.print("Enter price per night: ");
        double pricePerNight = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter cancellation fee: ");
        double cancellationFee = Double.parseDouble(scanner.nextLine());

        // Create the new room using the selected RoomType
        RoomModel newRoom = new RoomModel.RoomBuilder().setNumber(roomNumber).setType(selectedRoomType).setPrice(pricePerNight).setCancellationFee(cancellationFee).setBooking(false).build();

        rooms.add(newRoom); // Assuming a RoomManager class handles rooms

        System.out.println("Room added successfully!");

    }

    public void removeRoom(String roomNumber) {
        rooms.removeIf(room -> room.getRoomNumber().equalsIgnoreCase(roomNumber));


    }

}

