package others.reader_writer;

/**
 * Created by raymond on 6/28/16.
 */
public class Database {
    private int readers; // number of active readers

    /**
     * Initializes this database.
     */
    public Database() {
        this.readers = 0;
    }

    /**
     * Read from this database.
     *
     * @param number Number of the reader.
     */
    public void read(int number) {
        synchronized (this) {
            this.readers++;
            System.out.println("Reader " + number + " starts reading.");
        }

        final int DELAY = 5000;
        try {
            Thread.sleep((int) (Math.random() * DELAY));
        } catch (InterruptedException e) {
        }

        synchronized (this) {
            System.out.println("Reader " + number + " stops reading.");
            this.readers--;
            if (this.readers == 0) {
                this.notifyAll();
            }
        }
    }

    /**
     * Writes to this database.
     *
     * @param number Number of the Writer.
     */
    public synchronized void write(int number) {
        while (this.readers != 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
            }
        }
        System.out.println("Writer " + number + " starts writing.");

        final int DELAY = 5000;
        try {
            Thread.sleep((int) (Math.random() * DELAY));
        } catch (InterruptedException e) {
        }

        System.out.println("Writer " + number + " stops writing.");
        this.notifyAll();
    }
}
