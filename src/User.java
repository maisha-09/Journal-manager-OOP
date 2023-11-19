import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User {
    private String username;
    private String password;

    // Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Method to register a new user
    public static void registerUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        // Create a new user object
        User newUser = new User(username, password);

        // Save user data to a text file (you need to implement this)
        saveUserToFile(newUser);

        System.out.println("User registered successfully!");
    }

    // Method to simulate saving user data to a text file (to be replaced with
    // actual file I/O)
    private static void saveUserToFile(User user) {
        try (FileWriter writer = new FileWriter("users.txt", true)) {
            // Append user data to the file
            writer.write(user.getUsername() + "," + user.getPassword() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to simulate user login
    public static User loginUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        String enteredUsername = scanner.nextLine();

        System.out.print("Enter your password: ");
        String enteredPassword = scanner.nextLine();

        // Check if the entered credentials match any registered users
        List<User> users = readUsersFromFile();
        for (User user : users) {
            if (user.getUsername().equals(enteredUsername) && user.getPassword().equals(enteredPassword)) {
                System.out.println("Login successful!");
                return user;
            }
        }

        System.out.println("Invalid credentials. Login failed.");
        return null;
    }

    // Method to read user data from a text file
    private static List<User> readUsersFromFile() {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String username = parts[0];
                String password = parts[1];
                users.add(new User(username, password));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}
