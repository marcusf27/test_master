package clock.io;

public class ClockChange extends Thread{
    
    private Monitor monitor;
    private ClockOutput output;

    public ClockChange(Monitor monitor, ClockOutput output){
        this.monitor = monitor;
        this.output = output;
    }

    public void run(){
        while(true){
            try{
                Thread.sleep(0);
            }
            catch(Exception e){
                e.printStackTrace();
            }
            if(monitor.getAlarmHour()==monitor.getHour() && monitor.getAlarmMinute() == monitor.getMinute() && monitor.getAlarmSec()==monitor.getSecond()){
                alarmWentOff();
                output.setAlarmIndicator(false);
            }
        }
    }
    private void alarmWentOff(){
        long delay = System.currentTimeMillis();
        int i = 0;
        while(i < 20 && monitor.getAlarmCheck()){
            output.alarm();
            try {
                long current = System.currentTimeMillis();
                long next = delay + (i+1)*1000;
                long delayTime = next - current;
                Thread.sleep(delayTime);
            //Thread.sleep(1000);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            i++;
        }
    }
}
