package org.vaadin.application.views;

import java.time.LocalDate;

import org.vaadin.application.data.Task;
import org.vaadin.application.service.TaskService;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * Create Route for MainView.
 * 
 * @Route
 */
public class MainView extends VerticalLayout {
    private TaskService taskService;
    private TaskForm form;

    /**
     * Create Grid for displaying all Task data.
     * 
     * @param grid : Grid<Task>
     */

    public MainView(TaskService taskService) {
        this.taskService = taskService;
        addClassName("main-view");
        setSizeFull();
        configureGrid(); // todo configureGrid method

        form = new TaskForm();
        form.addSaveListener(this::saveTask);
        form.addDeleteListener(this::deleteTask);
        form.addCloseListener(e -> closeEditor());

        Button addTaskButton = new Button("Add task", e -> {
            grid.asSingleSelect().clear();
            editTask(createEmptyTask());
        });

        HorizontalLayout toolbar = new HorizontalLayout(addTaskButton);

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();

        add(toolbar, mainContent);
        updateList();
        closeEditor();
    }

    /**
     * Complete configureGrid() method.
     * 
     * Set the columns of the using .setColumns method
     * Add a ValueChangeListener to the grid
     */
    private void configureGrid() {
        grid.addClassName("task-grid");
        grid.setSizeFull();
        // todo .setColumns
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        // todo ValueChangeListener
    }

    private void editTask(Task task) {
        if (task == null) {
            closeEditor();
        } else {
            form.setTask(task);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setTask(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void saveTask(TaskForm.SaveEvent event) {
        taskService.save(event.getTask());
        updateList();
        closeEditor();
    }

    private void deleteTask(TaskForm.DeleteEvent event) {
        taskService.delete(event.getTask().getId());
        updateList();
        closeEditor();
    }

    private Task createEmptyTask() {
        return new Task(null, "", "", LocalDate.now(), Task.TaskStatus.TODO);
    }

    private void updateList() {
        grid.setItems(taskService.findAll());
    }
}