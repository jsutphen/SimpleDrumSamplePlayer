import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
    public String filename;
    private final URL URL;
    private static final int NUMBER_OF_CLIPS = 16;
    private AudioInputStream[] ais = new AudioInputStream[NUMBER_OF_CLIPS];
    private DataLine.Info[] info = new DataLine.Info[NUMBER_OF_CLIPS];
    private Clip[] clips = new Clip[NUMBER_OF_CLIPS];

    private boolean keepPlaying;
    public boolean[] pattern = {false, false, false, false,
                                false, false, false, false,
                                false, false, false, false,
                                false, false, false, false};
    private int patternPosition = 0;

    public SoundPlayer(String filename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        this.filename = filename;
        URL = SoundPlayer.class.getResource("resources/" + filename);
        for(int i = 0; i < NUMBER_OF_CLIPS; i++) {
            assert URL != null;
            ais[i] = AudioSystem.getAudioInputStream(URL);
            info[i] = new DataLine.Info(Clip.class, ais[i].getFormat());
            clips[i] = (Clip) AudioSystem.getLine(info[i]);
            clips[i].open(ais[i]);
            FloatControl volume = (FloatControl) clips[i].getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-10f);
        }
    }
    public void play() throws IOException, LineUnavailableException, InterruptedException {
        keepPlaying = true;
        int clipSelect = 0;
        while(keepPlaying) {
            clips[clipSelect].stop();
            clips[clipSelect].flush();
            clips[clipSelect].setFramePosition(0);
            if(pattern[patternPosition]) {
                clips[clipSelect].start();
            }
            Thread.sleep(125);
            patternPosition = (patternPosition + 1) % 16;
            clipSelect = (clipSelect + 1) % clips.length;
        }
    }
    public void pause(){
        keepPlaying = false;
        for(Clip clip : clips) {
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
        }
        patternPosition = 0;
    }
}
