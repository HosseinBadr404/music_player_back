import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionManager {
    private List<Transaction> transactions;
    private UserManager userManager;
    private MusicManager musicManager;

    public TransactionManager(UserManager userManager, MusicManager musicManager) {
        this.transactions = new ArrayList<>();
        this.userManager = userManager;
        this.musicManager = musicManager;
    }

    public boolean purchaseMusic(String userEmail, String musicTitle) {
        User user = userManager.getUserByEmail(userEmail);
        Music music = musicManager.getMusicByTitle(musicTitle);

        if (user == null || music == null) {
            return false;
        }

        if (music.isFree() || user.isSubscriptionActive() || user.hasPurchased(musicTitle)) {
            user.addPurchasedMusic(musicTitle);
            music.incrementDownloads();
            transactions.add(new Transaction(userEmail, musicTitle, 0.0, false));
            return true;
        }

        if (user.getBalance() >= music.getPrice()) {
            boolean success = user.deductBalance(music.getPrice());
            if (success) {
                user.addPurchasedMusic(musicTitle);
                music.incrementDownloads();
                transactions.add(new Transaction(userEmail, musicTitle, music.getPrice(), false));
                return true;
            }
        }
        return false;
    }

    public boolean purchaseSubscription(String userEmail, double amount) {
        User user = userManager.getUserByEmail(userEmail);
        if (user == null) return false;

        if (user.getBalance() >= amount) {
            boolean success = user.deductBalance(amount);
            if (success) {
                userManager.activateSubscription(userEmail, 30);
                transactions.add(new Transaction(userEmail, "Premium Subscription", amount, true));
                return true;
            }
        }
        return false;
    }

    public List<Transaction> getTransactionsByUser(String userEmail) {
        return transactions.stream()
                .filter(t -> t.getUserEmail().equals(userEmail))
                .collect(Collectors.toList());
    }
}