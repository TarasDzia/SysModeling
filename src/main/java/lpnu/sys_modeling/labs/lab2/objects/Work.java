package lpnu.sys_modeling.labs.lab2.objects;

import java.time.LocalTime;

public class Work {
    private final int id;
    private final int time;
    private boolean done = false;

    private LocalTime startWork;
    private LocalTime finishWork;

    public Work(int id, int workTime) {
        this.id = id;
        this.time = workTime;
    }

    public void doWork(){
        startWork = LocalTime.now();
        startWork = startWork.withNano(0);
        System.out.println("Start working... on " + this);
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finishWork = LocalTime.now();
        finishWork = finishWork.withNano(0);
        done = true;
        System.out.println("Finish working... on " + this);
    }

    @Override
    public String toString() {
        return "Work-"+id+"{" +
                "done="+ done +
                ", workingTime=" + time +
                ", startTime=" + startWork +
                ", finishTime=" + finishWork +
                '}';
    }

    public int getTime() {
        return time;
    }

    public LocalTime getStartWork() {
        return startWork;
    }

    public LocalTime getFinishWork() {
        return finishWork;
    }

    public int getId() {
        return id;
    }
}

