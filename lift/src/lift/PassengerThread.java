package lift;

public class PassengerThread extends Thread {
    private int enterFloor;
    private Passenger pass;
    private Monitor monitor;

    public PassengerThread(Passenger passenger, Monitor monitor) {
        this.pass = passenger;
        this.enterFloor = passenger.getStartFloor();
        this.monitor = monitor;
    }

    public void run() {
            try {
                pass.begin(); 
                monitor.callLift(enterFloor);
                monitor.canEnter(pass);
                pass.enterLift();
                monitor.entered(pass);
                monitor.canExit(pass);
                pass.exitLift();
                monitor.exited(pass);
                pass.end();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
    }
    
}
