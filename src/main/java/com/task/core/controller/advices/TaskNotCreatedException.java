package com.task.core.controller.advices;

public class TaskNotCreatedException extends RuntimeException {
    public TaskNotCreatedException() {
        super("Task could not be created");
    }
}
