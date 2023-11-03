package com.task.todo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;


import java.util.Date;
@Entity
public class Item {
    @Id
    @GeneratedValue
    private Long Id;
    @NotNull(message = "Description is required")
    private String description;
    private TaskStatus status = TaskStatus.NOT_DONE;
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    private Date creationDate = new Date();
    @JsonFormat(pattern="dd.MM.yyyy HH:mm:ss")
    private Date dueDate;

    public Item() {
    }

    public Item(String description,Date dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Item{" +
                "Id=" + Id +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", dueDate=" + dueDate +
                '}';
    }

}
