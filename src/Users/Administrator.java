package Users;

import model.RoomManager;
import model.RoomModel;

import java.util.List;
import java.util.Scanner;

public class Administrator extends Person{
    private RoomManager roomManager;

    public Administrator(String username, String password) {
        super(username, password,"ADMIN");
        roomManager=new RoomManager();
    }

    public void viewAllBookings(){

        List<RoomModel> allRoams=roomManager.getAllRooms();
        System.out.println("All bookings");
        for (RoomModel room:allRoams){
            if(room.isBooked()){
                System.out.println(room);
            }
        }
    }

    public void displayAvailableRooms(){

        roomManager.displayAvailableRooms();
    }
    public void displayAllRooms(){
        System.out.println("All rooms:");
        List<RoomModel> rooms=roomManager.getAllRooms();
        for(RoomModel room:rooms){
            System.out.println(room);
        }
    }
    public void viewTotalIncomePerDay(){
        double totalIncome=0;
        for(RoomModel room:roomManager.getAllRooms()){
            if(room.isBooked()){
                totalIncome+=room.getPrice();
            }
        }
        System.out.println("Total income:$ "+totalIncome);
    }

    public void addRoom(Scanner scanner) {
        roomManager.addRoom(scanner);
        roomManager.saveToFile();

    }

    public void removeRoom(String roomNumber) {
        roomManager.removeRoom(roomNumber);
        roomManager.saveToFile();
        System.out.println("Room removed successfully!");
    }
    @Override
    public void viewProfile() {
        System.out.println("Admin Profile:");
        System.out.println("Username: " + getUsername());
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }
}
