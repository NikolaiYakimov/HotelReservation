package Users;

import model.StoreableAndLoadable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements StoreableAndLoadable {
    private final String FILE_PATH = "D:\\JavaProjects\\HotelRoomReservetioSystem\\src\\Data\\UserStorageFile";
    private final List<Person> users = new ArrayList<>();


    public UserManager() {
        loadFromFile();
    }

    public void registerUser(String username, String password, String adminAccess) {
        int lastPersonId;
        if (getLastUser() == null) {
            lastPersonId = 0;
        } else
            lastPersonId = Integer.parseInt(getLastUser().getId());
        if (adminAccess.equals("$admin$")) {
            users.add(new Administrator(username, password));
        } else {

            users.add(new User(username, password, String.valueOf(lastPersonId + 1)));
        }
        saveToFile();
    }

    public User findByUsername(String username) {
        for (Person user : users) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return (User) user;
            }
        }
        return null;
    }

    //Login functionality
    public Person logIn(String username, String password) {
        for (Person person : users) {
            if (person.getUsername().equals(username) && person.getPassword().equals(password)) {
                System.out.println("Log in successfully!");
                return person;
            }
        }
        System.out.println("We dont have such account!");
        return null;
    }

    //List all the users for admins
    public void listAllUsers(Person admin) {
        if (admin instanceof Administrator) {
            System.out.println("List of users: ");
            for (Person person : users) {
                System.out.println(person.getUsername() + " - " + person.getRole());
            }
        } else {
            System.out.println("You don't have permission for that!");
        }
    }

    @Override
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = "", username, password, role;
                if (parts.length == 4) {
                    id = parts[0];
                    username = parts[1];
                    password = parts[2];
                    role = parts[3];
                } else {
                    username = parts[0];
                    password = parts[1];
                    role = parts[2];
                }

                if (role.equals("ADMIN")) {
                    users.add(new Administrator(username, password));
                } else {
//                    String[] bookings = parts[3].split(";");
                    users.add(new User(username, password, id));
//                    for (String book : bookings) {
//                        findByUsername(username).getBookHistory().add(book);
//                    }
                }
            }
            System.out.println("Load from the file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Person person : users) {
                String line="";
                if (person instanceof User) {
                    line = ((User) person).getId()+",";
                }
                line += person.getUsername() + "," + person.getPassword() + "," + person.getRole();
//                if (person instanceof User user && !user.getBookHistory().isEmpty()) {
//                    line += "," + String.join(";", user.getBookHistory());
//                }
                writer.write(line);
                writer.newLine();

            }
            System.out.println("Save to file");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public User getLastUser() {
        if (users.getLast() instanceof User lastUser) {
            return lastUser;
        }

        return null;
    }


}
