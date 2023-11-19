import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User loggedInUser = null;

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    User.registerUser();
                    break;
                case 2:
                    loggedInUser = User.loginUser();
                    if (loggedInUser != null) {
                        System.out.println("Welcome, " + loggedInUser.getUsername() + "!");
                        JournalManager journalManager = new JournalManager(loggedInUser);
                        handleJournalMenu(journalManager, scanner);
                    }
                    break;
                case 3:
                    System.out.println("Exiting the Journal Manager. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void handleJournalMenu(JournalManager journalManager, Scanner scanner) {
        while (true) {
            System.out.println("1. Create a New Entry");
            System.out.println("2. Edit Entry");
            System.out.println("3. List Entries");
            System.out.println("4. Search Entries");
            System.out.println("5. Delete Entries");
            System.out.println("6. Logout");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the title of the new entry: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter the content of the new entry: ");
                    String newContent = scanner.nextLine();
                    journalManager.createEntry(newTitle, newContent);
                    break;
                case 2:
                    System.out.print("Enter the title of the entry to edit: ");
                    String editTitle = scanner.nextLine();
                    System.out.print("Enter the new content: ");
                    String editContent = scanner.nextLine();
                    journalManager.editEntry(editTitle, editContent);
                    break;
                case 3:
                    journalManager.listEntries();
                    break;
                case 4:
                    System.out.print("Enter a keyword to search entries: ");
                    String keyword = scanner.nextLine();
                    journalManager.searchEntries(keyword);
                    break;
                case 5:
                    System.out.print("Enter the title of the entry to delete: ");
                    String deleteTitle = scanner.nextLine();
                    journalManager.deleteEntry(deleteTitle);
                    break;
                case 6:
                    System.out.println("Logging out. Goodbye!");
                    return; // Return from the method to go back to the main menu
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
