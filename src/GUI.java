import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GUI {
    final int NUMBER_OF_STEPS = 16;
    private JCheckBox[] bassDrumCheckBox = new JCheckBox[NUMBER_OF_STEPS];
    private JCheckBox[] hiHatCheckBox = new JCheckBox[NUMBER_OF_STEPS];
    private JCheckBox[] snareDrumCheckBox = new JCheckBox[NUMBER_OF_STEPS];
    private JPanel stepsPanel = new JPanel(new GridLayout(3,NUMBER_OF_STEPS));
    private JPanel playPanel = new JPanel();
    private JCheckBox playPause = new JCheckBox("Play / Pause");


    public GUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2,1));
        frame.setSize(500,200);
        for(int i = 0; i < NUMBER_OF_STEPS; i++) {
            bassDrumCheckBox[i] = new JCheckBox();
            snareDrumCheckBox[i] = new JCheckBox();
            hiHatCheckBox[i] = new JCheckBox();
        }
        playPause.addActionListener((e) -> {
            if (playPause.isSelected()) {
                new Thread(() -> {
                    try {
                        Main.bassDrum.play();
                    } catch (IOException | LineUnavailableException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
                new Thread(() -> {
                    try {
                        Main.hiHat.play();
                    } catch (IOException | LineUnavailableException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
                new Thread(() -> {
                    try {
                        Main.snare.play();
                    } catch (IOException | LineUnavailableException | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
            } else {
                // stop audio playback
                Main.bassDrum.pause();
                Main.hiHat.pause();
                Main.snare.pause();
            }
        });
        for (int i = 0; i < NUMBER_OF_STEPS; i++) {
            final int index = i;
            bassDrumCheckBox[index].addActionListener((e) -> {
                Main.bassDrum.pattern[index] = bassDrumCheckBox[index].isSelected();
            });
            snareDrumCheckBox[index].addActionListener((e) -> {
                Main.snare.pattern[index] = snareDrumCheckBox[index].isSelected();
            });
            hiHatCheckBox[index].addActionListener((e) -> {
                Main.hiHat.pattern[index] = hiHatCheckBox[index].isSelected();
            });
        }
        for(JCheckBox bassDrumCheckBox : bassDrumCheckBox) {
            stepsPanel.add(bassDrumCheckBox);
        }
        for(JCheckBox hiHatCheckBox : hiHatCheckBox) {
            stepsPanel.add(hiHatCheckBox);
        }
        for(JCheckBox snareDrumCheckBox : snareDrumCheckBox) {
            stepsPanel.add(snareDrumCheckBox);
        }
        playPanel.add(playPause);
        frame.add(stepsPanel);
        frame.add(playPanel);
        frame.setVisible(true);
    }
}
