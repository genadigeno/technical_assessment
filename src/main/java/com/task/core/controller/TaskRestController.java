package com.task.core.controller;

import com.task.core.controller.advices.TaskNotCreatedException;
import com.task.core.http.StatusResponse;
import com.task.core.messaging.ReceivedTaskQueue;
import com.task.core.messaging.TaskStatus;
import com.task.core.model.Task;
import com.task.core.http.TaskRegistrationNumberResponse;
import com.task.core.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/task")
public class TaskRestController {

    @Autowired
    private ReceivedTaskQueue<String> receivedTaskQueue;

    @Autowired
    private TaskService taskService;

    @GetMapping("/receive") //1
    public TaskRegistrationNumberResponse taskRegistrationNumber(@RequestParam String taskName) {

        /*for (int i=0; i< 100; i++) {
            receivedTaskQueue.add(taskName+i);
        }*/

        Task newTask = taskService.getTask(taskName);
        if (newTask == null) {
            newTask = new Task();
            newTask.setTaskName(taskName);
            newTask.setTaskResult(TaskStatus.RECEIVED.getValue());
            newTask = taskService.save(newTask);
        }

        if (newTask == null) throw new TaskNotCreatedException();

        receivedTaskQueue.add(taskName);
        return new TaskRegistrationNumberResponse(newTask.getTaskId());
    }

    @GetMapping("/status") //2
    public ResponseEntity<?> taskRegistrationNumber(@RequestParam int taskId) {
        Task task = taskService.getTask(taskId);
        if (task == null) throw new EntityNotFoundException("Task not Found");
        return ResponseEntity.ok(task);
    }

    @GetMapping("/external") //3
    public StatusResponse fakeRestService(@RequestParam String taskName) {

        try {
            if (taskName.lastIndexOf("5") > -1){ //ხელოვნური 5 წამიანი ყოვნი
                Thread.sleep(5000);
            }

            if (taskName.lastIndexOf("1") > -1){
                return new StatusResponse(1);
            } else if (taskName.lastIndexOf("2") > -1){
                return new StatusResponse(2, "Good Job", "");
            } else if (taskName.lastIndexOf("3") > -1){
                return new StatusResponse(3, "", "Cancelled");
            }

        } catch (NullPointerException | InterruptedException e) {
            e.printStackTrace();
        }

        return new StatusResponse(0);
    }
}
