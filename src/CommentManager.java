import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentManager {
    private final UserManager userManager;

    public CommentManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public boolean addComment(String content, String userEmail, long musicId) {
        if (!userManager.userExists(userEmail) || content == null || content.trim().isEmpty()) {
            System.out.println("Add comment failed: Invalid user or empty content");
            return false;
        }
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Comment (content, user_email, music_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, content);
            stmt.setString(2, userEmail);
            stmt.setLong(3, musicId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding comment: " + e.getMessage());
            return false;
        }
    }

    public List<Comment> getCommentsByMusic(long musicId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, content, likes, dislikes, user_email, music_id FROM Comment WHERE music_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, musicId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment(rs.getString("content"), rs.getString("user_email"));
                comment.setId(rs.getLong("id"));
                comment.setLikes(rs.getInt("likes"));
                comment.setDislikes(rs.getInt("dislikes"));
                comment.setMusicId(rs.getLong("music_id"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            System.out.println("Error getting comments: " + e.getMessage());
        }
        return comments;
    }

    public boolean likeComment(long commentId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Comment SET likes = likes + 1 WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, commentId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error liking comment: " + e.getMessage());
            return false;
        }
    }

    public boolean dislikeComment(long commentId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Comment SET dislikes = dislikes + 1 WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, commentId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error disliking comment: " + e.getMessage());
            return false;
        }
    }
}