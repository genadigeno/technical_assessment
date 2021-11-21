package com.task.core.http;

public class TaskRegistrationNumberResponse {

    public TaskRegistrationNumberResponse(int taskId) {
        this.taskId = taskId;
    }

    private int taskId;

    public int getTaskId() {
        return taskId;
    }
}
