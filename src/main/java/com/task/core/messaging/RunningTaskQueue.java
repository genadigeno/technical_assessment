package com.task.core.messaging;

import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentLinkedQueue;

/* მიღებული Task-ების გამშვები Queue, მაქსიმალურის Task-ების რაოდენობა რაც გაეშვება არის RUNNING_TASKS_SIZE
 * საწყისი თავისუფალი ადგილების რაოდენობა არის RUNNING_TASKS_SIZE
 * */
@Component("runningTaskQueue")
public class RunningTaskQueue<T> extends ConcurrentLinkedQueue<T> {
    public final int RUNNING_TASKS_SIZE = 10;

    private int freePlaces = RUNNING_TASKS_SIZE;

    public int totalFreePlaces() {
        return freePlaces;
    }

    /*როცა ერთი Task დასრულდება 1 ადგილი გამოთავისუფლდება
      რათა ახალი Task-ის მიღების შესაძლებლობა იყოს */
    public synchronized void freePlace() {
        if (freePlaces < RUNNING_TASKS_SIZE) ++freePlaces;
    }

    /*როცა ახალი Task ჩაემატება 1 ადგილი დაკავდება*/
    public synchronized void takePlace() {
        if (freePlaces > 0) --freePlaces;
    }

    public boolean hasEnoughSpace() {
        return freePlaces > 0;
    }

    @Override
    public synchronized boolean add(T t) {
        if (size() < RUNNING_TASKS_SIZE) { //RUNNING_TASKS_SIZE-ზე მეტ Task-ს აღარ მიიღებს.
            return super.add(t);
        }
        return false;
    }
}
