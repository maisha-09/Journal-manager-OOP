import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class JournalManager {
    private User currentUser;
    private List<JournalEntry> entries;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final String DATABASE_DIR = "database";

    public JournalManager(User currentUser) {
        this.currentUser = currentUser;
        this.entries = new ArrayList<>();
        loadEntries(); // Load entries when creating the JournalManager
    }

    public void createEntry(String title, String content) {
        String entryFilePath = getEntryFilePath(title);
        JournalEntry newEntry = new JournalEntry(title, content, currentUser.getUsername(), entryFilePath, new Date());
        entries.add(newEntry);
        saveEntryToFile(newEntry); // Save the new entry to a file
        System.out.println("Entry created successfully!");
    }

    public void editEntry(String title, String newContent) {
        JournalEntry entryToEdit = findEntryByTitle(title);
        if (entryToEdit != null && entryToEdit.getUsername().equals(currentUser.getUsername())) {
            entryToEdit.displayEntry();
            entryToEdit.setContent(newContent);
            saveEntryToFile(entryToEdit); // Save the updated entry to a file
            System.out.println("Entry edited successfully!");
        } else {
            System.out.println("Entry not found or you don't have permission to edit.");
        }
    }

    public void listEntries() {
        if (entries.isEmpty()) {
            System.out.println("No entries available.");
        } else {
            for (JournalEntry entry : entries) {
                entry.displayEntry();
            }
        }
    }

    public void searchEntries(String keyword) {
        boolean found = false;
        for (JournalEntry entry : entries) {
            if (entry.getTitle().contains(keyword) && entry.getUsername().equals(currentUser.getUsername())) {
                entry.displayEntry();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching entries found.");
        }
    }

    public void deleteEntry(String title) {
        JournalEntry entryToDelete = findEntryByTitle(title);
        if (entryToDelete != null && entryToDelete.getUsername().equals(currentUser.getUsername())) {
            entries.remove(entryToDelete);
            deleteEntryFile(entryToDelete); // Delete the corresponding file
            System.out.println("Entry deleted successfully!");
        } else {
            System.out.println("Entry not found or you don't have permission to delete.");
        }
    }

    // Save a journal entry to a text file
    private void saveEntryToFile(JournalEntry entry) {
        try {
            File entryFile = new File(entry.getEntryFilePath());
            File entryDirectory = entryFile.getParentFile();

            if (!entryDirectory.exists() && !entryDirectory.mkdirs()) {
                throw new IOException("Failed to create directory: " + entryDirectory);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(entryFile))) {
                writer.write(entry.getTitle() + "\n");
                writer.write(entry.getContent() + "\n");
                writer.write(DATE_FORMAT.format(entry.getDateTime()) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Delete a journal entry file
    private void deleteEntryFile(JournalEntry entry) {
        File entryFile = new File(entry.getEntryFilePath());
        if (entryFile.exists()) {
            if (entryFile.delete()) {
                System.out.println("Entry file deleted successfully.");
            } else {
                System.out.println("Unable to delete entry file.");
            }
        }
    }

    // Load journal entries from files
    private void loadEntries() {
        File userDirectory = new File(DATABASE_DIR, currentUser.getUsername());
        if (userDirectory.exists() && userDirectory.isDirectory()) {
            File[] entryFiles = userDirectory.listFiles();
            if (entryFiles != null) {
                for (File entryFile : entryFiles) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(entryFile))) {
                        String title = reader.readLine();
                        String content = reader.readLine();
                        String dateTimeStr = reader.readLine();

                        if (dateTimeStr != null && !dateTimeStr.isEmpty()) {
                            Date dateTime = DATE_FORMAT.parse(dateTimeStr);
                            entries.add(new JournalEntry(title, content, currentUser.getUsername(), entryFile.getPath(),
                                    dateTime));
                        } else {
                            System.out
                                    .println("Invalid date and time format in the entry file: " + entryFile.getName());
                        }
                    } catch (IOException | java.text.ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("No previous entries found.");
        }
    }

    // Find a journal entry by title
    private JournalEntry findEntryByTitle(String title) {
        for (JournalEntry entry : entries) {
            if (entry.getTitle().equals(title)) {
                return entry;
            }
        }
        return null;
    }

    // Get the file path for a new entry
    private String getEntryFilePath(String title) {
        return new File(DATABASE_DIR,
                currentUser.getUsername() + File.separator + title.replaceAll("\\s", "_") + ".txt").getPath();
    }
}