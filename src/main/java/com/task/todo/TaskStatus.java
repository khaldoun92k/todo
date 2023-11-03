package com.task.todo;

public enum TaskStatus {
    NOT_DONE("not done"),
    DONE("done"),
    PAST_DUE("past due");

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
