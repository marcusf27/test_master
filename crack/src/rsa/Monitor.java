package rsa;
import java.awt.Component;
import java.util.concurrent.ExecutorService;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import client.view.ProgressItem;
import client.view.WorklistItem;

public class Monitor {
    
    private final JPanel workList;
    private final JPanel progressList;
    private final JProgressBar mainProgressBar;
    private final ExecutorService executorService;


    public Monitor(JPanel workList, JPanel progressList, JProgressBar mainProgressBar, ExecutorService executorService){
        this.workList = workList;
        this.progressList = progressList;
        this.mainProgressBar = mainProgressBar;
        this.executorService = executorService;
    }

    public synchronized void addWork(WorklistItem work) throws InterruptedException{
        workList.add(work);
    }
    public synchronized void addExecuteableWork(Runnable progWork) throws InterruptedException{
        executorService.submit(progWork);
    }
    
    public synchronized void addProgWork(Runnable progWork) throws InterruptedException{
        progressList.add((Component) progWork);
        executorService.submit(progWork);
    }

    public synchronized void removeWork(WorklistItem work) throws InterruptedException{
        workList.remove(work);
    }
    public synchronized void removeProgWork(Runnable progWork) throws InterruptedException{
        progressList.remove((Component) progWork);
    }   
    
    public void run(Runnable progWork) {
        ProgressItem pro = (ProgressItem) progWork;
        // TODO Auto-generated method stub
       try {
            //pro.done(progWork);

            //progressList.remove(this);
       } catch (Exception e) {
        // TODO: handle exception
            e.printStackTrace();
       }
    }

    public synchronized void addMax(){
        mainProgressBar.setMaximum(mainProgressBar.getMaximum() + 1000000);
    }
    public synchronized void reduceStuff(){
        mainProgressBar.setValue(mainProgressBar.getValue() - 1000000);
        mainProgressBar.setMaximum(mainProgressBar.getMaximum() - 1000000);
    }
}
