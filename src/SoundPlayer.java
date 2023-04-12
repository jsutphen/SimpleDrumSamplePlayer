import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
    public String filename;
    private final URL URL;
    private final int NUMBER_OF_CLIPS = 16;
    private AudioInputStream[] ais = new AudioInputStream[NUMBER_OF_CLIPS];
    private DataLine.Info[] info = new DataLine.Info[NUMBER_OF_CLIPS];
    private Clip[] clips = new Clip[NUMBER_OF_CLIPS];
    public boolean[] pattern = {false, false, false, false,
                                false, false, false, false,
                                false, false, false, false,
                                false, false, false, false};


    public SoundPlayer(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.filename = filename;
        URL = SoundPlayer.class.getResource("resources/" + filename);
        for (int i = 0; i < NUMBER_OF_CLIPS; i++) {
            assert URL != null;
            ais[i] = AudioSystem.getAudioInputStream(URL);
            info[i] = new DataLine.Info(Clip.class, ais[i].getFormat());
            clips[i] = (Clip) AudioSystem.getLine(info[i]);
            clips[i].open(ais[i]);
            FloatControl volume = (FloatControl) clips[i].getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-10f);
        }
        // make soundPlayer listen to clock signals
        int clipSelect = 0;
        while (true) {
            clips[clipSelect].stop();
            clips[clipSelect].flush();
            clips[clipSelect].setFramePosition(0);
            if (pattern[Clock.clock]) {
                clips[clipSelect].start();
            }
            int holdClockValue = Clock.clock;
            while (Clock.clock == holdClockValue) {
                // wait until clock is updated
            }
            clipSelect = (clipSelect + 1) % clips.length;
        }
    }
}
