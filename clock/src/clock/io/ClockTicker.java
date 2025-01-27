package clock.io;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClockTicker extends Thread{

    private Monitor monitor;

    public ClockTicker(Monitor monitor){
        this.monitor = monitor;
    }
    
    public void run(){
        
        long delay = System.currentTimeMillis();
        int c = 0;
        while(true){
            try{                
                monitor.tick();
                //Thread.sleep(1000);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            try {           
                long current = System.currentTimeMillis();
                long next = delay + (c+1)*1000;
                long delayTime = next - current;
                Thread.sleep(delayTime);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            c++;
        }
    }
}
