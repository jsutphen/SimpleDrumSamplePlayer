public class Clock {
    public static int clock = 0;

    private static int numberOfSteps = 16;

    private static boolean keepPlaying;

    private static int bpm = 120;

    private void setBpm(int newBpm) {
        bpm = newBpm;
    }

    private static void startClock(int start) throws InterruptedException {
        clock = start;
        while(keepPlaying) {
            Thread.sleep((60 * 1000) / bpm );
            clock = (clock + 1) % numberOfSteps;
        }
    }

    public static void stopClock() {
        keepPlaying = false;
    }
}
