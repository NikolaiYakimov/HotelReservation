package Users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Person {
    private String username;
    private String password;
    private String role;
    private Pattern passPattern=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    public Person(String username, String password,String role) {
        if(username == null|| username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty!");
        }

        Matcher matcher=passPattern.matcher(password);

        if(matcher.matches()) {
            this.username = username;
            this.password = password;
            this.role=role;
        }else {
            System.out.println("Password must be at least 8 characters long, " +
                    "contain at least one digit, one lowercase letter, one uppercase letter, " +
                    "one special character, and must not contain whitespace.");
        }

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public abstract void viewProfile();
    public abstract String getRole();

}
