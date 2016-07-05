package others;

/**
 * Created by raymond on 6/28/16.
 */
public class Philosopher implements Runnable {
    private final Chopstick first;
    private final Chopstick second;
    private final int id;

    public Philosopher(Chopstick first, Chopstick second, int id) {
        this.first = first;
        this.second = second;
        this.id = id;
    }

    private void ponder() throws InterruptedException {
        Thread.sleep(((int) Math.random() * 10) + 10);
    }

    public void run() {
        try {
            while (true) {
                ponder(); // thinking
                synchronized (first) {
                    System.out.println("phil"+this.id+"take the "+ first.id + "th chop");
                    ponder();
                    synchronized (second) {
                        System.out.println("phil"+this.id+"take the "+ second.id + "th chop and eat");
                        ponder(); // eating
                    }
                }

                System.out.println("phil finished meal" + this.id + "release the" + first.id + "th and " +second.id+ "th chop");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
}