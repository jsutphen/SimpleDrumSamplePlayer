import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class GUI {
    final int NUMBER_OF_STEPS = 16;
    private java.util.List<JCheckBox[]> instrumentCheckBoxes = new LinkedList<>();
    private java.util.List<JPanel> instrumentStepsPanels = new LinkedList<>();
    private java.util.List<GridBagConstraints> instrumentStepsConstraints = new LinkedList<>();
    private JCheckBox playPause = new JCheckBox("Play / Pause");
    private JFrame frame = new JFrame();
    private java.util.List<JLabel> instrumentLabels = new LinkedList<>();
    private java.util.List<GridBagConstraints> instrumentLabelConstraints = new LinkedList<>();


    public GUI() {

        // make new checkBoxes for every sound
        for(int instrumentIndex = 0; instrumentIndex < Main.sounds.size(); instrumentIndex++) {

            instrumentStepsPanels.add(new JPanel());

            instrumentCheckBoxes.add(new JCheckBox[NUMBER_OF_STEPS]);

            for(int stepsIndex = 0; stepsIndex < NUMBER_OF_STEPS; stepsIndex++) {
                instrumentCheckBoxes.get(instrumentIndex)[stepsIndex] = new JCheckBox();
            }

            // also add a label to the list of labels for every instrument
            instrumentLabels.add(new JLabel(Main.sounds.get(instrumentIndex).filename));
        }

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.setSize(800,500);

        playPause.addActionListener((e) -> {
            if (playPause.isSelected()) {
                Main.clock.startClock(0);
                System.out.println("Called Main.clock.startClock(0)");
            } else {
                // stop audio playback
                Main.clock.stopClock();
            }
        });

        for (int stepIndex = 0; stepIndex < NUMBER_OF_STEPS; stepIndex++) {
            final int finalStepIndex = stepIndex;
            for(int instrumentIndex = 0; instrumentIndex < Main.sounds.size(); instrumentIndex++) {
                int finalInstrumentIndex = instrumentIndex;

                // for every step, for every instrument, add an action listener which maps the checkboxes to steps representation in the sounds
                instrumentCheckBoxes.get(finalInstrumentIndex)[finalStepIndex].addActionListener((e) -> {
                    Main.sounds.get(finalInstrumentIndex).pattern[finalStepIndex] = instrumentCheckBoxes.get(finalInstrumentIndex)[finalStepIndex].isSelected();

                    //* to check if actionListener changes the correct pattern:
                    System.out.println("pattern of instrument " + "'" + Main.sounds.get(finalInstrumentIndex).filename + "'" + " changed to: " +
                            Arrays.toString(Main.sounds.get(finalInstrumentIndex).pattern)); //*/
                });

                // on every step, add the respective checkboxes for every instrument
                instrumentStepsPanels.get(instrumentIndex).add(instrumentCheckBoxes.get(instrumentIndex)[finalStepIndex]);
            }

        }

        for(int instrumentIndex = 0; instrumentIndex < Main.sounds.size(); instrumentIndex++) {
            GridBagConstraints instrumentLabelConstraints = new GridBagConstraints();
            instrumentLabelConstraints.gridx = 0;
            instrumentLabelConstraints.gridy = instrumentIndex;
            this.instrumentLabelConstraints.add(instrumentLabelConstraints);

            GridBagConstraints instrumentStepsConstraints = new GridBagConstraints();
            instrumentStepsConstraints.gridx = 1;
            instrumentStepsConstraints.gridy = instrumentIndex;
            this.instrumentStepsConstraints.add(instrumentStepsConstraints);
        }

        for(int instrumentIndex = 0; instrumentIndex < Main.sounds.size(); instrumentIndex++) {
            frame.add(instrumentLabels.get(instrumentIndex), instrumentLabelConstraints.get(instrumentIndex));
            frame.add(instrumentStepsPanels.get(instrumentIndex), instrumentStepsConstraints.get(instrumentIndex));
        }

        GridBagConstraints playPauseConstraints = new GridBagConstraints();
        playPauseConstraints.gridx = 0;
        playPauseConstraints.gridy = Main.sounds.size() + 1;
        playPauseConstraints.gridwidth = 2;

        frame.add(playPause, playPauseConstraints);

        frame.setVisible(true);
    }
}
