import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

import clock.AlarmClockEmulator;
import clock.io.Choice;
import clock.io.ClockChange;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;
import clock.io.ClockTicker;
import clock.io.Monitor;

public class ClockMain {
    public static void main(String[] args) throws InterruptedException {
        AlarmClockEmulator emulator = new AlarmClockEmulator();
        ClockInput  in  = emulator.getInput();
        ClockOutput out = emulator.getOutput(); 

        Monitor monitor = new Monitor(LocalDateTime.now().getHour(),LocalDateTime.now().getMinute(), LocalDateTime.now().getSecond(), out);

        ClockTicker clockThread = new ClockTicker(monitor);
        ClockChange clockChange = new ClockChange(monitor, out);
        clockThread.start();
        clockChange.start();

        while (true) {


            in.getSemaphore().acquire();

            UserInput userInput = in.getUserInput();
            Choice c = userInput.choice();
            int h = userInput.hours();
            int m = userInput.minutes();
            int s = userInput.seconds();

            if(c.equals(Choice.SET_TIME)){
                monitor.setTime(h, m, s);
            }else if(c.equals(Choice.SET_ALARM)){
                monitor.setAlarm(h, m, s);
            }else if(c.equals(Choice.TOGGLE_ALARM)){
                monitor.setAlarmCheck(false);
            }
            System.out.println("choice=" + c + " h=" + h + " m=" + m + " s=" + s);
      
        }
    }
}
