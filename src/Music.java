public class Music {
    private String title;
    private String artist;
    private String image;
    private double price;
    private int downloads;
    private double rating;
    private String category;
    private String audioFileName;

    public Music(String title, String artist, String image, double price, 
                 int downloads, double rating, String category, String audioFileName) {
        this.title = title;
        this.artist = artist;
        this.image = image;
        this.price = price;
        this.downloads = downloads;
        this.rating = rating;
        this.category = category;
        this.audioFileName = audioFileName;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getImage() { return image; }
    public double getPrice() { return price; }
    public int getDownloads() { return downloads; }
    public double getRating() { return rating; }
    public String getCategory() { return category; }
    public String getAudioFileName() { return audioFileName; }

    public boolean isFree() { return price <= 0; }
    public void incrementDownloads() { this.downloads++; }
}