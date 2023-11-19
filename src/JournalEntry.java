import java.text.SimpleDateFormat;
import java.util.Date;

public class JournalEntry extends Entry {
    private String username;
    private String entryFilePath; // Path to the entry file
    private Date dateTime;

    public JournalEntry(String title, String content, String username, String entryFilePath, Date dateTime) {
        super(title, content);
        this.username = username;
        this.entryFilePath = entryFilePath;
        this.dateTime = dateTime;
    }

    @Override
    public void displayEntry() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = dateFormat.format(dateTime);

        System.out.println("Title: " + getTitle());
        System.out.println("Content: " + getContent());
        System.out.println("Username: " + username);
        System.out.println("Date and Time: " + formattedDateTime);
        System.out.println("------");
    }

    public String getUsername() {
        return username;
    }

    public String getEntryFilePath() {
        return entryFilePath;
    }

    public Date getDateTime() {
        return dateTime;
    }
}