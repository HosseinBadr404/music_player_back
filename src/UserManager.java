import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;

    public UserManager() {
        users = new ArrayList<>();
        initializeMockUsers();
    }

    private void initializeMockUsers() {
        users.add(new User("Ali Rezaei", "ali@example.com", "password123", 1.0));
        users.add(new User("Sara Ahmadi", "sara@example.com", "password456", 20.0));
    }

    public boolean login(String email, String password) {
        return users.stream()
                .anyMatch(user -> user.getEmail().equals(email) && user.getPassword().equals(password));
    }

    public boolean signUp(String name, String email, String password) {
        if (users.stream().anyMatch(user -> user.getEmail().equals(email))) {
            return false;
        }
        users.add(new User(name, email, password, 10.0));
        return true;
    }

    public User getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    public boolean addBalance(String userEmail, double amount) {
        User user = getUserByEmail(userEmail);
        if (user == null || amount <= 0) return false;
        user.addBalance(amount);
        return true;
    }

    public boolean activateSubscription(String userEmail, int days) {
        User user = getUserByEmail(userEmail);
        if (user == null) return false;
        user.activatePremiumSubscription(days);
        return true;
    }
}