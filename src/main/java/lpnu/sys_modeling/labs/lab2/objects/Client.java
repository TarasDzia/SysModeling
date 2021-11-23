package lpnu.sys_modeling.labs.lab2.objects;

import java.time.Duration;
import java.time.LocalTime;

public class Client extends Thread {
    private Work work;
    private LocalTime enterInQueueTime;

    private Duration timeDurLastEntered;
    private Duration waitTimeTillDone;

    private Duration workerWaitTime;
    private static int workId = 0;

    public Client(int workTime) {
        this.work = new Work(workId++, workTime);
        enterInQueueTime = LocalTime.now().withNano(0);
    }

    public Work getWork() {
        return work;
    }

    @Override
    public String toString() {
        waitTimeTillDone = Duration.between(enterInQueueTime, work.getFinishWork());
        return "" + work.getId() + "\t\t" +
                timeDurLastEntered.toSeconds() + "\t\t\t\t\t\t" +
                work.getTime() + "\t\t" +
                enterInQueueTime + "\t\t\t" +
                work.getStartWork() + "\t\t" +
                work.getFinishWork() + "\t\t\t" +
                waitTimeTillDone.toSeconds() + "\t\t\t\t" +
                workerWaitTime.toSeconds();
    }

    public void setWorkerWaitTime(Duration workerWaitTime) {
        this.workerWaitTime = workerWaitTime;
    }

    public Duration getEnterAfterPrevious() {
        return timeDurLastEntered;
    }

    public LocalTime getEnterInQueueTime() {
        return enterInQueueTime;
    }

    public Duration getWaitTimeTillDone() {
        return waitTimeTillDone;
    }

    public void setEnterAfterPrevious(Duration enterAfterPrevious) {
        this.timeDurLastEntered = enterAfterPrevious;
    }
}
