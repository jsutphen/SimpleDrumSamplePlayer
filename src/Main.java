import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {
    public static SoundPlayer bassDrum;
    public static SoundPlayer hiHat;
    public static SoundPlayer snare;

    static {
        try {
            bassDrum = new SoundPlayer("Kick Alecia DS.wav");
            hiHat = new SoundPlayer("ClosedHH Alessya DS.wav");
            snare = new SoundPlayer("Snare Alessya DS.wav");

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        GUI gui = new GUI();

    }
}