import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentManager {
    private List<Comment> comments;

    public CommentManager() {
        comments = new ArrayList<>();
        initializeMockComments();
    }

    private void initializeMockComments() {
        comments.add(new Comment("Great song!", "ali@example.com"));
        comments.add(new Comment("Not bad.", "sara@example.com"));
    }

    public List<Comment> getCommentsByMusic(String musicTitle) {
        return comments.stream()
                .filter(comment -> comment.getContent().contains(musicTitle))
                .collect(Collectors.toList());
    }

    public void addComment(String content, String userEmail, String musicTitle) {
        comments.add(new Comment(content, userEmail));
    }

    public void likeComment(Comment comment) {
        comment.incrementLikes();
    }

    public void dislikeComment(Comment comment) {
        comment.incrementDislikes();
    }
}