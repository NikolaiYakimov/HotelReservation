package Users;

import model.StoreableAndLoadable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements StoreableAndLoadable {
    private final String FILE_PATH="D:\\JavaProjects\\HotelRoomReservetioSystem\\src\\Data\\UserStorageFile";
    private final List<Person> users=new ArrayList<>();
    public UserManager(){
        loadFromFile();
    }

    @Override
    public void loadFromFile() {
        try(BufferedReader reader=new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while((line=reader.readLine())!=null){
                String[] parts=line.split(",");
                String username=parts[0];
                String password=parts[1];
                String role=parts[2];

                if(role.equals("ADMIN")) {
                    users.add(new Administrator(username, password));
                }
                else{
                    users.add(new User(username,password));
                }
            }
            System.out.println("Load from the file");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveToFile() {
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(FILE_PATH))){
            for(Person person:users){
                String line=person.getUsername()+","+person.getPassword()+","+person.getRole();
                writer.write(line);
                writer.newLine();

            }
            System.out.println("Save to file");
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
