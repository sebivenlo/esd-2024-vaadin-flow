package org.vaadin.application.views;

import static org.mockito.Mockito.description;

import org.vaadin.application.data.Task;
import org.vaadin.application.views.TaskForm.CloseEvent;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class TaskForm extends FormLayout {
    /**
     * Add a Textfield for the title, description.
     * Add a DatePicker for the due date.
     * Add a Select field for the status using enum TaskStatus from Task.java.
     * Add a button for each field.
     * 
     * @param title       : TextField
     * @param description : TextField
     * @param dueDate     : DatePicker
     * @param status      : Select
     * @param save        : Button
     * @param delete      : Button
     * @param close       : Button
     */

    Binder<Task> binder = new BeanValidationBinder<>(Task.class);

    public TaskForm() {
        addClassName("task-form");
        binder.bindInstanceFields(this);

        status.setItems(Task.TaskStatus.values());
        status.setLabel("Status");

        add(
                title,
                description,
                dueDate,
                status,
                createButtonsLayout());
    }

    public void setTask(Task task) {
        binder.setBean(task);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public static abstract class TaskFormEvent extends ComponentEvent<TaskForm> {
        private Task task;

        protected TaskFormEvent(TaskForm source, Task task) {
            super(source, false);
            this.task = task;
        }

        public Task getTask() {
            return task;
        }
    }

    public static class SaveEvent extends TaskFormEvent {
        SaveEvent(TaskForm source, Task task) {
            super(source, task);
        }
    }

    public static class DeleteEvent extends TaskFormEvent {
        DeleteEvent(TaskForm source, Task task) {
            super(source, task);
        }
    }

    public static class CloseEvent extends TaskFormEvent {
        CloseEvent(TaskForm source) {
            super(source, null);
        }
    }

    public Registration addDeleteListener(ComponentEventListener<DeleteEvent> listener) {
        return addListener(DeleteEvent.class, listener);
    }

    public Registration addSaveListener(ComponentEventListener<SaveEvent> listener) {
        return addListener(SaveEvent.class, listener);
    }

    public Registration addCloseListener(ComponentEventListener<CloseEvent> listener) {
        return addListener(CloseEvent.class, listener);
    }
}
