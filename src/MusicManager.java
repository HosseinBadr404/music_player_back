import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MusicManager {
    private List<Music> musicList;

    public MusicManager() {
        musicList = new ArrayList<>();
        initializeMockMusic();
    }

    private void initializeMockMusic() {
        musicList.add(new Music("Symphony No. 9", "Beethoven", "assets/images/c1.jpg", 
                3.99, 12500, 4.9, "CLASSIC", "sample_music.mp3"));
        musicList.add(new Music("Shape of You", "Ed Sheeran", "assets/images/c1.jpg", 
                1.49, 45000, 4.5, "POP", "sample_music.mp3"));
        musicList.add(new Music("Für Elise", "Beethoven", "assets/images/c1.jpg", 
                0.0, 23000, 4.5, "CLASSIC", "sample_music.mp3"));
    }

    public List<Music> getAllMusic() {
        return new ArrayList<>(musicList);
    }

    public List<Music> getMusicByCategory(String category) {
        return musicList.stream()
                .filter(music -> music.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    public Music getMusicByTitle(String title) {
        return musicList.stream()
                .filter(music -> music.getTitle().equals(title))
                .findFirst()
                .orElse(null);
    }
}