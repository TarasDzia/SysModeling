package lpnu.sys_modeling.labs.lab2_rework;

import javafx.scene.control.TextArea;
import lpnu.sys_modeling.labs.lab2_rework.objects.Client;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;

public class SimulationQueue{

    private Random random;
    private List<Client> clientDoneWork = new ArrayList<>();
    private Queue<Client> clientQueue = new LinkedList<>();
    private final int capacity;
    private int workId;

    private LocalTime currentTime;
    private Client currentClient;
    private int secondsToNextClient;

    //random bounds
    private int minEnterTime;
    private int maxEnterTime;
    private int minWorkTime;
    private int maxWorkTime;


    public SimulationQueue(int capacity, int minEnterTime, int maxEnterTime, int minWorkTime, int maxWorkTime) {
        this.capacity = capacity;
        this.minEnterTime = minEnterTime;
        this.maxEnterTime = maxEnterTime;
        this.minWorkTime = minWorkTime;
        this.maxWorkTime = maxWorkTime;
        this.random = new Random(39781L);
        secondsToNextClient = 0;
        workId = 0;
    }


    public void start(){
        currentTime = LocalTime.now();
        currentTime = currentTime.withNano(0);
        int workerWaitTime = 0;
        while(clientDoneWork.size() < capacity){
            // responsible for adding new clients
            if(isItTimeToAddNew())
                addNewClient();
            else
                secondsToNextClient--;

            if(!clientQueue.isEmpty())
                currentClient = clientQueue.peek(); // responsible for taking new currentClient
            else
                currentClient = null;

            if (currentClient != null){
                // responsible for working on currentWork
                if(!currentClient.getWork().isDone()){
                    currentClient.getWork().doABitOfWork(currentTime);
                }

                if(currentClient.getWork().isDone()){  // start working with next client if previous finished
                    clientDoneWork.add(clientQueue.poll());
                    currentClient = clientQueue.peek();

                    if (currentClient != null) {
                        currentClient.getWork().doABitOfWork(currentTime);
                    }
                }
            }


            if(!clientDoneWork.isEmpty() && currentClient != null) {
                findTimeBetweenClientsEntered(getLastDoneClient(), currentClient);
                currentClient.setWorkerWaitTime(calculateDurationBetween(getLastDoneClient().getWork().getFinishWork(), currentClient.getWork().getStartWork()));
            }

            // time flow
            currentTime = currentTime.plusSeconds(1);
        }
        System.out.println("Results: \n" + printResults());

    }


    private Duration calculateDurationBetween(LocalTime start, LocalTime finish){
        if(start != null && finish != null){
            return Duration.between(start, finish);
        }
        return Duration.ZERO;
    }

    private Client getLastDoneClient(){
        return clientDoneWork.get(clientDoneWork.size()-1);
    }

    private void findTimeBetweenClientsEntered(Client previousClient, Client currentClient) {
        Duration timeBetweenLastEntered = Duration.between(previousClient.getEnterInQueueTime(), currentClient.getEnterInQueueTime());
        currentClient.setEnterAfterPrevious(timeBetweenLastEntered);
    }

  /*  private boolean isCurrentClientWorkDone() {
        return currentClient.getWork().getTime() <= currentClient.getWork().getCompletedWorkTime();
    }*/

    private boolean isItTimeToAddNew(){
        if(secondsToNextClient <= 0)
            return true;
        return false;
    }

    private void addNewClient() {
        int workTime = Math.abs(random.nextInt()%maxWorkTime)+minWorkTime;
        Client client1 = new Client(currentTime, workTime, workId);
        clientQueue.add(client1);
        secondsToNextClient = Math.abs(random.nextInt()%maxEnterTime)+minEnterTime;
        System.out.println("Next client will come in " + (secondsToNextClient+1));
        workId++;
    }


    public String printResults() {
        String result = "";
        result += "ID клієнта" + "\t" +
                "Час входу між попереднім" + "\t" +
                "Час опрацювання заявки" + "\t" +
                "Час входу клієнта" + "\t" +
                "Час початку роботи" + "\t" +
                "Час закінчення роботи" + "\t" +
                "Час очікування в системі" + "\t" +
                "Час очікування касира";
        for (Client c : clientDoneWork) {
            result += "\n" + c.toString();
        }
        double intensityOfEnterThread = calculateIntensityOfEnterThread();

        result += "\nІнтенсивність вхідного потоку = " + String.format("%.3f", intensityOfEnterThread);
        result += "\nСередній час очікування заявкою обслуговування = " + String.format("%.2f", calculateAvarageWaitTimeForStartWork()) + " seconds";
        result += "\nСередній час перебування заявки в системі = " + String.format("%.2f", calculateAverageClientTimeInSystem()) + " seconds";
        result += "\nКоефіцієнт завантаження системи = " + String.format("%.3f", (intensityOfEnterThread / (1/ calculateAverageWorkDurationTime())) * calculateIntensityOfEnterThread());

        return result;
//        textArea.setText(result);
    }

    private double calculateIntensityOfEnterThread() {
        double averageTime = 0;
        for (Client c : clientDoneWork) {
            averageTime += c.getEnterAfterPrevious().toSeconds();
        }
        return capacity/averageTime;
    }

    private double calculateAvarageWaitTimeForStartWork() {
        double avarageWaitTime = 0;
        for (Client c : clientDoneWork) {
            avarageWaitTime += Duration.between(c.getEnterInQueueTime(), c.getWork().getStartWork()).toSeconds();
        }
        return avarageWaitTime / capacity;
    }

    private double calculateAverageClientTimeInSystem() {
        double avarageClientTime = 0;
        for (Client c : clientDoneWork) {
            avarageClientTime += c.getWaitTimeTillDone().toSeconds();
        }
        return avarageClientTime / capacity;
    }

    private double calculateAverageWorkDurationTime() {
        double avarageWorkDoingTime = 0;
        for (Client c : clientDoneWork) {
            avarageWorkDoingTime += c.getWork().getTime();
        }
        return avarageWorkDoingTime / capacity;
    }

}
