package org.vaadin.application.data;

import java.time.LocalDate;

public class Task {
    /**
     * Create fields for task parameters.
     * Create enum TaskStatus with values TODO, IN_PROGRESS, DONE.
     * Create a constructor with parameters for all fields.
     * 
     * @param id          : Long
     * @param title       : String
     * @param description : String
     * @param dueDate     : LocalDate
     * @param status      : TaskStatus
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
