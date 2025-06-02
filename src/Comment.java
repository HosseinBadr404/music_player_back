public class Comment {
    private String content;
    private int likes;
    private int dislikes;
    private String userEmail;

    public Comment(String content, String userEmail) {
        this.content = content;
        this.userEmail = userEmail;
        this.likes = 0;
        this.dislikes = 0;
    }

    public String getContent() { return content; }
    public int getLikes() { return likes; }
    public int getDislikes() { return dislikes; }
    public String getUserEmail() { return userEmail; }

    public void incrementLikes() { this.likes++; }
    public void incrementDislikes() { this.dislikes++; }
}