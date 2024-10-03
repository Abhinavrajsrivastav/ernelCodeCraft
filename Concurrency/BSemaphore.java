// Description: Binary semaphore implementation
class BSemaphore {
    private int S = 1;

    public synchronized void down() {
        while (S == 0){
            try {
                wait(); 
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        S = 0;
    }

    public synchronized void up() {
        S = 1;
        notify(); 
    }
}

class P extends Thread {
    private String name;
    private BSemaphore semaphore;

    public P(String name, BSemaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
    }

    public void run() {
        System.out.println("Thread " + name + " is trying to acquire the semaphore...");
        semaphore.down(); 
        System.out.println("Thread " + name + " has acquired the semaphore!");

        try {
            Thread.sleep(2000); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Thread " + name + " is releasing the semaphore...");
        semaphore.up();
    }
}

class Main {
    public static void main(String[] args) {
        BSemaphore semaphore = new BSemaphore(); 
        P p1 = new P("P1", semaphore);
        P p2 = new P("P2", semaphore);

        p1.start(); // Start thread P1
        p2.start(); // Start thread P2
    }
}
