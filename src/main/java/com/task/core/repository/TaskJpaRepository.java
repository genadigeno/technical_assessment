package com.task.core.repository;

import com.task.core.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskJpaRepository extends JpaRepository<Task, Integer> {
    Task getByTaskName(String name);
    Task getByTaskId(Integer id);
}
