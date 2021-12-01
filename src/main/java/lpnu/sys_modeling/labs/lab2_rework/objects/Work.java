package lpnu.sys_modeling.labs.lab2_rework.objects;

import java.time.LocalTime;

public class Work {
    private final int id;
    private final int time;
    private boolean done = false;

    private int completedWorkTime;

    private LocalTime startWork;
    private LocalTime finishWork;

    public Work(int id, int workTime) {
        this.id = id;
        this.time = workTime;
    }

    public void doABitOfWork(LocalTime currentTime) {
        if(completedWorkTime == 0) {
            startWork = currentTime;
            System.out.println("Start working... on Work-" + this.id);

        }

        if(completedWorkTime >= time) {
            finishWork = currentTime;
            done = true;
            System.out.println("Finish working... on " + this);
        }
        completedWorkTime++;
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

    public int getCompletedWorkTime() {
        return completedWorkTime;
    }

    public boolean isDone() {
        return done;
    }
}

