package lift;

public class LiftThread extends Thread {
    private LiftView lift;
    private int currentFloor = 0;
    private boolean up = true;
    private int direction = 0;
    private Monitor monitor;
    private boolean doorsOpen = false;
    private int MAXFLOOR;

    public LiftThread(LiftView lift, Monitor monitor, int MAXFLOOR) {
        this.lift = lift;
        this.monitor = monitor;
        this.MAXFLOOR = MAXFLOOR;
    }
    
    public void moveLift() throws InterruptedException {
        monitor.canMove();
        if(up) {
            direction = 1;
        } else {
            direction = -1;
        }
        lift.moveLift(currentFloor, currentFloor + direction);
        currentFloor += direction;
         if(currentFloor == MAXFLOOR - 1) {
            up = false;
        } else if (currentFloor == 0){
            up = true;
        }
    }

    private void updateDoors() throws InterruptedException {
        if(monitor.canOpen()) {
            lift.openDoors(currentFloor);
            doorsOpen = true;
            monitor.updateDoor(doorsOpen);
            monitor.canClose();
            doorsOpen = false;
            monitor.updateDoor(doorsOpen);
            lift.closeDoors();
            }
    }

   

    private void runLift() {
            try {
                 moveLift();
                 updateFloor();
                 updateDoors();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }


    private void updateFloor() throws InterruptedException {
        monitor.updateFloor(currentFloor);
    }

    public void run() {
        while (true) {
            runLift();
        }
    }
}
