package com.task.core.messaging;

import com.task.core.controller.advices.UnknownResultException;
import com.task.core.http.StatusResponse;
import com.task.core.model.Task;
import com.task.core.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CompletionTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompletionTask.class);

    @Autowired
    private TaskService taskService;

    @Value("${external.url}")
    private String baseUrl;

    @RabbitListener(queues = "#{taskQueue.name}")
    public void receiveTasks(String taskName) { //test on delay time

        RestTemplate restTemplate = new RestTemplate();
        try {
            StatusResponse response = restTemplate.getForObject(baseUrl+taskName, StatusResponse.class);
            System.out.println(response);

            Task task = taskService.getTask(taskName);
            if (task != null) {
                task.setTaskStatus(response.getStatus());

                if (response.getStatus() == 0) {
                    task.setTaskResult(TaskStatus.RECEIVED.getValue());
                } else if (response.getStatus() == 1) {
                    task.setTaskResult(TaskStatus.IN_PROCESS.getValue());
                } else if (response.getStatus() == 2) {
                    task.setTaskResult(TaskStatus.SUCCESSFUL.getValue());
                    task.setFailureReason(response.getResult());
                } else if (response.getStatus() == 3) {
                    task.setTaskResult(TaskStatus.FAILED.getValue());
                    task.setFailureReason(response.getError());
                } else {
                    throw new UnknownResultException();
                }
                taskService.save(task);
            } else {
                LOGGER.warn("Task \"{}\" not found in database", taskName);
            }
        } catch (NullPointerException e) {
            LOGGER.error(e.getMessage());
        } catch (UnknownResultException e) {
            LOGGER.warn(e.getMessage());
        }
    }

}
