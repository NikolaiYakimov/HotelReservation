import Users.*;
import Users.UserManager;
import model.*;


import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();
    private static RoomManager roomManager = new RoomManager();
    private static User currentUser;
    private static Administrator currentAdmin;

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Reservation System");
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void registerUser(){
        System.out.println("Enter username:");
        String username=scanner.nextLine();
        System.out.println("Enter new password:");
        String password=scanner.nextLine();
        System.out.println("Special symbols: ");
        String specSym=scanner.nextLine();

        if(userManager.findByUsername(username)!=null){
            System.out.println("Username already exists. Please choose a different one.");
        }else {
        userManager.registerUser(username,password,specSym);
        System.out.println("Successful registration!");
    }
    }

    private static void login() {
        System.out.println("Enter username:");
        String username=scanner.nextLine();
        System.out.println("Enter  password:");
        String password=scanner.nextLine();

        Person person=userManager.logIn(username,password);
        if(person!=null){
            if(person instanceof User){
                currentUser = (User) person;
                userMenu();
            } else if (person instanceof Administrator) {
                currentAdmin=(Administrator) person;
                adminMenu();
            }

        }else{
            System.out.println("Invalid username or password.");
        }

    }


    private static void removeRoom(){
        System.out.println("Enter number of the room to delete: ");
        String roomNumber=scanner.nextLine();
        currentAdmin.removeRoom(roomNumber);
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\nAdmin Menu");
            System.out.println("1. View All Bookings");
            System.out.println("2. View All Rooms");
            System.out.println("3. View Total Income by today");
            System.out.println("4. Add Room");
            System.out.println("5. Remove Room");
            System.out.println("6. Logout");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    currentAdmin.viewAllBookings();
                    break;
                case 2:
                    currentAdmin.displayAvailableRooms();
                    break;
                case 3:
                    currentAdmin.viewTotalIncomePerDay();
                    break;
                case 4:
                    currentAdmin.addRoom(scanner);
                    break;
                case 5:
                    removeRoom();
                    break;
                case 6:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    private static void userMenu() {
        while (true) {
            System.out.println("\nUser Menu");
            System.out.println("1. View Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Profile");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    currentUser.viewAvailableRooms(roomManager);
                    break;
                case 2:
                    System.out.println("Enter room to book:");
                    String roomNumber=scanner.nextLine();
                    currentUser.bookRoom(roomManager,roomNumber);
                    break;
                case 3:
                    System.out.println("Enter room to cancel booking:");
                    String roomNumberToCancel=scanner.nextLine();
                    currentUser.cancelBooking(roomManager,roomNumberToCancel);
                    break;
                case 4:
                    currentUser.viewProfile();
                    break;
                case 5:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

}
