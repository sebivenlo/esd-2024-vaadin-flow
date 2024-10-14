package org.vaadin.application.views;

import org.vaadin.application.data.Task;
import org.vaadin.application.service.TaskService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route("task")
public class TaskDetailView extends VerticalLayout implements HasUrlParameter<Long> {

    private TaskService taskService;

    public TaskDetailView(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void setParameter(BeforeEvent event, Long taskId) {
        removeAll();

        Task task = taskService.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        add(
            new H2(task.getTitle()),
            new Paragraph(task.getDescription()),
            new Paragraph("Due date: " + task.getDueDate()),
            new Paragraph("Status: " + task.getStatus())
        );
    }
}
