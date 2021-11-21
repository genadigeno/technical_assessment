package com.task.core.service;

import com.task.core.model.Task;
import com.task.core.repository.TaskJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskJpaRepository taskJpaRepository;

    @Transactional(rollbackOn = Exception.class)
    public Task save(Task task) {
        return taskJpaRepository.save(task);
    }

    public Task getTask(String taskName) {
        return taskJpaRepository.getByTaskName(taskName);
    }

    public Task getTask(int taskId) {
        return taskJpaRepository.getByTaskId(taskId);
    }
}
