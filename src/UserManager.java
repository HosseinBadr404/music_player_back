import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserManager {
    private static final String USERS_FILE = "users.txt";
    private List<User> users;
    private MusicManager musicManager;

    public UserManager() {
        users = new ArrayList<>();
        loadUsers();
    }

    public void setMusicManager(MusicManager musicManager) {
        this.musicManager = musicManager;
    }

    private void loadUsers() {
        List<String> lines = FileUtil.readLines(USERS_FILE);
        for (String line : lines) {
            try {
                String[] parts = line.split("\\|");
                if (parts.length >= 4) {
                    String name = parts[0];
                    String email = parts[1];
                    String password = parts[2];
                    double balance = Double.parseDouble(parts[3]);
                    User user = new User(name, email, password, balance);
                    if (parts.length > 4 && !parts[4].isEmpty()) {
                        String purchasedMusic = parts[4];
                        if (!purchasedMusic.isEmpty()) {
                            String[] musicTitles = purchasedMusic.split(",");
                            for (String title : musicTitles) {
                                user.addPurchasedMusic(title.trim());
                            }
                        }
                    }
                    if (parts.length > 5 && !parts[5].isEmpty()) {
                        user.activatePremiumSubscription(LocalDate.parse(parts[5]));
                    }
                    users.add(user);
                }
            } catch (Exception e) {
                System.out.println("Error parsing user line: " + line + ", error: " + e.getMessage());
            }
        }
    }

    public void saveUsers() {
        List<String> lines = users.stream().map(user -> {
            String purchasedMusic = String.join(",", user.getPurchasedMusic());
            String subscriptionEnd = user.getSubscriptionEndDate() != null ? user.getSubscriptionEndDate().toString() : "";
            return String.format("%s|%s|%s|%.2f|%s|%s",
                    user.getName(), user.getEmail(), user.getPassword(),
                    user.getBalance(), purchasedMusic, subscriptionEnd);
        }).collect(Collectors.toList());
        FileUtil.writeLines(USERS_FILE, lines);
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
        saveUsers();
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
        saveUsers();
        return true;
    }

    public boolean userExists(String userEmail) {
        return users.stream().anyMatch(user -> user.getEmail().equals(userEmail));
    }

    public boolean hasPurchased(String userEmail, String musicTitle) {
        User user = getUserByEmail(userEmail);
        if (user == null) return false;
        return user.getPurchasedMusic().contains(musicTitle);
    }

    public boolean canAccessMusic(String userEmail, String musicTitle) {
        User user = getUserByEmail(userEmail);
        if (user == null || musicManager == null) return false;
        Music music = musicManager.getMusicByTitle(musicTitle);
        if (music == null) return false;
        return music.isFree() || user.isSubscriptionActive() || hasPurchased(userEmail, musicTitle);
    }

    public void activateSubscription(String userEmail, int days) {
        User user = getUserByEmail(userEmail);
        if (user != null) {
            user.activatePremiumSubscription(days);
            saveUsers();
        }
    }

    public boolean updateUserInfo(String oldEmail, String newName, String newEmail) {
        User user = getUserByEmail(oldEmail);
        if (user == null) return false;

        if (!newEmail.equals(oldEmail) && users.stream().anyMatch(u -> u.getEmail().equals(newEmail))) {
            return false;
        }

        try {
            java.lang.reflect.Field nameField = User.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(user, newName);

            java.lang.reflect.Field emailField = User.class.getDeclaredField("email");
            emailField.setAccessible(true);
            emailField.set(user, newEmail);

            saveUsers();
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Error updating user info: " + e.getMessage());
            return false;
        }
    }
}