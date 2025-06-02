import java.time.LocalDateTime;

public class Transaction {
    private String userEmail;
    private String musicTitle;
    private double amount;
    private LocalDateTime timestamp;
    private boolean isSubscription;

    public Transaction(String userEmail, String musicTitle, double amount, boolean isSubscription) {
        this.userEmail = userEmail;
        this.musicTitle = musicTitle;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.isSubscription = isSubscription;
    }

    public String getUserEmail() { return userEmail; }
    public String getMusicTitle() { return musicTitle; }
    public double getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean isSubscription() { return isSubscription; }
}