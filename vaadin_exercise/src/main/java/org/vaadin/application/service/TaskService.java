package org.vaadin.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.vaadin.application.data.Task;

@Service
public class TaskService {
    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    public List<Task> findAll() {
        return new ArrayList<>(tasks);
    }

    public Optional<Task> findById(Long id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst();
    }

    public Task save(Task task) {
        if (task.getId() == null) {
            task.setId(nextId++);
            tasks.add(task);
        } else {
            tasks = tasks.stream()
                    .map(t -> t.getId().equals(task.getId()) ? task : t)
                    .collect(Collectors.toList());
        }
        return task;
    }

    public void delete(Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }
}