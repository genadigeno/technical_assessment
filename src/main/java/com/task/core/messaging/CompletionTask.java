package com.task.core.messaging;

import com.task.core.controller.advices.UnknownResultException;
import com.task.core.http.StatusResponse;
import com.task.core.model.Task;
import com.task.core.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class CompletionTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompletionTask.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private RunningTaskQueue<String> runningTaskQueue;

    @Autowired
    private ReceivedTaskQueue<String> receivedTaskQueue;

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${external.url}")
    private String baseUrl;

    @Scheduled(fixedDelay = 2000)
    public void receiveTasks() {
        LOGGER.info("#### Size of running task queue: {} ####", runningTaskQueue.size());
        LOGGER.info("#### Size of received task queue: {} ####", receivedTaskQueue.size());

        /*
        * received queue-დან წაკითხვა, მერე შეამოწმებს თუ არის ადგილი running queue-ში
        * და თუ არის შეინახავს running queue-ში ხოლო received queue-დან ამოიღებს.
        * */
        if (!receivedTaskQueue.isEmpty() && runningTaskQueue.hasEnoughSpace()) {
            LOGGER.info("Total number of tasks which have to be added: {}", runningTaskQueue.totalFreePlaces());

            //რამდენი ცარიელი ადგილიც აქვს იმდენი ახალი task დაემატება.
            for (int i = 1; i <= runningTaskQueue.totalFreePlaces(); i++) {
                String task = receivedTaskQueue.poll();
                LOGGER.warn("A new task \"{}\" added to Running Task Queue", task);
                runningTaskQueue.add(task);
                runningTaskQueue.takePlace(); //+1 ადგილი დაკავდება. (შეიძლება ეს ფუნქცია add()-ში ჩაემატოს და არა აქ.)
            }
        }

        //running queue-დან წაკითხვა და შესაბამისი პროცესის გაშვება.
        for (int i = 1; i <= runningTaskQueue.size(); i++) {
            String taskName = runningTaskQueue.poll();

            //ცალკე ნაკადებად ვუშვებ.
            new Thread(() -> updateTaskStatus(taskName), "thread-"+i+"-"+taskName).start();
        }
    }

    private void updateTaskStatus(String taskName) {
        try {
            if (taskName.lastIndexOf("5") > -1) Thread.sleep(10000);
            else if (taskName.lastIndexOf("15") > -1) Thread.sleep(10000);
            else if (taskName.lastIndexOf("25") > -1) Thread.sleep(10000);
            else if (taskName.lastIndexOf("35") > -1) Thread.sleep(10000);

            /*StatusResponse response = restTemplate.getForObject(baseUrl+taskName, StatusResponse.class);

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
            }*/

        } catch (NullPointerException e) {
            LOGGER.error(e.getMessage());
        } catch (UnknownResultException e) {
            LOGGER.warn(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOGGER.info("Task \"{}\" has been COMPLETED!", taskName);
            runningTaskQueue.freePlace();//Task-ის დასრულების შემდეგ +1 ადგილის გათავისუფლება.
        }
    }

}
