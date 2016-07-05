package others;

/**
 * Created by raymond on 6/28/16.
 */

public class Dinner {
    public static void main(String[] args) throws Exception {
        final Philosopher[] philosophers = new Philosopher[5];
        Chopstick[] chopsticks = new Chopstick[philosophers.length];
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Chopstick(i);
        }
        for (int i = 0; i < philosophers.length; i++) {
            Chopstick first = chopsticks[i];
            Chopstick second = chopsticks[(i + 1) % chopsticks.length];
            if (i == philosophers.length-1) {
                philosophers[i] = new Philosopher(second, first,i);
            } else {
                philosophers[i] = new Philosopher(first, second,i);
            }
            Thread t = new Thread(philosophers[i], "Phil " + (i + 1));
            t.start();
        }
    }
}
