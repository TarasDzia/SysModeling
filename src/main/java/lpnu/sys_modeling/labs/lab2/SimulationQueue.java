package lpnu.sys_modeling.labs.lab2;

import javafx.scene.control.TextArea;
import lpnu.sys_modeling.labs.lab2.objects.Client;
import lpnu.sys_modeling.labs.lab2.threads.ClientAdder;
import lpnu.sys_modeling.labs.lab2.threads.Worker;

import java.time.Duration;
import java.util.*;

public class SimulationQueue extends Thread {

    Random random;

    List<Client> clientDoneWork = new ArrayList<>();
    Queue<Client> clientQueue = new LinkedList<>();
    int capacity;
    TextArea textArea;

    ClientAdder queueAdder;
    Worker worker;

    public SimulationQueue(int capacity, int minEnterTime, int maxEnterTime, int minWorkTime, int maxWorkTime, TextArea textArea) {
        this.capacity = capacity;
        this.textArea = textArea;
        queueAdder = new ClientAdder(clientQueue, capacity, minEnterTime, maxEnterTime, minWorkTime, maxWorkTime);
        worker = new Worker(clientDoneWork, clientQueue);
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (clientDoneWork.size() == capacity) {
                queueAdder.interrupt();
                worker.interrupt();
                printResults(textArea);
                return;
            }
        }
    }

    public void printResults(TextArea textArea) {
        String result = "";
        result += "workId" + "\t" +
                "timeAfterLastEntered" + "\t" +
                "workDuration" + "\t" +
                "clientEnterQueue" + "\t" +
                "workStart" + "\t\t" +
                "workFinish" + "\t" +
                "clientWaitTime" + "\t\t" +
                "workerWaitTime";
        for (Client c : clientDoneWork) {
            result += "\n" + c.toString();
        }
        double intensityOfEnterThread = calculateIntensityOfEnterThread();

        result += "\nІнтенсивність вхідного потоку = " + String.format("%.3f", intensityOfEnterThread);
        result += "\nСередній час очікування заявкою обслуговування = " + String.format("%.2f", calculateAvarageWaitTimeForStartWork()) + " seconds";
        result += "\nСередній час перебування заявки в системі = " + String.format("%.2f", calculateAverageClientTimeInSystem()) + " seconds";
        result += "\nКоефіцієнт завантаження системи = " + String.format("%.3f", (intensityOfEnterThread / calculateAverageWorkDurationTime()));

        textArea.setText(result);
    }

    private double calculateIntensityOfEnterThread() {
        double averageTime = 0;
        for (Client c : clientDoneWork) {
            averageTime += c.getEnterAfterPrevious().toSeconds();
        }
        return 1 / (averageTime / capacity);
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
