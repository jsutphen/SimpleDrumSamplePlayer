public class Clock {
    public int clock = 0;

    private static final int NUMBER_OF_STEPS = 16;

    private static boolean keepPlaying = false;

    private static int bpm = 120;

    private void setBpm(int newBpm) {
        bpm = newBpm;
    }


    public void startClock(int start) {

        clock = start;
        keepPlaying = true;
        Thread clockThread = new Thread( () -> {
            while(true) {
                if(keepPlaying) {
                    try {
                        Thread.sleep((60 * 1000) / bpm / 4);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    clock = (clock + 1) % NUMBER_OF_STEPS;


                    //* to check whether the clock is updated correctly
                    System.out.println("clock updated: " + clock); //*/

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
