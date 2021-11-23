package lpnu.sys_modeling.labs.lab2.threads;

import lpnu.sys_modeling.labs.lab2.objects.Client;

import java.util.Queue;
import java.util.Random;

public class ClientAdder extends Thread{
    private final long seed = 39781L;

    Queue<Client> clientQueue;
    private final int capacity;
    private final Random random;

    private final int minEnterTime;
    private final int maxEnterTime;

    private final int minWorkTime;
    private final int maxWorkTime;

    public ClientAdder(Queue<Client> clientQueue, int capacity, int minEnterTime, int maxEnterTime, int minWorkTime, int maxWorkTime) {
        this.clientQueue = clientQueue;
        this.capacity = capacity;
        this.minEnterTime = minEnterTime;
        this.maxEnterTime = maxEnterTime;
        this.minWorkTime = minWorkTime;
        this.maxWorkTime = maxWorkTime;
        random = new Random();
        random.setSeed(seed);
        start();
    }

    @Override
    public void run() {
        int workTime;
        int waitTime;
        try {
            for (int i = 0; i < capacity; i++) {
                workTime = Math.abs(random.nextInt()%maxEnterTime)+minEnterTime;
                addNewClient(workTime);

                waitTime = Math.abs(random.nextInt()%maxWorkTime)+minWorkTime;
                System.out.println("Client was added. Next will come in " + waitTime + " seconds");
                sleep(waitTime * 1000L);
            }
        } catch (InterruptedException e) {}
    }


    private void addNewClient(int workTime){
        clientQueue.add(new Client(workTime));
    }
}
