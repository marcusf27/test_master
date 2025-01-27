
import lift.LiftThread;
import lift.LiftView;
import lift.Passenger;
import lift.PassengerThread;
import lift.Monitor;

public class OnePersonRidesLift {

    public static void main(String[] args) throws InterruptedException {

        final int NBR_FLOORS = 10, MAX_PASSENGERS_IN_LIFT = 6, NBR_OF_PASSENGERS = 40;

        Monitor monitor = new Monitor(MAX_PASSENGERS_IN_LIFT, NBR_FLOORS);

        LiftView view = new LiftView(NBR_FLOORS, MAX_PASSENGERS_IN_LIFT);
        LiftThread t = new LiftThread(view, monitor, NBR_FLOORS);
        t.start();

        for (int i = 0; i < NBR_OF_PASSENGERS; i++) {
            Passenger pass = view.createPassenger();
            PassengerThread p1 = new PassengerThread(pass, monitor);
            p1.start();
        }
    }
}