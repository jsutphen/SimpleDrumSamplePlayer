public class Clock {
    private static final int NUMBER_OF_STEPS = 16;
    private static boolean keepPlaying = false;
    private static int bpm = 120;
    public int clock = 0;

    private void setBpm(int newBpm) {
        bpm = newBpm;
    }


    public void startClock(int start) {

        clock = start;
        synchronized (this) {
            notifyAll();
        }

        keepPlaying = true;
        Thread clockThread = new Thread(() -> {
            while (true) {
                if (keepPlaying) {
                    try {
                        Thread.sleep((60 * 1000) / bpm / 4);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    clock = (clock + 1) % NUMBER_OF_STEPS;
                    synchronized (this) {
                        notifyAll();
                    }
                }
            }
        });
        clockThread.start();
    }

    public void stopClock() {
        keepPlaying = false;
    }
}
