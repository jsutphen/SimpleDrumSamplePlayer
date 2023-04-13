import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static List<SoundPlayer> sounds = new LinkedList<>();

    public volatile static Clock clock = new Clock();

    static {
        try {
            sounds.add(new SoundPlayer("Kick Alecia DS.wav"));
            sounds.add(new SoundPlayer("ClosedHH Alessya DS.wav"));
            sounds.add(new SoundPlayer("Snare Alessya DS.wav"));
            sounds.add(new SoundPlayer("Perc Alessya DS 1.wav"));
            sounds.add(new SoundPlayer("Perc Alessya DS 2.wav"));
            sounds.add(new SoundPlayer("Timpani Alessya DS 1.wav"));
            sounds.add(new SoundPlayer("Timpani Alessya DS 2.wav"));
            sounds.add(new SoundPlayer("Timpani Alessya DS 3.wav"));

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        GUI gui = new GUI();

    }
}