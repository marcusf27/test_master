package clock.io;

import java.util.concurrent.Semaphore;

import clock.io.*;

public class Monitor {
    private int sec, minute, hour;
    private int alarmSec, alarmMinute, alarmHour;
    private ClockOutput output;
    private boolean alarmCheck = true;
    private Semaphore mutexAlarm = new Semaphore(1);
    private Semaphore mutexTick = new Semaphore(1);

    public Monitor(int hour, int minute, int sec, ClockOutput output) throws InterruptedException{
        this.output = output;
        setTime(hour, minute, sec);
    }
    public void tick()throws InterruptedException{
        mutexTick.acquire();
        sec++;
        if(sec==60){
            sec=0;
            addMin();
        }
        displayTime();
        mutexTick.release();
    }
    private void addMin(){
        minute++;
        if(minute==60){
            minute=0;
            addHour();
        }
    }
    private void addHour(){
        hour++;
        if(hour==24){
            hour = 0;
        }
    }
    public int getHour(){
        return this.hour;
    }
    public int getMinute(){
        return this.minute;
    }
    public int getSecond(){
        return this.sec;
    }
    private void displayTime(){
        output.displayTime(hour, minute, sec);
    }

    public void setTime(int hour, int minute, int sec) throws InterruptedException{
        mutexTick.acquire();
        this.hour = hour;
        this.minute = minute;
        this.sec = sec;
        displayTime();
        mutexTick.release();
    }
    public void setAlarm(int hour, int minute, int sec)throws InterruptedException{
        mutexAlarm.acquire();
        output.setAlarmIndicator(true);
        this.alarmHour = hour;
        this.alarmMinute = minute;
        this.alarmSec = sec;
        mutexAlarm.release();
    }
    public int getAlarmHour(){
        return alarmHour;
    }
    public int getAlarmSec(){
        return alarmSec;
    }
    public int getAlarmMinute(){
        return alarmMinute;
    }
    public boolean getAlarmCheck(){
        return alarmCheck;
    }
    public void setAlarmCheck(boolean alarmCheck)throws InterruptedException{
        mutexAlarm.acquire();
        this.alarmCheck = alarmCheck;
        mutexAlarm.release();
    }
}
