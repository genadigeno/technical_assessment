package com.task.core.messaging;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/* Task-ების მიმღები Queue, სადაც რაოდენობა არ იზღუდება. */
@Component("receivedTaskQueue")
public class ReceivedTaskQueue<T> extends ConcurrentLinkedQueue<T> {

    @Override
    public synchronized boolean add(T t) {
        if (!contains(t)) { //ერთი და იგივე დასახელების Task-ს არ ინახავს.
            return super.add(t);
        }
        return false;
    }
}
