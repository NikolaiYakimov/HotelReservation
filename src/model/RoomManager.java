package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RoomManager implements StoreableAndLoadable {
    private final String FILE_PATH="D:\\JavaProjects\\HotelRoomReservetioSystem\\src\\Data\\RoomStorageFile";
    private List<RoomModel> rooms;

    public RoomManager(){
        rooms=new ArrayList<>();
        loadFromFile();
    }

    @Override
    public void loadFromFile() {
        try (BufferedReader bf=new BufferedReader(new FileReader(FILE_PATH))){
            String line;
            while((line=bf.readLine())!=null){
                String[] parts=line.split(",");
                String roomNumber=parts[0];
                RoomType type=RoomType.valueOf(parts[1].toUpperCase());
                double pricePerNight=Double.parseDouble(parts[2]);
                double cancellationFee=Double.parseDouble(parts[3]);
                boolean isBooked=Boolean.getBoolean(parts[4]);
                rooms.add(new RoomModel.RoomBuilder()
                        .setNumber(roomNumber)
                        .setType(type)
                        .setPrice(pricePerNight)
                        .setCancellationFee(cancellationFee)
                        .setBooking(isBooked).build());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveToFile() {
        try(PrintWriter writer=new PrintWriter(new FileWriter(FILE_PATH))){
            for(RoomModel room:rooms){
                writer.println(room.getRoomNumber()+","+room.getType().name()+','+room.getPrice()+","+room.getCancellationFee()+","+room.isBooked());
            }

        }catch (IOException e){
            System.err.println("Error saving rooms: "+e.getMessage());
        }

    }

    public List<RoomModel> getAvailableRooms(){
        List<RoomModel> availableRooms=new ArrayList<>();
        for(RoomModel room:rooms){
            if(!room.isBooked()){
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }
    public void displayAvailableRooms(){
        for (RoomModel room: getAvailableRooms()){
            System.out.println(room);
        }
    }

    public RoomModel findRoomByNumber(String roomNumber){
        for(RoomModel room:rooms){
            if(room.getRoomNumber().equalsIgnoreCase(roomNumber))
                return room;
        }
        return null;
    }

    public void bookRoam(RoomModel room){
        room.bookRoom();
    }
    public void cancelBooking(RoomModel room){
        room.cancelBooking();
    }

    public List<RoomModel> getAllRooms(){
        return rooms;
    }
    public void addRoom(RoomModel newRoom){
        rooms.add(newRoom);

    }
    public void removeRoom(String roomNumber){
        rooms.removeIf(room->room.getRoomNumber().equalsIgnoreCase(roomNumber));
    }
}
