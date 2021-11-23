package lpnu.sys_modeling.labs.lab2.threads;

import lpnu.sys_modeling.labs.lab2.objects.Client;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Worker extends Thread{
    private Client currentClient;

    List<Client> clientDoneWork = new ArrayList<>();
    Queue<Client> worksToDo = new LinkedList<>();

    public Worker(List<Client> clientDoneWork, Queue<Client> worksToDo) {
        this.clientDoneWork = clientDoneWork;
        this.worksToDo = worksToDo;
        start();
    }

    @Override
    public void run() {
        LocalTime startWorkerWaiting = null;
        Duration timeBetweenLastEntered = Duration.ZERO;
        while (!Thread.currentThread().isInterrupted()){
            currentClient = worksToDo.poll();
            if (currentClient == null){
                if(startWorkerWaiting == null)
                    startWorkerWaiting = LocalTime.now();
                continue;
            }
            currentClient.setWorkerWaitTime(calculateWorkerWaitTime(startWorkerWaiting));
            startWorkerWaiting = null;

            if(getLastClient() != null)
                timeBetweenLastEntered = Duration.between(getLastClient().getEnterInQueueTime(), currentClient.getEnterInQueueTime());

            currentClient.setEnterAfterPrevious(timeBetweenLastEntered);
            currentClient.getWork().doWork();
            clientDoneWork.add(currentClient);
        }
    }

    private Client getLastClient(){
        if (clientDoneWork.size() == 0)
            return null;
        return clientDoneWork.get(clientDoneWork.size()-1);
    }

    private Duration calculateWorkerWaitTime(LocalTime startWorkerWaiting){
        if(startWorkerWaiting != null){
            return Duration.between(startWorkerWaiting, LocalTime.now());
        }
        return Duration.ZERO;
    }
}
