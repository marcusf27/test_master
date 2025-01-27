package lift;

import java.util.concurrent.Semaphore;

public class Monitor {
    private int[] toEnter;
    private int[] toExit;
    private int floor;
    private int inLift = 0;
    private int MAX;
    private int MAXFLOOR;
    private int onTheMove = 0;
    private boolean doorsOpen = false;
    private Semaphore mutex = new Semaphore(1);

    public Monitor(int MAX, int MAXFLOOR) {
        this.MAX = MAX;
        this.MAXFLOOR = MAXFLOOR;
        this.toEnter = new int[MAXFLOOR];
        this.toExit = new int[MAXFLOOR];
    }

    public synchronized void callLift(int fromFloor) throws InterruptedException {
        mutex.acquire();
        toEnter[fromFloor] += 1;
        mutex.release();
        notifyAll();
    }

    public synchronized void canEnter(Passenger passenger) throws InterruptedException {
        while (floor != passenger.getStartFloor() || inLift == MAX || !doorsOpen) {
            wait();
        }
        mutex.acquire();
        onTheMove++;
        inLift++;
        mutex.release();
        notifyAll();
    }

    public synchronized void entered(Passenger passenger) throws InterruptedException {
        toEnter[passenger.getStartFloor()] -= 1;
        toExit[passenger.getDestinationFloor()] += 1;
        onTheMove--;
        notifyAll();
    }

    public synchronized void canExit(Passenger passenger) throws InterruptedException {
        while (floor != passenger.getDestinationFloor() || !doorsOpen) {
            wait();
        }
        mutex.acquire();
        onTheMove++;
        mutex.release();
        notifyAll();
    }

    public synchronized void exited(Passenger passenger) throws InterruptedException {
            toExit[passenger.getDestinationFloor()] -= 1;
            onTheMove--;
            inLift--;
            notifyAll();
    }

    public synchronized void updateFloor(int currentFloor) throws InterruptedException {
        floor = currentFloor;
    }
    
    public boolean canOpen() {
       if((toEnter[floor] != 0 && inLift < MAX) || toExit[floor] != 0) {
            return true;
       }
       return false;
    }

    public synchronized void canClose() throws InterruptedException {
        while (onTheMove != 0 || (toEnter[floor] != 0 && inLift < MAX) || toExit[floor] != 0) {
            wait();
        }
    }

    public synchronized void updateDoor(boolean b) {
        doorsOpen = b;
        notifyAll();
    }

    public synchronized void canMove() throws InterruptedException {
        while(wantToRide() == 0) {
            wait();
        }
    }

    private int wantToRide() {
        int k = 0;
        for (int i = 0; i < MAXFLOOR; i++) {
            k += toEnter[i] + toExit[i];
        }
        return k;
    }

}
